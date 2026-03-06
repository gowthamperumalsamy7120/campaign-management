package org.management.service;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.management.dto.CampaignDto;
import org.management.entity.Campaign;
import org.management.repository.CampaignRepository;
import org.management.utils.ResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public Optional<Campaign> retrieve(Integer id) {
        return campaignRepository.findById(id);
    }

    public List<Campaign> list() {
        return campaignRepository.findAll();
    }

    public ResponseModel add(CampaignDto dto) {
        Campaign campaign = modelMapper.map(dto, Campaign.class);
        campaignRepository.save(campaign);
        return new ResponseModel(true, "Added Successfully", campaign);
    }

    public ResponseModel update(CampaignDto dto, Integer id) {
        Optional<Campaign> optional = campaignRepository.findById(id);
        if (optional.isPresent()) {
            Campaign entity = optional.get();
            modelMapper.map(dto, entity);
            return new ResponseModel(true, "Updated Successfully", campaignRepository.save(entity));
        }
        throw new RuntimeException("Invalid ID");
    }

    @SneakyThrows
    public ResponseModel delete(Integer id) {
        campaignRepository.deleteById(id);
        return new ResponseModel(true, "Deleted Successfully");
    }

}