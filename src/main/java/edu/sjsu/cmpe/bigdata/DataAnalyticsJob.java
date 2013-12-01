package edu.sjsu.cmpe.bigdata;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;


@Every("3600s")
public class DataAnalyticsJob extends Job{
        @Override
        public void doJob() {
        	System.out.println("In Sentiment Analysis-BEGIN");
        	MongoClient client;
			try {
				client = new MongoClient();
				DB courseDB = client.getDB("bigdata");
				DBCollection collection = courseDB.getCollection("yelp");
    		
				// Query for fetching reviews for a particular restaurant
				//DBObject query = new BasicDBObject("type","review").append("business_id", "VFslQjSgrw4Mu5_Q1xk1KQ");
				DBObject query = new BasicDBObject("type","review").append("business_id", "LjOIxpH-89S18WI1ktmPBQ");
				
				DBCursor cursor = collection.find(query);
				
				SentimentalAnalysis builder = new SentimentalAnalysis();
				
				// URL for using sentiment API for sentimental analysis
				String baseurl = "https://api.sentigem.com/external/get-sentiment"
						+ "?api-key=c7b7029934a1159ae3e2a9c3f80d3f99PiXQg6kuBN_0dp2Z8E5lrWTD4-oAqJbY&text=";
						
				String param = "";
				String response = "";
				int neutral=0, negative=0, positive=0, notEval=0;
				
				
				while(cursor.hasNext()){
					
					DBObject cur = cursor.next();
					String userReview = (String) cur.get("text");
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
				} 
				catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					}			
				}
				
				DBCollection collection1 = courseDB.getCollection("analysis");
				
				String b_id = "VFslQjSgrw4Mu5_Q1xk1KQ";
				
				DBObject doc = new BasicDBObject()
									.append("business_id", b_id)
									.append("positive", positive)
									.append("negative", negative)
									.append("neutral", neutral)
									.append("notEval", notEval)
									.append("date", new Date());
				
				collection1.insert(doc);
				
				System.out.println("Number of neutral reviews       : "+ neutral);
				System.out.println("Number of positive reviews      : "+ positive);
				System.out.println("Number of negative reviews      : "+ negative);
				System.out.println("Number of reviews not evaluated : "+ notEval);
					    
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
			System.out.println("In Sentiment Analysis-END");
			}
        }
