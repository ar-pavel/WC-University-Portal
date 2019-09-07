package bd.edu.wcu.authserver.service;

import bd.edu.wcu.authserver.model.LoginToken;
import bd.edu.wcu.authserver.repository.AuthRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {
    @Autowired
    AuthService authService;
    @Autowired
    AuthRepository authRepository;



    @Test
    public void createUser() {
        LoginToken authenticate = authService.authenticate("ABC", "ABCWCU");
        System.out.println(authenticate);


    }

    @Test
    public void findById() {
    }

    @Test
    public void findAll() {
        System.out.println(authService.findAll());
        System.out.println(authRepository.findAll());
    }

    @Test
    public void deleteALL() {
        authService.deleteALL();
    }
}