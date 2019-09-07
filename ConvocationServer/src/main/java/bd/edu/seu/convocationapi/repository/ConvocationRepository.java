package bd.edu.seu.convocationapi.repository;

import bd.edu.seu.convocationapi.model.Convocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvocationRepository extends MongoRepository<Convocation, String> {
}
