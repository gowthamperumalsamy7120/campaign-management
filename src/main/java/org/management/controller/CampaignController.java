package org.management.controller;

import org.management.dto.CampaignDto;
import org.management.entity.Campaign;
import org.management.service.CampaignService;
import org.management.utils.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping("/{id}")
    public Optional<Campaign> retrieve(@PathVariable Integer id) {
        return this.campaignService.retrieve(id);
    }

    @GetMapping
    public List<Campaign> list() {
        return campaignService.list();
    }

    @PostMapping
    public ResponseModel add(@RequestBody CampaignDto dto) {
        return campaignService.add(dto);
    }

    @PutMapping("/{id}")
    public ResponseModel update(@RequestBody CampaignDto dto, @PathVariable Integer id) {
        return this.campaignService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseModel delete(@PathVariable Integer id) {
        return this.campaignService.delete(id);
    }


}