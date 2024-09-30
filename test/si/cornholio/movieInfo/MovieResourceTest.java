import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import si.cornholio.movieInfo.Movie;
import si.cornholio.movieInfo.MovieDAO;
import si.cornholio.movieInfo.MovieResource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieResourceTest {

    @Mock
    private MovieDAO movieDAO;

    @InjectMocks
    private MovieResource movieResource;

    private Movie movie1;
    private Movie movie2;

    @Before
    public void setUp() {
        movie1 = new Movie();
        movie1.setMovieId("tt0000001");
        movie1.setTitle("Inception");
        movie1.setGenre("Sci-Fi");
        movie1.setMovieYear(2011);

        movie2 = new Movie();
        movie2.setMovieId("tt0000002");
        movie2.setTitle("The Dark Knight");
        movie2.setGenre("Action");
        movie1.setMovieYear(2008);
    }

//    @Test
//    public void testGetAllMovies() {
//    	ObjectMapper objectMapper = new ObjectMapper();     	
//    	
//        List<Movie> movies = Arrays.asList(movie1, movie2);
//        when(movieDAO.findAll(1,10)).thenReturn(movies);
//
//        List<Movie> result = null;
//        String json = movieResource.getAllMovies(1,10).readEntity(String.class);
//        try
//        {
//        	result = objectMapper.readValue(json, new TypeReference<List<Movie>>() {});
//        }
//        catch(Exception e)
//        {
//
//        }
//
//        assertEquals(2, result.size());
//        assertEquals("Inception", result.get(0).getTitle());
//        verify(movieDAO, times(1)).findAll(1,10);
//    }

    @Test
    public void testGetMovieById_Success() {
        when(movieDAO.findById("tt0000001")).thenReturn(movie1);

        Response response = movieResource.getMovieById("tt0000001");
        Movie result = (Movie) response.getEntity();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Inception", result.getTitle());
        verify(movieDAO, times(1)).findById("tt0000001");
    }

    @Test
    public void testGetMovieById_NotFound() {
        when(movieDAO.findById("tt0000003")).thenReturn(null);

        Response response = movieResource.getMovieById("tt0000003");

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(movieDAO, times(1)).findById("tt0000003");
    }

    @Test
    public void testCreateMovie() {
        when(movieDAO.create(any(Movie.class))).thenReturn(movie1);

        Response response = movieResource.createMovie(movie1);
        Movie result = (Movie) response.getEntity();

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("Inception", result.getTitle());
        verify(movieDAO, times(1)).create(movie1);
    }

    @Test
    public void testUpdateMovie_Success() {
        when(movieDAO.findById("tt0000001")).thenReturn(movie1);
        when(movieDAO.update(any(Movie.class))).thenReturn(movie1);

        Response response = movieResource.updateMovie("tt0000001", movie1);
        Movie result = (Movie) response.getEntity();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Inception", result.getTitle());
        verify(movieDAO, times(1)).update(movie1);
    }

    @Test
    public void testUpdateMovie_NotFound() {
        when(movieDAO.findById("tt0000003")).thenReturn(null);

        Response response = movieResource.updateMovie("tt0000003", movie1);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(movieDAO, times(1)).findById("tt0000003");
        verify(movieDAO, times(0)).update(any(Movie.class));
    }

    @Test
    public void testDeleteMovie_Success() {
        Response response = movieResource.deleteMovie("tt0000001");

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(movieDAO, times(1)).delete("tt0000001");
    }
}

