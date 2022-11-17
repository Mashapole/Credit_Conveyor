package com.enfint.CreditConveyer.controller;

import com.enfint.CreditConveyer.dto.CreditDTO;
import com.enfint.CreditConveyer.dto.LoanApplicationRequestDTO;
import com.enfint.CreditConveyer.dto.LoanOfferDTO;
import com.enfint.CreditConveyer.dto.ScoringDataDTO;
import com.enfint.CreditConveyer.service.OfferAndCalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/conveyer")
public class OfferAndCalculationController {

    @Autowired
    private OfferAndCalculationService services;

    @PostMapping("/loanOffers")
    public List<LoanOfferDTO> loanOffer(@RequestBody LoanApplicationRequestDTO requestDTO)
    {
        log.info("Loan Application Offer",requestDTO);
        return services.offers(requestDTO);
    }

    @PostMapping("/calc")
    public CreditDTO calculationService(@RequestBody ScoringDataDTO scoringDataDTO)
    {
        log.info("Calculation has started{}",scoringDataDTO);
        return services.calculationService(scoringDataDTO);
    }


}
