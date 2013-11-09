package edu.sjsu.cmpe.bigdata.ui.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;
import edu.sjsu.cmpe.bigdata.ui.views.HomeView;


@Path("/")
@Produces(MediaType.TEXT_HTML)
public class HomeResource {
  //  private final BookRepositoryInterface bookRepository;

    public HomeResource() {
     //   this.bookRepository = bookRepository;
    }

    @GET
    public HomeView getHome() {
        return new HomeView();
    }
}