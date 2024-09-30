package si.cornholio.movieInfo;

import javax.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

	public MovieResource()
	{
		
	}
	
	@Inject
	private MovieDAO movieDAO;

    @Inject
    private ActorDAO actorDAO;	
	
	@GET
	@Path("/list")	
	public Response getAllMovies(@QueryParam("page") @DefaultValue("1") int page,
			@QueryParam("size") @DefaultValue("10") int size) {
		// Get paginated movie list
		List<Movie> movies = movieDAO.findAll(page, size);

		// Get total number of movies for pagination metadata
		long totalMovies = movieDAO.countAllMovies();
		int totalPages = (int) Math.ceil((double) totalMovies / size);

				
		return Response.ok()
				.entity(movies)
				.header("X-Total-Count", totalMovies)    
				.header("X-Total-Pages", totalPages)     
				.header("X-Current-Page", page)          
				.header("X-Page-Size", size)             
				.cacheControl(CacheControl.valueOf("max-age=30"))
				.build();
	}

	@GET
	@Path("/{id}")
	public Response getMovieById(@PathParam("id") String id) {
		Movie movie = movieDAO.findById(id);
		if (movie == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		 EntityTag etag = new EntityTag(Integer.toString(movie.hashCode()));

		 return Response.ok(movie)
	                .tag(etag)                                   // Add ETag for validation
	                .cacheControl(CacheControl.valueOf("max-age=60")) // Cache for 60 seconds
	                .build();
	}

	@POST
	public Response createMovie(Movie movie) {
		movieDAO.create(movie);
		return Response.status(Response.Status.CREATED).entity(movie).build();
	}

	@PUT
	@Path("/{id}")
	public Response updateMovie(@PathParam("id") String id, Movie movie) {
		Movie existingMovie = movieDAO.findById(id);
		if (existingMovie == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		movie.setMovieId(id);
		movieDAO.update(movie);
		return Response.ok(movie).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteMovie(@PathParam("id") String id) {
		movieDAO.delete(id);
		return Response.noContent().build();
	}

    // add actor to a movie
    @POST
    @Path("/{movieId}/actors/{actorId}")
    public Response addActorToMovie(@PathParam("movieId") String movieId, @PathParam("actorId") String actorId) {
        Movie movie = movieDAO.findById(movieId);
        Actor actor = actorDAO.findById(actorId);

        if (movie == null || actor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        movie.addActor(actor);  
        movieDAO.update(movie); 

        return Response.ok(movie).build();
    }

    // remove actor from a movie
    @DELETE
    @Path("/{movieId}/actors/{actorId}")
    public Response removeActorFromMovie(@PathParam("movieId") String movieId, @PathParam("actorId") String actorId) {
        Movie movie = movieDAO.findById(movieId);
        Actor actor = actorDAO.findById(actorId);

        if (movie == null || actor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        movie.removeActor(actor);  // Remove the actor from the movie
        movieDAO.update(movie);    // Update the movie in the database

        return Response.ok(movie).build();
    }	
	
	@POST
	@Path("/insertTestData")
	public Response insertTestData() {
		Movie movie1 = new Movie();
		movie1.setTitle("Kekec nad samotnim breznom");
		movie1.setGenre("Action");
		movie1.setDescription("Kekec strikes back");
		movie1.setMovieYear(1950);

		Movie movie2 = new Movie();
		movie2.setTitle("Dumb and dumber");
		movie2.setGenre("Drama");
		movie2.setDescription("How low can you go?");
		movie2.setMovieYear(1994);

		movieDAO.create(movie1);
		movieDAO.create(movie2);

		return Response.ok("Test data inserted").build();
	}		
}

