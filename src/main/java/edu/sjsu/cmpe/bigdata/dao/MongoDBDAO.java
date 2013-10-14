package edu.sjsu.cmpe.bigdata.dao;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoDBDAO {
	private static MongoClient mongoClientDAO; 
	private static DB dbDAO;
	private static DBCollection collDAO;
	
	public MongoClient getDBConnection(String dbHostName, int dbPortNumber) throws UnknownHostException {
		mongoClientDAO = new MongoClient(dbHostName,dbPortNumber);
		return mongoClientDAO;
	}
	
	public DB getDB (String dbName)	{
		dbDAO = mongoClientDAO.getDB(dbName);
		return dbDAO;
	}

	public DBCollection getCollection(String collection) {
		collDAO = dbDAO.getCollection(collection);
		return collDAO;
	}
	
	public void insertData(BasicDBObject doc)	{
		collDAO.insert(doc);
	}

	public void closeConnection() {
		mongoClientDAO.close();
		
	}

	public DBCursor findData(BasicDBObject query1) {
		return collDAO.find(query1);
	}
}
