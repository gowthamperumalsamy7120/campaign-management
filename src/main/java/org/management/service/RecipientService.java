package org.management.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.management.dto.RecipientDto;
import org.management.entity.Recipient;
import org.management.repository.RecipientRepository;
import org.management.utils.ResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RecipientService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RecipientRepository repository;

    @PostConstruct
    public void init() {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public List<Recipient> list() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public Optional<Recipient> retrieve(Integer id) {
        return repository.findById(id);
    }

    public ResponseModel add(RecipientDto dto) {
        Optional<Recipient> recipient = this.repository.findByEmail(dto.getEmail());
        if (recipient.isPresent()) {
            throw new RuntimeException(" Email " + dto.getEmail() + " already exists .");
        }
        Recipient entity = modelMapper.map(dto, Recipient.class);
        return new ResponseModel(true, "Added Successfully", repository.save(entity));
    }

    public ResponseModel update(RecipientDto dto, Integer id) {
        Optional<Recipient> optional = repository.findById(id);
        if (optional.isPresent()) {
            Recipient entity = optional.get();
            if (!dto.getEmail().equals(entity.getEmail())) {
                Optional<Recipient> recipient = this.repository.findByEmail(dto.getEmail());
                if (recipient.isPresent()) {
                    throw new RuntimeException(" Email " + dto.getEmail() + " already exists .");
                }
            }
            modelMapper.map(dto, entity);
            return new ResponseModel(true, "Updated Successfully", repository.save(entity));
        }
        throw new RuntimeException("Invalid ID");
    }

    @SneakyThrows
    public ResponseModel delete(Integer id) {
        repository.deleteById(id);
        return new ResponseModel(true, "Deleted Successfully");
    }
}