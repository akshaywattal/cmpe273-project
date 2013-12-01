package edu.sjsu.cmpe.bigdata.api.resources;

import java.net.UnknownHostException;
import java.util.HashMap;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.NotFoundException;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.bigdata.domain.Book;
import edu.sjsu.cmpe.bigdata.domain.Review;
import edu.sjsu.cmpe.bigdata.dto.AuthorDto;
import edu.sjsu.cmpe.bigdata.dto.AuthorsDto;
import edu.sjsu.cmpe.bigdata.dto.BookDto;
import edu.sjsu.cmpe.bigdata.dto.LinkDto;
import edu.sjsu.cmpe.bigdata.dto.LinksDto;
import edu.sjsu.cmpe.bigdata.dto.ReviewDto;
import edu.sjsu.cmpe.bigdata.dto.ReviewsDto;
import edu.sjsu.cmpe.bigdata.dto.SentimentAnalysisDto;

/**
 * Main Analytics resource, contains API for all Big Data Analysis 
 */

@Path("/v1/analytics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnalyticsResource {
	
	/**
	 * API to get result of sentiment analysis
	 */
	@GET
    @Path("/sentiment")
    @Timed(name = "view-sentiment")
    public String viewSentiment() throws UnknownHostException {
		SentimentAnalysisDto sentimentAnalysisDto = new SentimentAnalysisDto();
		return sentimentAnalysisDto.getSentiment();
    }
}