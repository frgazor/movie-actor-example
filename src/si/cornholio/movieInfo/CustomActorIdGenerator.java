package si.cornholio.movieInfo;

import java.io.Serializable;

import javax.persistence.Query;
import javax.persistence.SequenceGenerator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

@SequenceGenerator(name = "actor_sequence", sequenceName = "ACTOR_SEQUENCE", allocationSize = 1)
public class CustomActorIdGenerator implements IdentifierGenerator {

    private static final String PREFIX = "nm";
    //private static final AtomicLong COUNTER = new AtomicLong(1);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        Query query = session.createNativeQuery("SELECT NEXTVAL('actor_sequence')");
        Long sequenceValue = ((Number) query.getSingleResult()).longValue();

        return PREFIX + String.format("%07d", sequenceValue);
    }    
    
//    @Override
//    public Serializable generate(SharedSessionContractImplementor session, Object object) {
//        long id = COUNTER.getAndIncrement();
//        return PREFIX + String.format("%07d", id);  
//    }
        
}
