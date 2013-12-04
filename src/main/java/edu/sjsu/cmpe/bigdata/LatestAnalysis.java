package tengen;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class LatestAnalysis {

	public String excutePost(String targetURL, String urlParameters) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {

			//e.printStackTrace();
			return "This review is not evaluated";

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	public static void main(String args[]) throws UnknownHostException
	{
		MongoClient client = new MongoClient();
		DB courseDB = client.getDB("codeassassins");
		DBCollection collection = courseDB.getCollection("bigdata");
		DBCollection collection1 = courseDB.getCollection("analysis");

		//String b_id = "VFslQjSgrw4Mu5_Q1xk1KQ";
		//String b_id = "YMeWjOd1svHDGdDCKoiGgg";
		String b_id = "LjOIxpH-89S18WI1ktmPBQ";
		
		// Query for fetching reviews for a particular restaurant
		DBObject query = new BasicDBObject("type","review").append("business_id", b_id);
		
		DBCursor cursor = collection.find(query);

		LatestAnalysis builder = new LatestAnalysis();
		
		// URL for using sentigem API for sentimental analysis
		String baseurl = "https://api.sentigem.com/external/get-sentiment?api-key=c7b7029934a1159ae3e2a9c3f80d3f99PiXQg6kuBN_0dp2Z8E5lrWTD4-oAqJbY&text=";
				
		String param = "";
		String response = "";
		int neutral=0, negative=0, positive=0,notEval=0;
		
		
		while(cursor.hasNext()){

			DBObject cur = cursor.next();
			String userReview = (String) cur.get("text");
			
			// Fetching flag for a review
			
			double reviewFlag = (Double) cur.get("flag");
						
			// only reviews with flag as 1 will be analyzed and there flag will be set to 2 
			
			if (reviewFlag == 1){
				
				// Fetching ObjectId of the review which will be used while updating
				// flag for that review
				
				ObjectId reviewID = (ObjectId) cur.get("_id");
				System.out.print(reviewID);
				try {
					String encodedUserReview = java.net.URLEncoder.encode(userReview, "ISO-8859-1");

					String finalurl = baseurl+encodedUserReview;
					response = builder.excutePost(finalurl,param);
					System.out.print(response+"\n");
					
					if (response.contains("neutral")){
						neutral++;
					}
					else if (response.contains("positive")){
						positive++;
					}
					else if (response.contains("negative")){
						negative++;
					}
					else if (response.equalsIgnoreCase("This review is not evaluated")){
						notEval++;
					}
					
				    // Creating query to set review flag to 2 for the review being analyzed
					
					DBObject updateQuery1 = new BasicDBObject().append("_id", reviewID);
					
					DBObject updateQuery2 = new BasicDBObject("$set", new BasicDBObject("flag", 2.0));
					
					collection.update(updateQuery1, updateQuery2);
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
		}
		
		
		// Creating query for fetching details of last analysis
		
		DBObject query1 = new BasicDBObject()
						.append("business_id", b_id)
				;

		DBObject query2 = new BasicDBObject()
						.append("date", -1)
						;
		
		// Executing query
		
		DBCursor cursor1 = collection1.find(query1).sort(query2).limit(1);
			
		int lastPositive=0, lastNegative=0, lastNeutral=0, lastNotEval=0;
		
		try{
		
		// Fetching last analysis details	
		DBObject cur1 = cursor1.next();
		//String business_id = (String) cur1.get("business_id");
		lastPositive = (Integer) cur1.get("positive");
		lastNegative = (Integer) cur1.get("negative");
		lastNeutral  = (Integer) cur1.get("neutral");
		lastNotEval  = (Integer) cur1.get("notEval");
			
		// Printing last analysis details
		
		System.out.println("\nLast analysis details : \n ");
		System.out.println("Number of neutral reviews       : "+ lastNeutral);
		System.out.println("Number of positive reviews      : "+ lastPositive);
		System.out.println("Number of negative reviews      : "+ lastNegative);
		System.out.println("Number of reviews not evaluated : "+ lastNotEval);
		}catch(Exception e){
			System.out.println("\nAnalysing for the first time\n");
		}

		// Adding last analysis details to the latest details
		
		int finalPositive = lastPositive + positive;
		int finalNegative = lastNegative + negative;
		int finalNeutral = lastNeutral + neutral;
		int finalNotEval = lastNotEval + notEval;
		
		// Query for inserting latest analysis to db
		
		DBObject doc = new BasicDBObject()
							.append("business_id", b_id)
							.append("positive", finalPositive)
							.append("negative", finalNegative)
							.append("neutral", finalNeutral)
							.append("notEval", finalNotEval)
							.append("date", new Date())
							;

		collection1.insert(doc);

		// Displaying latest anlysis
		
		System.out.println("\nLatest analysis data :\n");
		System.out.println("Number of neutral reviews       : "+ finalNeutral);
		System.out.println("Number of positive reviews      : "+ finalPositive);
		System.out.println("Number of negative reviews      : "+ finalNegative);
		System.out.println("Number of reviews not evaluated : "+ finalNotEval);

	}
}