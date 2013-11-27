package edu.sjsu.cmpe.bigdata.dao;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import edu.sjsu.cmpe.bigdata.config.BigDataServiceConfiguration;

public class MongoDBDAO {
	BigDataServiceConfiguration configuration = new BigDataServiceConfiguration();
	private static MongoClient mongoClientDAO; 
	private static DB dbDAO;
	private static DBCollection collDAO;
	private String dbHostName;
    int dbPortNumber;
	String dbName;
    String dbUserName;
    String dbPassword;
    String bigdataUserCollection;
	
	public MongoDBDAO(){
	this.setDbHostName(configuration.getDbHost());
	this.dbPortNumber = configuration.getDbPort();
	this.dbName = configuration.getDatabasename();
	this.dbUserName = configuration.getDatabasename();
	this.dbPassword = configuration.getDbHost();
	this.bigdataUserCollection = configuration.getBigdatausercollection();
	}
	
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

	public String getDbHostName() {
		return dbHostName;
	}

	public void setDbHostName(String dbHostName) {
		this.dbHostName = dbHostName;
	}
	
	public int getDbPortNumber() {
		return dbPortNumber;
	}

	public void setDbPortNumber(int dbPortNumber) {
		this.dbPortNumber = dbPortNumber;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getBigdataUserCollection() {
		return bigdataUserCollection;
	}

	public void setBigdataUserCollection(String bigdataUserCollection) {
		this.bigdataUserCollection = bigdataUserCollection;
	}

}
