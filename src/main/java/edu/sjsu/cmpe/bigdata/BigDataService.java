package edu.sjsu.cmpe.bigdata;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;



import com.yammer.dropwizard.views.ViewBundle;




import de.spinscale.dropwizard.jobs.JobsBundle;
//import edu.sjsu.cmpe.bigdata.api.resources.BookResource;
import edu.sjsu.cmpe.bigdata.api.resources.UserResource;
import edu.sjsu.cmpe.bigdata.config.BigDataServiceConfiguration;
import edu.sjsu.cmpe.bigdata.stomp.ApolloSTOMP;
import edu.sjsu.cmpe.bigdata.ui.resources.DashboardResource;
import edu.sjsu.cmpe.bigdata.ui.resources.HomeResource;

public class BigDataService extends Service<BigDataServiceConfiguration> {

    public static void main(String[] args) throws Exception {
	new BigDataService().run(args);
    }

    @Override
    public void initialize(Bootstrap<BigDataServiceConfiguration> bootstrap) {
	bootstrap.setName("bigdata-service");
	bootstrap.addBundle(new AssetsBundle());
	bootstrap.addBundle(new ViewBundle());
	bootstrap.addBundle(new JobsBundle("edu.sjsu.cmpe.bigdata"));
    }

    @Override
    public void run(BigDataServiceConfiguration configuration,
	    Environment environment) throws Exception {
    	/*
         * Pulling Configurations from config file
         */
        String queueName = configuration.getStompQueueName();
        String topicName = configuration.getStompTopicName();
        String apolloUser = configuration.getApolloUser();
        String apolloPassword = configuration.getApolloPassword();
        String apolloHost = configuration.getApolloHost();
        int apolloPort = configuration.getApolloPort();
        
        /*
         * Adding the Messaging Layer and Declaring its instance
         */
        ApolloSTOMP apolloSTOMP = new  ApolloSTOMP(apolloUser, apolloPassword, apolloHost, apolloPort, queueName, topicName);
        
        /** Root API */
		environment.addResource(UserResource.class);
		/** Books APIs */
		//environment.addResource(BookResource.class);	
		/** UI API */
		environment.addResource(new HomeResource());
		environment.addResource(new DashboardResource());
	    }
}
