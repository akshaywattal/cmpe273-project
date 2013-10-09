package edu.sjsu.cmpe.bigdata.domain;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	@JsonProperty
	private String username;
	
	@JsonProperty
	@NotEmpty
    private String email;
    
	@JsonProperty
	@NotEmpty
    private String password;
    
    /**
     * @return the username
     */
    public String getUsername() {
	return this.username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
	this.username = username;
    }
    
    /**
     * @return the email
     */
    public String getEmail() {
	return this.email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }
    
    /**
     * @return the password
     */
    public String getPassword() {
	return this.password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }	
	
}
