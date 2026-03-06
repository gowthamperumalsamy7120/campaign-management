package org.management.controller;

import org.management.dto.EmailConfigurationDto;
import org.management.entity.EmailConfiguration;
import org.management.repository.EmailConfigurationRepository;
import org.management.service.EmailConfigurationService;
import org.management.utils.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("email")
public class EmailConfigurationController {
    @Autowired
    private EmailConfigurationService service;
    @Autowired
    private EmailConfigurationRepository repository;

    @GetMapping
    public List<EmailConfiguration> list() {
        return service.list();
    }

    @GetMapping("{id}")
    public Optional<EmailConfiguration> retrieve(@PathVariable Integer id) {
        return repository.findById(id);
    }

    @PostMapping
    public ResponseModel add(@RequestBody EmailConfigurationDto emailConfigurationDto) {
        EmailConfiguration emailConfigurations = service.add(emailConfigurationDto);
        return new ResponseModel<>(true, "Added Successfully", emailConfigurations);
    }

    @PutMapping("{id}")
    public ResponseModel update(@RequestBody EmailConfigurationDto emailConfigurationDto, Integer id) {
        return service.update(emailConfigurationDto, id);
    }

    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable Integer id) {
        return service.delete(id);
    }

    @PostMapping("send-Mail")
    public ResponseModel sendMail(@RequestParam(required = false) String subject, @RequestParam(required = false) String body, @RequestParam(required = false) String toAddress) {
        return service.sendEmail(subject, body, toAddress);
    }
}
