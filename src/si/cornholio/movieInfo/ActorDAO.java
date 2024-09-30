package si.cornholio.movieInfo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.List;

@Stateless
public class ActorDAO {

	public ActorDAO()
	{
		
	}
	
	@PersistenceContext(unitName = "moviePU")
	private EntityManager em;

	public List<Actor> findAll(int page, int size) {
		// Calculate the starting index
		int firstResult = (page - 1) * size;

		TypedQuery<Actor> query = em.createQuery("SELECT a FROM Actor a", Actor.class);
		query.setFirstResult(firstResult); // Start position
		query.setMaxResults(size);         // Number of results (page size)

		return query.getResultList();    	
	}

	public long countAllActors() {
		// Count total number of actors
		return em.createQuery("SELECT COUNT(a) FROM Actor a", Long.class).getSingleResult();
	}	     

	public Actor findById(String id) {
		return em.find(Actor.class, id);
	}

	public Actor create(Actor actor) {
		em.persist(actor);
		return actor;
	}

	public Actor update(Actor actor) {
		return em.merge(actor);
	}

	public void delete(String id) {
		Actor actor = em.find(Actor.class, id);
		if (actor != null) {
			em.remove(actor);
		}
	}
}
