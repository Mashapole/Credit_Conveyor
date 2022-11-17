package com.enfint.deal.feign;

import com.enfint.deal.dto.LoanApplicationRequestDTO;
import com.enfint.deal.dto.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value="clientData",url="http://localhost:8080/conveyer")
public interface FeignServiceUntil {
    @PostMapping("/loanOffers")
    List<LoanOfferDTO> offers(@RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO);
}
