package com.myimdb.searchbook.controller;

import com.myimdb.searchbook.dto.ReportResponse;
import com.myimdb.searchbook.service.ReportService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/snapshot")
    public ReportResponse generateSnapshot() {
        return reportService.generateSnapshot();
    }
}
