package si.cornholio.movieInfo;

import java.io.Serializable;

import javax.persistence.Query;
import javax.persistence.SequenceGenerator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

@SequenceGenerator(name = "movie_sequence", sequenceName = "MOVIE_SEQUENCE", allocationSize = 1)
public class CustomMovieIdGenerator implements IdentifierGenerator {

    private static final String PREFIX = "tt";   
    //private static final AtomicLong COUNTER = new AtomicLong(1);
    
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        Query query = session.createNativeQuery("SELECT NEXTVAL('movie_sequence')");
        Long sequenceValue = ((Number) query.getSingleResult()).longValue();

        return PREFIX + String.format("%07d", sequenceValue);
    }
	

//    @Override
//    public Serializable generate(SharedSessionContractImplementor session, Object object) {
//        long id = COUNTER.getAndIncrement();
//        return PREFIX + String.format("%07d", id);
//    }
	
}
