package edu.sjsu.cmpe.bigdata.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class MongoDBConfig {
	
	public static String dbHostName;
	public static int dbPortNumber;
	public static String dbName;
	public static String dbUserName;
	public static String dbPassword;
	public static String bigdataUserCollection;


	public  MongoDBConfig() throws FileNotFoundException, IOException {
			Properties prop = new Properties();
			URL url = this.getClass().getResource("MongoDB.properties");
			InputStream inputStream = url.openStream(); 
			prop.load(inputStream);
			
			dbHostName = prop.getProperty("host");
			dbPortNumber = Integer.parseInt(prop.getProperty("port"));
			dbName = prop.getProperty("databasename");
			dbUserName = prop.getProperty("username");
			dbPassword = prop.getProperty("password");
			bigdataUserCollection =  prop.getProperty("bigdatausercollection");
		}

}
