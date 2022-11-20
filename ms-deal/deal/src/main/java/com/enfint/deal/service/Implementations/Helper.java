package com.enfint.deal.service.Implementations;

import com.enfint.deal.dto.LoanApplicationRequestDTO;
import com.enfint.deal.dto.LoanOfferDTO;

import java.util.List;

public interface Helper {

    List<LoanOfferDTO> possibleCalculation(LoanApplicationRequestDTO reg);

    void selectingOffer(LoanOfferDTO loanOfferDTO);
}
