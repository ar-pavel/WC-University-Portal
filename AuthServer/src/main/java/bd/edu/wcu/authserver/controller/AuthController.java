package bd.edu.wcu.authserver.controller;


import bd.edu.wcu.authserver.exception.ResourceAlreadyExistsException;
import bd.edu.wcu.authserver.model.Credential;
import bd.edu.wcu.authserver.model.LoginToken;
import bd.edu.wcu.authserver.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("")
    @ResponseBody
    public LoginToken authenticate(@RequestParam String username, @RequestParam String password) {
        return authService.authenticate(username, password);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<LoginToken> insertStudent(@RequestBody Credential credential) {
        try {
            System.err.println("Controller " + credential);

            LoginToken loginToken = credential.getLoginToken();
            String password = credential.getPassword();

            LoginToken authServiceUser = authService.createUser(loginToken, password);
            return ResponseEntity.status(HttpStatus.CREATED).body(authServiceUser);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

    }

    @GetMapping("")
    public ResponseEntity<List<LoginToken>> getAllLoginTokens(){
        List<LoginToken> loginTokenList = authService.findAll();
        return ResponseEntity.ok(loginTokenList);
    }
//    @GetMapping(value = "/{username}")
//    public ResponseEntity<LoginToken> getStudent(@PathVariable String username) {
//        try {
//            LoginToken loginToken = authService.findById(username);
//            return ResponseEntity.ok().body(loginToken);
//        } catch (ResourceDoesNotExistException e){
//            return ResponseEntity.notFound().build();
//        }
//    }
}


