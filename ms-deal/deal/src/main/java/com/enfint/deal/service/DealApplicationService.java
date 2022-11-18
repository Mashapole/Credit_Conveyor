package com.enfint.deal.service;

import com.enfint.deal.dto.ApplicationStatusHistoryDTO;
import com.enfint.deal.dto.LoanApplicationRequestDTO;
import com.enfint.deal.dto.LoanOfferDTO;
import com.enfint.deal.dto.enumm.ChangeType;
import com.enfint.deal.entity.Application;
import com.enfint.deal.entity.Client;
import com.enfint.deal.entity.model.ApplicationStatus;
import com.enfint.deal.entity.model.Passport;
import com.enfint.deal.feign.FeignServiceUntil;
import com.enfint.deal.repository.ApplicationRepository;
import com.enfint.deal.repository.ClientRepository;
import com.enfint.deal.repository.CreditRepositoty;
import com.enfint.deal.service.Implementations.Helper;
import com.enfint.deal.validation.validate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DealApplicationService implements Helper {


    @Autowired
    private CreditRepositoty creditRepositoty;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private FeignServiceUntil feignServiceUntil;

    @Override
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
        log.info("CLIENT TABLE WITH DATA:{}", storeClient);
        log.info("---------------------------------------");

        log.info("---------------------------------------");
        log.info("Application entity is created, and data is stored to database ");
        Application application=new Application();
        application.setClientId(storeClient);
        application.setCreationDate(LocalDate.now());

        Application store=applicationRepository.save(application);
        log.info("APPLICATION TABLE WITH DATA:{}", store);
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
            log.info("LoanOffer With ID:{}", loanOfferDTOS);
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

    @Override
    public void selectingOffer(LoanOfferDTO loanOfferDTO)
    {
        log.info("---------------------------------------");
        log.info("Selecting offer");

        Application application=applicationRepository.findById(loanOfferDTO.getApplicationId()).get();

        List<ApplicationStatusHistoryDTO> stat= new ArrayList<>();
        stat.add(new ApplicationStatusHistoryDTO(ApplicationStatus.APPROVED, LocalDateTime.now(), ChangeType.APPROVED));

        application.setStatus(ApplicationStatus.APPROVED);
        application.setStatusHistoryy(stat);
        application.setAppliedOffer("");
        final Application app=applicationRepository.save(application);
        log.info("Application Information is updated and saved to DB");
        log.info("TABLE DATA IS:{}", app);
        log.info("---------------------------------------");

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
