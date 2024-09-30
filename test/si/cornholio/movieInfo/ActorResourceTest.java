import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import si.cornholio.movieInfo.Actor;
import si.cornholio.movieInfo.ActorDAO;
import si.cornholio.movieInfo.ActorResource;
import si.cornholio.movieInfo.Movie;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ActorResourceTest {

    @Mock
    private ActorDAO actorDAO;

    @InjectMocks
    private ActorResource actorResource;

    private Actor actor1;
    private Actor actor2;

    @Before
    public void setUp() {
        actor1 = new Actor();
        actor1.setActorId("nm0000001");
        actor1.setFirstName("Leonardo");
        actor1.setLastName("Di Caprio");
        actor1.setDateOfBirth(1975);

        actor2 = new Actor();
        actor2.setActorId("nm0000002");
        actor2.setFirstName("Christian");
        actor2.setLastName("Bale");
        actor2.setDateOfBirth(1970);
    }

//    @Test
//    public void testGetAllActors() {
//    	ObjectMapper objectMapper = new ObjectMapper(); 
//    	
//        List<Actor> actors = Arrays.asList(actor1, actor2);
//        when(actorDAO.findAll(1,10)).thenReturn(actors);
//
//        List<Actor> result = null;
//        //result = actorResource.getAllActors(1,10);
//        String json = actorResource.getAllActors(1,10).readEntity(String.class);
//        try
//        {
//        	result = objectMapper.readValue(json, new TypeReference<List<Actor>>() {});
//        }
//        catch(Exception e)
//        {
//
//        }
//        
//        
//        assertEquals(2, result.size());
//        assertEquals("Leonardo", result.get(0).getFirstName());
//        verify(actorDAO, times(1)).findAll(1,10);
//    }

    @Test
    public void testGetActorById_Success() {
        when(actorDAO.findById("nm0000001")).thenReturn(actor1);

        Response response = actorResource.getActorById("nm0000001");
        Actor result = (Actor) response.getEntity();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Leonardo", result.getFirstName());
        verify(actorDAO, times(1)).findById("nm0000001");
    }

    @Test
    public void testGetActorById_NotFound() {
        when(actorDAO.findById("nm0000003")).thenReturn(null);

        Response response = actorResource.getActorById("nm0000003");

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(actorDAO, times(1)).findById("nm0000003");
    }

    @Test
    public void testCreateActor() {
        when(actorDAO.create(any(Actor.class))).thenReturn(actor1);

        Response response = actorResource.createActor(actor1);
        Actor result = (Actor) response.getEntity();

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("Leonardo", result.getFirstName());
        verify(actorDAO, times(1)).create(actor1);
    }

    @Test
    public void testUpdateActor_Success() {
        when(actorDAO.findById("nm0000001")).thenReturn(actor1);
        when(actorDAO.update(any(Actor.class))).thenReturn(actor1);

        Response response = actorResource.updateActor("nm0000001", actor1);
        Actor result = (Actor) response.getEntity();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Leonardo", result.getFirstName());
        verify(actorDAO, times(1)).update(actor1);
    }

    @Test
    public void testUpdateActor_NotFound() {
        when(actorDAO.findById("nm0000003")).thenReturn(null);

        Response response = actorResource.updateActor("nm0000003", actor1);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(actorDAO, times(1)).findById("nm0000003");
        verify(actorDAO, times(0)).update(any(Actor.class));
    }

    @Test
    public void testDeleteActor_Success() {
        Response response = actorResource.deleteActor("nm0000001");

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(actorDAO, times(1)).delete("nm0000001");
    }
}

