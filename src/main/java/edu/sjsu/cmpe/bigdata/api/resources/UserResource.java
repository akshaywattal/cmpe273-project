package edu.sjsu.cmpe.bigdata.api.resources;

import javax.management.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.metrics.annotation.Timed;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

import edu.sjsu.cmpe.bigdata.domain.User;
import edu.sjsu.cmpe.bigdata.dto.LinkDto;
import edu.sjsu.cmpe.bigdata.dto.LinksDto;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
	private int authenticated=400;

    public UserResource() {
    }

    @POST
    @Timed(name = "create-user")
    public Response createUser(User user) throws UnknownHostException {
    	
    	/**
    	 * Making a database connection to Database: bigdata
    	 */
    	MongoClient mongoClient = new MongoClient("localhost",27017);
		DB db = mongoClient.getDB("bigdata");
		
		/**
    	 * Creating a new Collection: bigdataUserCollection and inserting data
    	 */
		DBCollection coll = db.getCollection("bigdataUserCollection");
		BasicDBObject doc = new BasicDBObject("username",user.getUsername()).append("email", user.getEmail()).append("password", user.getPassword());
		coll.insert(doc);
    	
		/**
    	 * Closing connection
    	 */
		mongoClient.close(); 	
	return Response.status(201).build();
    }
    
    @POST
    @Path("/{username}")
    @Timed(name = "authenticate-user")
    public Response authenticateUser(User user) throws UnknownHostException {
    	
    	/**
    	 * Making a database connection to Database: bigdata
    	 */
    	MongoClient mongoClient = new MongoClient("localhost",27017);
		DB db = mongoClient.getDB("bigdata");
		
		/**
    	 * Connecting to Collection: bigdataUserCollection
    	 */
		DBCollection coll = db.getCollection("bigdataUserCollection");
		
		/**
    	 * Creating query1 for user authentication
    	 */
		BasicDBObject query1 = new BasicDBObject();
		List<BasicDBObject> query1List = new ArrayList<BasicDBObject>();
		query1List.add(new BasicDBObject("username", user.getUsername()));
		query1List.add(new BasicDBObject("password", user.getPassword()));
		query1.put("$and", query1List);
	 
		DBCursor cursor = coll.find(query1);
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
			authenticated=200;
		}
    	
		/**
    	 * Closing connection
    	 */
		mongoClient.close(); 	
	return Response.status(authenticated).build();
    }
}

