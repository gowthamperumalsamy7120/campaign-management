package org.management.controller;

import org.management.dto.DashboardDto;
import org.management.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dashboard")
public class DashboardController {
    @Autowired
    private DashboardService service;

    @GetMapping("/{campaignId}")
    public DashboardDto getDashboard(@PathVariable Integer campaignId) {
        return service.getDashboard(campaignId);
    }
}
