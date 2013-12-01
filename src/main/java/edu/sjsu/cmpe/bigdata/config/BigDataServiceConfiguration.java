package edu.sjsu.cmpe.bigdata.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
    
    @NotEmpty
    @JsonProperty
    private static String stompQueueName;

    @NotEmpty
    @JsonProperty
    private static String stompTopicName;
    
    @NotEmpty
    @JsonProperty
    private static String apolloUser;

	@NotEmpty
    @JsonProperty
    private static String apolloPassword;
    
    @NotEmpty
    @JsonProperty
    private static String apolloHost;
    
    @JsonProperty
    private static int apolloPort;
    
    /*@Valid
    @NotNull
    @JsonProperty
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

	/**
     * @return the stompQueueName
     */
    public String getStompQueueName() {
	return stompQueueName;
    }

    /**
     * @param stompQueueName
     *            the stompQueueName to set
     */
    public void setStompQueueName(String stompQueueName) {
    	BigDataServiceConfiguration.stompQueueName = stompQueueName;
    }

    /**
     * @return the stompTopicName
     */
    public String getStompTopicName() {
	return stompTopicName;
    }

    /**
     * @param stompTopicName
     *            the stompTopicName to set
     */
    public void setStompTopicName(String stompTopicName) {
    	BigDataServiceConfiguration.stompTopicName = stompTopicName;
    }
    
    /**
     * @return the apolloUser
     */
    public String getApolloUser() {
		return apolloUser;
	}
    
    /**
     * @param apolloUser
     *            the apolloUser to set
     */
	public void setApolloUser(String apolloUser) {
		BigDataServiceConfiguration.apolloUser = apolloUser;
	}
	
	/**
     * @return the apolloPassword
     */
	public String getApolloPassword() {
		return apolloPassword;
	}
	
	/**
     * @param apolloPassword
     *            the apolloPassword to set
     */
	public void setApolloPassword(String apolloPassword) {
		BigDataServiceConfiguration.apolloPassword = apolloPassword;
	}
	
	/**
     * @return the apolloHost
     */
	public String getApolloHost() {
		return apolloHost;
	}
	
	/**
     * @param apolloHost
     *            the apolloHost to set
     */
	public void setApolloHost(String apolloHost) {
		BigDataServiceConfiguration.apolloHost = apolloHost;
	}
	
	/**
     * @return the apolloPort
     */
	public int getApolloPort() {
		return apolloPort;
	}
	
	/**
     * @param apolloPort
     *            the apolloPort to set
     */
	public void setApolloPort(int apolloPort) {
		BigDataServiceConfiguration.apolloPort = apolloPort;
	} 
	
	/**
     * @return the httpClient
     */
	/*public JerseyClientConfiguration getJerseyClientConfiguration() {
        return httpClient;
	}*/
    
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
