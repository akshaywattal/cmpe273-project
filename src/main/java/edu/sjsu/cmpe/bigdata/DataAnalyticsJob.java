package edu.sjsu.cmpe.bigdata;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Date;

import javax.jms.Connection;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;
import edu.sjsu.cmpe.bigdata.stomp.ApolloSTOMP;


@Every("3600s")
public class DataAnalyticsJob extends Job{
        @Override
        public void doJob() {
        	System.out.println("In Sentiment Analysis-BEGIN");
        	MongoClient client;
        	ApolloSTOMP apolloSTOMP = new ApolloSTOMP();
        	Connection connection;
			try {
				client = new MongoClient();
				DB courseDB = client.getDB("bigdata");
				DBCollection collection = courseDB.getCollection("yelp");
				DBCollection collection1 = courseDB.getCollection("analysis");

				String b_id = "VFslQjSgrw4Mu5_Q1xk1KQ";
				//String b_id = "YMeWjOd1svHDGdDCKoiGgg";
				//String b_id = "LjOIxpH-89S18WI1ktmPBQ";
				
				// Query for fetching reviews for a particular restaurant
				DBObject query = new BasicDBObject("type","review").append("business_id", b_id);			
				DBCursor cursor = collection.find(query);
				SentimentAnalysis builder = new SentimentAnalysis();
				
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
								
					// Only reviews with flag=1 will be analyzed and their flag will be set to 2 
					if (reviewFlag == 1){
						
						// Fetching ObjectId of the review which will be used while updating
						// Flag for that review
						ObjectId reviewID = (ObjectId) cur.get("_id");
						System.out.print(reviewID);
						try {
							String encodedUserReview = java.net.URLEncoder.encode(userReview, "ISO-8859-1");

							String finalurl = baseurl+encodedUserReview;
							response = builder.executePost(finalurl,param);
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
							e.printStackTrace();
							}			
						}
					}
				
				// Creating query for fetching details of last analysis
				DBObject query1 = new BasicDBObject()
								.append("business_id", b_id);

				DBObject query2 = new BasicDBObject()
								.append("date", -1);
				
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
									.append("date", new Date());

				collection1.insert(doc);
				
				// Displaying latest analysis
				System.out.println("\nLatest analysis data :\n");
				System.out.println("Number of neutral reviews       : "+ finalNeutral);
				System.out.println("Number of positive reviews      : "+ finalPositive);
				System.out.println("Number of negative reviews      : "+ finalNegative);
				System.out.println("Number of reviews not evaluated : "+ finalNotEval);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
					}
			try {
				connection = apolloSTOMP.makeConnection();
				apolloSTOMP.publishTopicMessage(connection);
				connection.close();
				} 
			catch (Exception e1) {
				e1.printStackTrace();
				}
			System.out.println("In Sentiment Analysis-END");
			}
        }
