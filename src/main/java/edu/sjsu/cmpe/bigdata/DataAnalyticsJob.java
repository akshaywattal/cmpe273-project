package edu.sjsu.cmpe.bigdata;

import javax.jms.Connection;


import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;


@Every("30s")
public class DataAnalyticsJob extends Job{
        @Override
        public void doJob() {
        	System.out.println("In Job");
            }
        }
