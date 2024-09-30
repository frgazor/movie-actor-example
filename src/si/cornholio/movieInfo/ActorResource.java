package si.cornholio.movieInfo;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/actors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActorResource {

	public ActorResource()
	{
		
	}
	
    @Inject
    private ActorDAO actorDAO;

    @GET
    public Response getAllActors(@QueryParam("page") @DefaultValue("1") int page,
	@QueryParam("size") @DefaultValue("10") int size) {
    	List<Actor> actors = actorDAO.findAll(page, size);
        
		long totalActors = actorDAO.countAllActors();
		int totalPages = (int) Math.ceil((double) totalActors / size);
		
		return Response.ok()
				.entity(actors)
				.header("X-Total-Count", totalActors)    
				.header("X-Total-Pages", totalPages)     
				.header("X-Current-Page", page)          
				.header("X-Page-Size", size)             
				.cacheControl(CacheControl.valueOf("max-age=30"))
				.build();		
    }

    @GET
    @Path("/{id}")
    public Response getActorById(@PathParam("id") String id) {
        Actor actor = actorDAO.findById(id);
        if (actor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        EntityTag etag = new EntityTag(Integer.toString(actor.hashCode()));

		 return Response.ok(actor)
	                .tag(etag)                                   
	                .cacheControl(CacheControl.valueOf("max-age=60"))
	                .build();
    }

    @POST
    public Response createActor(Actor actor) {
        actorDAO.create(actor);
        return Response.status(Response.Status.CREATED).entity(actor).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateActor(@PathParam("id") String id, Actor actor) {
        Actor existingActor = actorDAO.findById(id);
        if (existingActor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        actor.setActorId(id);
        actorDAO.update(actor);
        return Response.ok(actor).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteActor(@PathParam("id") String id) {
        actorDAO.delete(id);
        return Response.noContent().build();
    }
    
    @GET
    @Path("/{id}/picture")
    @Produces({"image/jpeg", "image/png"}) 
    @Transactional
    public Response getPicture(@PathParam("id") String id) {
        Actor actor = actorDAO.findById(id);
        if (actor == null || actor.getPicture() == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Actor or picture not found").build();
        }
        return Response.ok(actor.getPicture()).build();
    }    
    
    
/*    @POST
    @Path("/{id}/upload-picture")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response uploadPicture(@PathParam("id") String id, @Context InputStream uploadedInputStream) {

        try {
            // Convert InputStream to byte[]
            byte[] pictureBytes = convertInputStreamToByteArray(uploadedInputStream);

            // Update the actor's picture in the database
            Actor actor = actorDAO.findById(id);
            if (actor == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Actor not found").build();
            }

            actor.setPicture(pictureBytes);
            actorDAO.update(actor);

            return Response.status(Response.Status.OK).entity("Picture uploaded successfully").build();
        } catch (IOException e) {
            e.printStackTrace();S
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error uploading picture").build();
        }
    }    
    
    
    private byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }
    */
	@POST
	@Path("/insertTestData")
	public Response insertTestData() {
		Actor actor1 = new Actor();
		actor1.setFirstName("Vin");
		actor1.setLastName("Electric");
		actor1.setDateOfBirth(1966);

		Actor actor2 = new Actor();
		actor2.setFirstName("Old");
		actor2.setLastName("Shatterhand");
		actor2.setDateOfBirth(1845);

		actorDAO.create(actor1);
		actorDAO.create(actor2);

		return Response.ok("Test data inserted").build();
	}	    
}

