package bd.edu.wcu.authserver.repository;

import bd.edu.wcu.authserver.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends MongoRepository<User, String> {
}
