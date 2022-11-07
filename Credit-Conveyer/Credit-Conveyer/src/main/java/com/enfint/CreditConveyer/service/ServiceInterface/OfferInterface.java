package com.enfint.CreditConveyer.service.ServiceInterface;

import com.enfint.CreditConveyer.dto.LoanApplicationRequestDTO;
import com.enfint.CreditConveyer.dto.LoanOfferDTO;

import java.util.List;

public interface OfferInterface {
     List<LoanOfferDTO> offers(LoanApplicationRequestDTO requestDTO);
}
