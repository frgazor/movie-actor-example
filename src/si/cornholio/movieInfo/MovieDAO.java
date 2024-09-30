package si.cornholio.movieInfo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import java.util.List;

@Stateless
public class MovieDAO {

	public MovieDAO()
	{
		
	}
	
	@PersistenceContext(unitName = "moviePU")
	private EntityManager em;

	public List<Movie> findAll(int page, int size) {
		// Calculate the starting index
		int firstResult = (page - 1) * size;

		TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m", Movie.class);
		query.setFirstResult(firstResult); // Start position
		query.setMaxResults(size);         // Number of results (page size)

		return query.getResultList();
	}

	public long countAllMovies() {
		// Count total number of movies
		return em.createQuery("SELECT COUNT(m) FROM Movie m", Long.class).getSingleResult();
	}	 

	public Movie findById(String id) {
		return em.find(Movie.class, id);
	}

	public Movie create(Movie movie) {
		em.persist(movie);
		return movie;
	}

	@Transactional
	public Movie update(Movie movie) {
		return em.merge(movie);
	}

	public void delete(String id) {
		Movie movie = em.find(Movie.class, id);
		if (movie != null) {
			em.remove(movie);
		}
	}
}
