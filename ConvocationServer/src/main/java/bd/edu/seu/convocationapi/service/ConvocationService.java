package bd.edu.seu.convocationapi.service;

import bd.edu.seu.convocationapi.exceptions.ResourceAlreadyExistsException;
import bd.edu.seu.convocationapi.exceptions.ResourceDoesNotExistException;
import bd.edu.seu.convocationapi.model.Convocation;
import bd.edu.seu.convocationapi.repository.ConvocationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ConvocationService {
    private ConvocationRepository convocationRepository;

    public ConvocationService(ConvocationRepository convocationRepository) {
        this.convocationRepository = convocationRepository;
    }

    public Convocation insertConvocation(Convocation convocation) throws ResourceAlreadyExistsException {
        Optional<Convocation> optionalConvocation = convocationRepository.findById(convocation.getStudentId());
        if (optionalConvocation.isPresent()) {
            throw new ResourceAlreadyExistsException(convocation.getStudentId());
        } else {
            return convocationRepository.save(convocation);
        }
    }

    public List<Convocation> findAll() {
        List<Convocation> convocationList = new ArrayList<>();
        convocationList.addAll((Collection<? extends Convocation>) convocationRepository.findAll());
        return convocationList;
    }

    public Convocation findById(String id) throws ResourceDoesNotExistException {
        Optional<Convocation> optionalConvocation = convocationRepository.findById(id);
        if (optionalConvocation.isPresent()) {
            return optionalConvocation.get();
        } else throw new ResourceDoesNotExistException(id);
    }

    public Convocation updateConvocation(String id, Convocation convocation) throws ResourceDoesNotExistException {
        Optional<Convocation> optionalConvocation = convocationRepository.findById(id);
        if (optionalConvocation.isPresent()) {
            convocation.setStudentId(id);
            return convocationRepository.save(convocation);
        } else {
            throw new ResourceDoesNotExistException(id);
        }
    }

    public boolean deleteById(String id) throws ResourceDoesNotExistException {
        Optional<Convocation> optionalConvocation = convocationRepository.findById(id);
        optionalConvocation.ifPresent(convocation -> convocationRepository.deleteById(id));
        optionalConvocation.orElseThrow(() -> new ResourceDoesNotExistException(id));
        return true;
    }

    public void deleteALL() {
        convocationRepository.deleteAll();
    }
}