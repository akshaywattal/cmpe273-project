package edu.sjsu.cmpe.bigdata.ui.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.sjsu.cmpe.bigdata.ui.views.DashboardView;

@Path("/dashboard")
@Produces(MediaType.TEXT_HTML)
public class DashboardResource {
//  private final BookRepositoryInterface bookRepository;

    public DashboardResource() {
     //   this.bookRepository = bookRepository;
    }

    @GET
    public DashboardView getDashboard() {
        return new DashboardView();
    }
}
