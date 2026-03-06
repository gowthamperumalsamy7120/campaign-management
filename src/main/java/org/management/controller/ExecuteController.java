package org.management.controller;

import org.management.service.ExecuteCampaignService;
import org.management.utils.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("execute")
public class ExecuteController {
    @Autowired
    private ExecuteCampaignService service;

    @PostMapping("/{id}")
    public ResponseModel executeCampaign(@PathVariable Integer id) {
        return service.executeCampaign(id);
    }
}
