package edu.bd.seu.userinterface.service;

import edu.bd.seu.userinterface.model.Convocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ConvocationService {
    @Value("${convocationUrl}/convocations")
    private String convocationUrl;
    private RestTemplate restTemplate;

    public ConvocationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Convocation insertConvocation(Convocation convocation){
        return restTemplate.postForObject(convocationUrl, convocation, Convocation.class);
    }
    public Convocation getConvocation(String id){
        return restTemplate.getForObject(convocationUrl + "/" + id, Convocation.class);
    }

    public List<Convocation> getConvocations( ){
        ResponseEntity<List<Convocation>> response = restTemplate.exchange(
                convocationUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Convocation>>(){});
        List<Convocation> convocations = response.getBody();
        return convocations;
    }
    public Convocation updateConvocation( Convocation convocation){
        HttpEntity<Convocation> reqUpdate = new HttpEntity<>(convocation);
        restTemplate.exchange(convocationUrl + "/" + convocation.getId(), HttpMethod.PUT, reqUpdate, Convocation.class);
        return reqUpdate.getBody();
    }
}
