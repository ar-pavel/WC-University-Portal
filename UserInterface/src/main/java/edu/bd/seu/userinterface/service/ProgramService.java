package edu.bd.seu.userinterface.service;

import edu.bd.seu.userinterface.model.Program;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProgramService {
    @Value("${programUrl}/programs")
    private String programUrl;
    private RestTemplate restTemplate;

    public ProgramService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Program insertProgram(Program program){
        return restTemplate.postForObject(programUrl, program, Program.class);
    }
    public Program getProgram(String id){
        return restTemplate.getForObject(programUrl + "/" + id, Program.class);
    }

    public List<Program> getPrograms( ){
        ResponseEntity<List<Program>> response = restTemplate.exchange(
                programUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Program>>(){});
        List<Program> programs = response.getBody();
        return programs;
    }
    public Program updateProgram( Program program){
        HttpEntity<Program> reqUpdate = new HttpEntity<>(program);
        restTemplate.exchange(programUrl + "/" + program.getName(), HttpMethod.PUT, reqUpdate, Program.class);
        return reqUpdate.getBody();
    }





}
