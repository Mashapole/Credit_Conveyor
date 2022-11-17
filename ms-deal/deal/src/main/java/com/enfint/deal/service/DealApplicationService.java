package com.enfint.deal.service;

import com.enfint.deal.dto.LoanApplicationRequestDTO;
import com.enfint.deal.dto.LoanOfferDTO;
import com.enfint.deal.entity.Application;
import com.enfint.deal.entity.Client;
import com.enfint.deal.entity.Credit;
import com.enfint.deal.entity.model.Passport;
import com.enfint.deal.feign.FeignServiceUntil;
import com.enfint.deal.repository.ApplicationRepository;
import com.enfint.deal.repository.ClientRepository;
import com.enfint.deal.repository.CreditRepositoty;
import com.enfint.deal.validation.validate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DealApplicationService {


    @Autowired
    private CreditRepositoty creditRepositoty;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private FeignServiceUntil feignServiceUntil;

    public List<LoanOfferDTO> possibleCalculation(@RequestBody LoanApplicationRequestDTO reg)
    {


        //I always prefer to validate data
        validationData(reg);
        Client createClient=createClient(reg);

        //let's store data to database
        log.info("---------------------------------------");
        log.info("Client entity is created, and data is stored to database ");
        Client storeClient=clientRepository.save(createClient);
        log.info("Execution Completed");
        log.info("---------------------------------------");

        log.info("---------------------------------------");
        log.info("Client entity is created, and data is stored to database ");
        Application application=new Application();
        application.setClientId(storeClient);
        application.setCreationDate(LocalDate.now());

        Application store=applicationRepository.save(application);
        log.info("Execution Completed");
        log.info("---------------------------------------");


        log.info("---------------------------------------");
        log.info("Post Request is sent using Feign");
        List<LoanOfferDTO> loanOfferDTOS=feignServiceUntil.offers(reg);
        log.info("Execution Completed");
        log.info("---------------------------------------");

        log.info("---------------------------------------");
        log.info("Assign LoanOffer With ID");
        if(loanOfferDTOS!=null)
        {
            loanOfferDTOS.forEach(loanOfferDTO -> loanOfferDTO.setApplicationId(application.getApplicationId()));
        }
        else
        {
            log.info("---------------------------------------");
            log.info("LoanOfferDTO is Empty");
            log.info("Execution Completed");
            log.info("---------------------------------------");
        }
        log.info("Execution Completed");
        log.info("---------------------------------------");

        return loanOfferDTOS;
    }
    private Client createClient(LoanApplicationRequestDTO reg)
    {
        Client client= new Client();
        client.setLastName(reg.getLastName());
        client.setFirstName(reg.getFirstName());
        client.setMiddleName(reg.getMiddleName());
        client.setBirthDate(reg.getBirthdate());
        client.setEmail(reg.getEmail());
        client.setPassport(new Passport(reg.getPassportSeries(),reg.getPassportNumber()));

        return client;
    }

    private void validationData(LoanApplicationRequestDTO reg)
    {
        log.info("---------------------------------");
        log.info("Data Validation");
        if(!validate.checkAmount(reg.getAmount()));

        if(!validate.checkTerm(reg.getTerm()));

        if(!validate.checkFirstName(reg.getFirstName()));

        if(!validate.checkLastName(reg.getLastName()));

        if(!validate.checkEmail(reg.getEmail()));

        if(!validate.checkBirthdate(reg.getBirthdate()));

        if(!validate.checkPassportSeries(reg.getPassportSeries()));

        if(!validate.checkPassportNumber(reg.getPassportNumber()));
        log.info("---------------------------------");
        log.info("Data Validated");
        log.info("---------------------------------");
    }
}
