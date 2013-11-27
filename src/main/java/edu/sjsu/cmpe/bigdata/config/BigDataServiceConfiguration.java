package edu.sjsu.cmpe.bigdata.config;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class BigDataServiceConfiguration extends Configuration {
	@NotEmpty
    @JsonProperty
    private static String dbHost;

    @JsonProperty
    private static int dbPort;
    
    @NotEmpty
    @JsonProperty
    private static String databasename;

    @NotEmpty
    @JsonProperty
    private static String username;
    
    @NotEmpty
    @JsonProperty
    private static String password;
    
    @NotEmpty
    @JsonProperty
    private static String bigdatausercollection;    
    
    public String getDbHost() {
		return dbHost;
	}

	public void setDbHost(String dbHost) {
		BigDataServiceConfiguration.dbHost = dbHost;
	}

	public int getDbPort() {
		return dbPort;
	}

	public void setDbPort(int dbPort) {
		BigDataServiceConfiguration.dbPort = dbPort;
	}

	public String getDatabasename() {
		return databasename;
	}

	public void setDatabasename(String databasename) {
		BigDataServiceConfiguration.databasename = databasename;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		BigDataServiceConfiguration.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		BigDataServiceConfiguration.password = password;
	}

	public String getBigdatausercollection() {
		return bigdatausercollection;
	}

	public void setBigdatausercollection(String bigdatausercollection) {
		BigDataServiceConfiguration.bigdatausercollection = bigdatausercollection;
	}
    
}
