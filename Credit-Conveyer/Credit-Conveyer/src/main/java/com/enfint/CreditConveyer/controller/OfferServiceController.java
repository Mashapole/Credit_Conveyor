package com.enfint.CreditConveyer.controller;

import com.enfint.CreditConveyer.dto.LoanApplicationRequestDTO;
import com.enfint.CreditConveyer.dto.LoanOfferDTO;
import com.enfint.CreditConveyer.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class OfferServiceController {

    @Autowired
    private OfferService offerService;

    @PostMapping("/loanOffers")
    public List<LoanOfferDTO> loanOffer(@RequestBody LoanApplicationRequestDTO requestDTO)
    {
        log.info("Loan Application Offer",requestDTO);
        return offerService.offers(requestDTO);
    }


}
