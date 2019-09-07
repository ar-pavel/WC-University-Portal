package bd.edu.wcu.authserver.service;

import bd.edu.wcu.authserver.exception.ResourceAlreadyExistsException;
import bd.edu.wcu.authserver.exception.ResourceDoesNotExistException;
import bd.edu.wcu.authserver.model.LoginToken;
import bd.edu.wcu.authserver.model.User;
import bd.edu.wcu.authserver.repository.AuthRepository;
import bd.edu.wcu.authserver.repository.LoginTokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private AuthRepository authRepository;
    private LoginTokenRepository loginTokenRepository;

    public AuthService(AuthRepository authRepository, LoginTokenRepository loginTokenRepository) {
        this.authRepository = authRepository;
        this.loginTokenRepository = loginTokenRepository;
    }

    public LoginToken authenticate(String username, String password) {
        Optional<User> user = authRepository.findById(username);

        if (!user.isPresent()  )
            return new LoginToken(null, null, null, "norole");
        else if(user.isPresent()){
            if(!user.get().getPassword().equals(password))
                return new LoginToken(null, null, null, "norole");
        }

        Optional<LoginToken> loginToken = loginTokenRepository.findById(username);
        return loginToken.orElseGet(() -> new LoginToken(null, null, null, "norole"));
    }

    public LoginToken createUser(LoginToken loginToken, String password) throws ResourceAlreadyExistsException {
        System.err.println(loginToken);
//        Optional<LoginToken> optionalLoginToken = loginTokenRepository.findById(loginToken.getUsername());
        Optional<User> user = authRepository.findById(loginToken.getUsername());
        if (user.isPresent()) {
            throw new ResourceAlreadyExistsException(loginToken.getUsername());
        } else {
            authRepository.save(new User(loginToken.getUsername(), password));
            LoginToken savedToken = loginTokenRepository.save(loginToken);
            return savedToken;
        }
    }

    public LoginToken updateUser(LoginToken loginToken, String password)throws ResourceDoesNotExistException {
        Optional<LoginToken> optionalLoginToken = loginTokenRepository.findById(loginToken.getUsername());
        if(optionalLoginToken.isPresent()){
            LoginToken savedToken = loginTokenRepository.save(loginToken);
            authRepository.save(new User(loginToken.getUsername(), password));
            return savedToken;
        }
        else
            throw new ResourceDoesNotExistException(loginToken.getUsername());
    }

    public LoginToken findById(String username) throws ResourceDoesNotExistException {
        Optional<LoginToken> optionalLoginToken = loginTokenRepository.findById(username);
        if (optionalLoginToken.isPresent()) {
            return optionalLoginToken.get();
        } else throw new ResourceDoesNotExistException(username);
    }

    public List<LoginToken> findAll(){
        return loginTokenRepository.findAll();
    }


    public boolean deleteById(String username) throws ResourceDoesNotExistException {
        if (loginTokenRepository.findById(username).isPresent()) {
            loginTokenRepository.deleteById(username);
            authRepository.deleteById(username);
        } else {
            throw new ResourceDoesNotExistException(username);
        }
        return true;
    }


    public void deleteALL() {
        loginTokenRepository.deleteAll();
        authRepository.deleteAll();
    }

}