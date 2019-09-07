package bd.edu.seu.convocationapi.controller;

import bd.edu.seu.convocationapi.exceptions.ResourceAlreadyExistsException;
import bd.edu.seu.convocationapi.exceptions.ResourceDoesNotExistException;
import bd.edu.seu.convocationapi.model.Convocation;
import bd.edu.seu.convocationapi.service.ConvocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/convocations")
public class ConvocationController {
    private ConvocationService convocationService;

    public ConvocationController(ConvocationService convocationService) {
        this.convocationService = convocationService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Convocation> insertConvocation(@RequestBody Convocation convocation) {
        try {
            Convocation insertedConvocation = convocationService.insertConvocation(convocation);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedConvocation);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/{code}")
    public ResponseEntity<Convocation> getConvocation(@PathVariable String code) {
        try {
            Convocation convocation = convocationService.findById(code);
            return ResponseEntity.ok(convocation);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Convocation>> getConvocation() {
        return ResponseEntity.ok().body(convocationService.findAll());
    }

    @PutMapping(value = "")
    public ResponseEntity<Convocation> updateConvocation(@RequestBody String code, @RequestBody Convocation convocation) {
        try {
            Convocation updateConvocation = convocationService.updateConvocation(code, convocation);
            return ResponseEntity.ok().body(convocation);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{code}")
    public ResponseEntity<String> deleteConvocation(@PathVariable String code) {
        try {
            boolean deleted = convocationService.deleteById(code);
            return ResponseEntity.ok(code);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

}


