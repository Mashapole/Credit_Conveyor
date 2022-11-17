package com.enfint.deal.feign;

import com.enfint.deal.dto.LoanApplicationRequestDTO;
import com.enfint.deal.dto.LoanOfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/conveyer")
public class TestFeignController {

    @Autowired
    private FeignService service;

    /*This class is juss testing if fiegn client is working correct, seperatly*/
    @PostMapping("/loanOffers")
    public List<LoanOfferDTO> loanOfferDTOList(@RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO)
    {
        return service.offerDTOList(loanApplicationRequestDTO);
    }
}
