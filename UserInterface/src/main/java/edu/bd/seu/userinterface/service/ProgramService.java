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
        HttpEntity<Program> request = new HttpEntity<>(program);
        ResponseEntity<Program> response = restTemplate
                .exchange(programUrl, HttpMethod.POST, request, Program.class);
//        Program response = restTemplate.postForObject(programUrl, program, Program.class);
        return response.getBody();
    }
    public Program getProgram(String id){
        Program program = restTemplate.getForObject(programUrl + "/" + id, Program.class);
        return program;
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




}
