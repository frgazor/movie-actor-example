package si.cornholio.movieInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "actor")
public class Actor {

    @Id
    @GeneratedValue(generator = "custom-actor-id")
    @GenericGenerator(name = "custom-actor-id", strategy = "si.cornholio.movieInfo.CustomActorIdGenerator")
    @Column(name = "actorid", nullable = false)
    private String actorId;

    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
	private String lastName;
    @Column(name = "dateofbirth")
    private int dateOfBirth; 
        
    @Lob
    @Column(name = "picture",columnDefinition = "BLOB")
    private byte[] picture; // Store the picture as binary data (BLOB)
    
    public String getActorId() {
		return actorId;
	}


	public void setActorId(String actorId) {
		this.actorId = actorId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public int getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(int dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }	

//	public List<Movie> getMovies() {
//		return movies;
//	}
//
//	public void setMovies(List<Movie> movies) {
//		this.movies = movies;
//	}
//	
//    public void addMovie(Movie movie) {
//        this.movies.add(movie);
//        movie.getActors().add(this);
//    }
//
//    public void removeMovie(Movie movie) {
//        this.movies.remove(movie);
//        movie.getActors().remove(this);
//    }	
//
//    @ManyToMany(mappedBy = "actors")
//    private List<Movie> movies;
}
