package com.enfint.deal.feign;

import com.enfint.deal.dto.LoanApplicationRequestDTO;
import com.enfint.deal.dto.LoanOfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeignService {

    @Autowired
    private FeignServiceUntil until;
    public List<LoanOfferDTO> offerDTOList(LoanApplicationRequestDTO loanApplicationRequestDTO)
    {
        List<LoanOfferDTO> loan=until.offers(loanApplicationRequestDTO);
        return loan;
    }
}
