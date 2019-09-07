package edu.bd.seu.userinterface.service;

import edu.bd.seu.userinterface.model.Credential;
import edu.bd.seu.userinterface.model.LoginToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Exchanger;

@Service
public class AuthService {
    @Value("${authUrl}/auth")
    private String authUrl;
    private RestTemplate restTemplate;

    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LoginToken authenticate(String username, String password) {

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("username", username);
        multiValueMap.add("password", password);
        LoginToken loginToken = restTemplate.postForObject(authUrl, multiValueMap, LoginToken.class);

        return loginToken;
    }

    public LoginToken createCredential( LoginToken loginToken,String name, String password){
//        HttpEntity<LoginToken> reqUpdate = new HttpEntity<>(loginToken);
//        restTemplate.exchange(authUrl + "/" + password, HttpMethod.POST, reqUpdate, LoginToken.class);
//        return reqUpdate.getBody();
        Credential credential = new Credential(loginToken, name, password);
        System.err.println("Auth service" + loginToken);
        return restTemplate.postForObject(authUrl+ "/create", credential, LoginToken.class);

    }
}
