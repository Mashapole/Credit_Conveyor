package com.enfint.CreditConveyer.service;

import com.enfint.CreditConveyer.dto.LoanApplicationRequestDTO;
import com.enfint.CreditConveyer.dto.LoanOfferDTO;
import com.enfint.CreditConveyer.exception.ApiException;
import com.enfint.CreditConveyer.service.LoanOfferCalculation.OfferMonthlyInstallment;
import com.enfint.CreditConveyer.service.ServiceInterface.OfferInterface;
import com.enfint.CreditConveyer.service.LoanOfferCalculation.OfferRateCalculation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class OfferService implements OfferInterface
{
    @Override
    public List<LoanOfferDTO> offers(LoanApplicationRequestDTO requestDTO)
    {
        log.info("Data validation");
        validateName(requestDTO.getFirstName());
        validateSurname(requestDTO.getLastName());
        validateEmail(requestDTO.getEmail());
        validateDatebirth(requestDTO.getBirthdate());
        validatePassportNumber(requestDTO.getPassportNumber());
        validatePassportSeries(requestDTO.getPassportSeries());
        validateLoanAmount(requestDTO.getAmount());
        log.info("Create Loan Offer Using Stream");
        return Stream.of(createLoanOffer(new LoanOfferDTO(),false,false,requestDTO),
                createLoanOffer(new LoanOfferDTO(),false,true,requestDTO),
                createLoanOffer(new LoanOfferDTO(),true,false,requestDTO),
                createLoanOffer(new LoanOfferDTO(),true,true,requestDTO)).collect(Collectors.toList());
    }


    private void validatePassportNumber(String passportNumber)
    {
        if(String.valueOf(passportNumber).equals("") || String.valueOf(passportNumber).isEmpty() || String.valueOf(passportNumber)==null)
        {

            throw new ApiException("Passport number is required");
        }
        else {
            //or we can use [0-9]+
            if(Pattern.matches("[\\d]{4}",passportNumber))
            {
               log.info("Passport number is Correct");
            }
            else
            {
                throw new ApiException("Passport number is incorrect");
            }
        }
    }

    private void validatePassportSeries(String passportSeries) {
        if(String.valueOf(passportSeries).equals("") || String.valueOf(passportSeries).isEmpty() || String.valueOf(passportSeries)==null)
        {

            throw new ApiException("passport series number is required");
        }
        else {
            //or we can use [0-9]+
            //String.valueOf(passportSeries).length()==6
            if(Pattern.matches("[\\d]{6}",passportSeries))
            {
                log.info("passport series is Correct");
            }
            else
            {
                throw new ApiException("passport series is incorrect");
            }
        }
    }

    private void validateEmail(String email)
    {
        if(!email.isEmpty() || !email.equals("") || email!=null)
        {
            //on the email I used to avoid illegal escape character
            if(Pattern.matches("[\\w\\.]{2,50}@[\\w\\.]{2,20}",email))
            {
                log.info("Email Is correct");
            }
            else
            {
                throw new ApiException("Email is not in correct format");

            }
        }
        else {
            throw new ApiException("Email is required");
        }
    }

    private void validateDatebirth(LocalDate birthdate)
    {
      if(String.valueOf(birthdate)==null || String.valueOf(birthdate).equals("") || String.valueOf(birthdate).isEmpty())
      {
          throw new ApiException("date of birth is required");
      }
      else {
          Date date;
          SimpleDateFormat simpleDateFormat;

          try
          {
              simpleDateFormat=new SimpleDateFormat("YYYY-MM-DD");
              date=simpleDateFormat.parse(String.valueOf(birthdate));
              log.info("Date is correct");
          }
          catch(Exception ex)
          {
              log.info("Error: "+ex.toString());
              throw new ApiException("Date of birth is in wrong format-required(yyyy-mm-dd)");
          }

          if(Period.between(birthdate, LocalDate.now()).getYears()>=18)
          {
              log.info("Age is Correct");
          }
          else {
              throw new ApiException("Age must be greater than 18");
          }

      }
    }

    private void validateLoanAmount(BigDecimal amount)
    {
        if(String.valueOf(amount).equals("") || String.valueOf(amount)==null || String.valueOf(amount).isEmpty())
        {
            throw new ApiException("Amount is required");
        }
        else
        {
            if(amount.compareTo(BigDecimal.valueOf(10000))>0)
            {
                log.info("Amount is correct");
            }
            else
            {
                throw new ApiException("Amount it muss be greater tha 10 000");
            }
        }
    }

    private void validateName(String firstName)
    {
        if(firstName.isEmpty() || firstName.equals("") || firstName==null)
        {
            throw new ApiException("first name required.");
        }
        else
        {
            if(firstName.length()>2 && firstName.length()<30)
            {
                if(firstName.matches("^[a-zA-Z]*$"))
                {
                    log.info("First Name is correct");
                }
                else
                {
                    throw new ApiException("First Name format is incorrect");
                }
            }
            else
            {
                throw new ApiException("Length of first name must be between 2-30");
            }
        }
    }
    private void validateSurname(String lastName) {

        if(lastName.isEmpty() || lastName.equals("") || lastName==null)
        {
            throw new ApiException("last name required.");
        }
        else
        {
            if(lastName.length()>2 && lastName.length()<30)
            {
                if(lastName.matches("^[a-zA-Z]*$"))
                {
                    log.info("last Name is correct");
                }
                else
                {
                    throw new ApiException("last Name format is incorrect");
                }
            }
            else
            {
                throw new ApiException("Length of last name must be between 2-30");
            }
        }
    }


    private LoanOfferDTO createLoanOffer(LoanOfferDTO loanOfferDTO, boolean isInsuranceEnabled, boolean isSalaryClient, LoanApplicationRequestDTO requestDTO) {

        BigDecimal value=BigDecimal.valueOf(10);
        OfferRateCalculation rateCalculation= new OfferRateCalculation(isInsuranceEnabled,isSalaryClient,value);
        OfferMonthlyInstallment offerMonthlyInstallment= new OfferMonthlyInstallment(requestDTO.getAmount(),requestDTO.getTerm(),rateCalculation.calculateRate());

        loanOfferDTO.setRequestAmount(requestDTO.getAmount());
        loanOfferDTO.setTotalAmount(offerMonthlyInstallment.totalAmount());
        loanOfferDTO.setTerm(requestDTO.getTerm());
        loanOfferDTO.setMonthlyPayment(offerMonthlyInstallment.monthlyInstallment());
        loanOfferDTO.setRate(rateCalculation.calculateRate());

        loanOfferDTO.setInsuranceEnabled(isInsuranceEnabled);
        loanOfferDTO.setSalaryClient(isSalaryClient);
        return  loanOfferDTO;
    }
    private BigDecimal totalAmount(Integer term, BigDecimal monthInstallment) {

        log.info("Total Amount Of Money");
        return monthInstallment.multiply(BigDecimal.valueOf(term));
    }
}
