package edu.sjsu.cmpe.bigdata;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

//import edu.sjsu.cmpe.bigdata.api.resources.BookResource;
import edu.sjsu.cmpe.bigdata.api.resources.RootResource;
import edu.sjsu.cmpe.bigdata.config.BigDataServiceConfiguration;

public class BigDataService extends Service<BigDataServiceConfiguration> {

    public static void main(String[] args) throws Exception {
	new BigDataService().run(args);
    }

    @Override
    public void initialize(Bootstrap<BigDataServiceConfiguration> bootstrap) {
	bootstrap.setName("bigdata-service");
    }

    @Override
    public void run(BigDataServiceConfiguration configuration,
	    Environment environment) throws Exception {
	/** Root API */
	environment.addResource(RootResource.class);
	/** Books APIs */
	//environment.addResource(BookResource.class);
    }
}
