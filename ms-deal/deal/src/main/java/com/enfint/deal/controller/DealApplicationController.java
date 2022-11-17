package com.enfint.deal.controller;

import com.enfint.deal.dto.LoanApplicationRequestDTO;
import com.enfint.deal.dto.LoanOfferDTO;
import com.enfint.deal.service.DealApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deal")
public class DealApplicationController {

    @Autowired
    private DealApplicationService dealApplicationService;

    @PostMapping("application")
    public ResponseEntity<List<LoanOfferDTO>> possibleCalculation(@RequestBody LoanApplicationRequestDTO loan)
    {
        return ResponseEntity.ok(dealApplicationService.possibleCalculation(loan));
    }
}
