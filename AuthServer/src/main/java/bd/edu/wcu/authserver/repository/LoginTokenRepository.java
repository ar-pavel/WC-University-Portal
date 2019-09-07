package bd.edu.wcu.authserver.repository;


import bd.edu.wcu.authserver.model.LoginToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginTokenRepository extends MongoRepository<LoginToken, String> {
}