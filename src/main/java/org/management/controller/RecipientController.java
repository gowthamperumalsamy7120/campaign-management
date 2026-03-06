package org.management.controller;


import org.management.dto.RecipientDto;
import org.management.entity.Recipient;
import org.management.service.RecipientBulkUploadService;
import org.management.service.RecipientService;
import org.management.utils.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("recipients")
public class RecipientController {

    @Autowired
    private RecipientService service;
    @Autowired
    private RecipientBulkUploadService recipientBulkUploadService;

    @GetMapping("/{id}")
    public Optional<Recipient> retrieve(@PathVariable Integer id) {
        return this.service.retrieve(id);
    }

    @GetMapping
    public List<Recipient> list() {
        return this.service.list();
    }

    @PostMapping
    public ResponseModel add(@RequestBody RecipientDto dto) {
        return this.service.add(dto);
    }

    @PutMapping("/{id}")
    public ResponseModel update(@RequestBody RecipientDto dto, @PathVariable Integer id) {
        return this.service.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseModel delete(@PathVariable Integer id) {
        return this.service.delete(id);
    }

    @PostMapping(value = "excel-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel uploadRackExcel(@RequestParam("file") MultipartFile file) {
        return this.recipientBulkUploadService.uploadExcelData(file);
    }

}