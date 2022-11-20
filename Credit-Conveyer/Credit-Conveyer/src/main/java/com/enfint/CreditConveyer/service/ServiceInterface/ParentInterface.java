package com.enfint.CreditConveyer.service.ServiceInterface;

import com.enfint.CreditConveyer.dto.CreditDTO;
import com.enfint.CreditConveyer.dto.LoanApplicationRequestDTO;
import com.enfint.CreditConveyer.dto.LoanOfferDTO;
import com.enfint.CreditConveyer.dto.ScoringDataDTO;

import java.util.List;

public interface ParentInterface {
     List<LoanOfferDTO> offers(LoanApplicationRequestDTO requestDTO);
     CreditDTO calculationService(ScoringDataDTO scoringDataDTO);
}
