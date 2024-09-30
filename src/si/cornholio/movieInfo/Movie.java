package si.cornholio.movieInfo;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(generator = "custom-movie-id")
    @GenericGenerator(name = "custom-movie-id", strategy = "si.cornholio.movieInfo.CustomMovieIdGenerator")
    @Column(name = "movieid",nullable = false)
    private String movieId;

    @Column(name = "title")
    private String title;
    @Column(name = "genre")
    private String genre;
    @Column(name = "movieyear")
	private int movieYear;
    @Column(name = "description")
    private String description;    
    
    public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String id) {
		this.movieId = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getMovieYear() {
		return movieYear;
	}

	public void setMovieYear(int year) {
		this.movieYear = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "movie_actor",
        joinColumns = @JoinColumn(name = "movieid"),
        inverseJoinColumns = @JoinColumn(name = "actorid")
    )
    private List<Actor> actors;

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	
    public void addActor(Actor actor) {
        this.actors.add(actor);
        //actor.getMovies().add(this);
    }

    public void removeActor(Actor actor) {
        this.actors.remove(actor);
        //actor.getMovies().remove(this);
    }	

}
