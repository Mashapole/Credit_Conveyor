package com.enfint.CreditConveyer.service;

import com.enfint.CreditConveyer.dto.CreditDTO;
import com.enfint.CreditConveyer.dto.LoanApplicationRequestDTO;
import com.enfint.CreditConveyer.dto.LoanOfferDTO;
import com.enfint.CreditConveyer.dto.ScoringDataDTO;
import com.enfint.CreditConveyer.model.PaymentScheduleElement;
import com.enfint.CreditConveyer.service.Calculation.ConveyerRateCalculation;
import com.enfint.CreditConveyer.service.Calculation.OfferMonthlyInstallment;
import com.enfint.CreditConveyer.service.ServiceInterface.ParentInterface;
import com.enfint.CreditConveyer.service.Calculation.OfferRateCalculation;
import com.enfint.CreditConveyer.validate.validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class OfferAndCalculationService implements ParentInterface
{


    @Override
    public List<LoanOfferDTO> offers(LoanApplicationRequestDTO requestDTO)
    {
        log.info("Loan Offer Data validation");
        validateOffer(requestDTO);
        log.info("Create Loan Offer Using Stream");
        return Stream.of(createLoanOffer(new LoanOfferDTO(),false,false,requestDTO),
                createLoanOffer(new LoanOfferDTO(),false,true,requestDTO),
                createLoanOffer(new LoanOfferDTO(),true,false,requestDTO),
                createLoanOffer(new LoanOfferDTO(),true,true,requestDTO)).collect(Collectors.toList());
    }

    public static void validateOffer(LoanApplicationRequestDTO reg)
    {
        validate v= new validate();
        log.info("---------------------------------");
        log.info("Offer Data Validation");
        if(!v.checkAmount(reg.getAmount()));

        if(!v.checkTerm(reg.getTerm()));

        if(!v.checkFirstName(reg.getFirstName()));

        if(!v.checkLastName(reg.getLastName()));

        if(!v.checkEmail(reg.getEmail()));

        if(!v.checkBirthdate(reg.getBirthdate()));

        if(!v.checkPassportSeries(reg.getPassportSeries()));

        if(!v.checkPassportNumber(reg.getPassportNumber()));
        log.info("---------------------------------");
        log.info("Data Validated");
        log.info("---------------------------------");
    }

    @Override
    public CreditDTO calculationService(ScoringDataDTO scoringDataDTO)
    {
        validateScoring(scoringDataDTO);
        log.info("Scoring data is validated");

        log.info("Set values to the CreditDTO");

        CreditDTO dto= new CreditDTO();
        //let's re-use offerRateCalculation class and method
        BigDecimal value=BigDecimal.valueOf(10);

        OfferRateCalculation offerRateCalculation= new OfferRateCalculation(scoringDataDTO.isInsuranceEnabled(),scoringDataDTO.isSalaryClient(),value);

        BigDecimal rate= ConveyerRateCalculation.scoringRate(offerRateCalculation.calculateRate(),scoringDataDTO);
        OfferMonthlyInstallment monthlyInstallment= new OfferMonthlyInstallment(scoringDataDTO.getAmount(),scoringDataDTO.getTerm(),rate);

        dto.setAmount(scoringDataDTO.getAmount());
        dto.setTerm(scoringDataDTO.getTerm());
        dto.setMonthlyPayment(monthlyInstallment.monthlyInstallment());
        dto.setRate(rate);
        dto.setPsk(monthlyInstallment.totalAmount());
        dto.setPaymentSchedule(paymentSchedule(dto));
        return dto;
    }

    private void validateScoring(ScoringDataDTO reg)
    {
        validate v= new validate();
        log.info("---------------------------------");
        log.info("Scoring Data Validation");
        if(!v.checkAmount(reg.getAmount()));
        if(!v.checkTerm(reg.getTerm()));
        if(!v.checkFirstName(reg.getFirstName()));
        if(!v.checkLastName(reg.getLastName()));
        if(!v.checkGender(reg.getGender()));
        if(!v.checkBirthdate(reg.getBirthdate()));
        if(!v.checkPassportSeries(reg.getPassportSeries()));
        if(!v.checkPassportNumber(reg.getPassportNumber()));
        if(!v.checkPassportIssueDate(reg.getPassportIssueDate()));
        if(!v.checkPassportBranch(reg.getPassportIssueBranch()));
        if(!v.checkMaritalStatus(reg.getMaritalStatus()));
        if(!v.checkDependentAmount(reg.getDependentAmount()));
        if(!v.checkEmploymentStatus(reg.getEmployment().getEmploymentStatus()));
        if(!v.checkValidAccount(reg.getAccount()));
        if(!v.checkEmployment(reg.getEmployment()));
        if(!v.checkINN(reg.getEmployment().getEmployerINN()));
        if(!v.checkSalary(reg.getEmployment().getSalary()));
        if(!v.checkPosition(reg.getEmployment().getPosition()));
        if(!v.checkWorkExprience(reg.getEmployment().getWorkExperienceTotal()));
        if(!v.checkCurrentWorkExprience(reg.getEmployment().getGetWorkExperienceCurrent()));
        log.info("---------------------------------");
        log.info("Data Validated");
        log.info("---------------------------------");
    }

    public List<PaymentScheduleElement> paymentSchedule(CreditDTO dto)
    {
        List<PaymentScheduleElement> elements=new ArrayList<>();
        final int number=0;
        final LocalDate date=LocalDate.now();
        final BigDecimal totalPayment=dto.getPsk();
        final BigDecimal interestPayment=calculateInterest(dto);
        final BigDecimal debtPayment=totalPayment.subtract(interestPayment);
        final BigDecimal remainingDebt=outstandingBalance(totalPayment,interestPayment,dto.getRate(),dto.getTerm(), dto.getMonthlyPayment());

        elements.add(new PaymentScheduleElement(number,date,totalPayment,interestPayment,debtPayment,remainingDebt));
        log.info("Payment Schedule element{}",elements);
        return elements;
    }

    private BigDecimal outstandingBalance(BigDecimal totalPayment, BigDecimal interestPayment, BigDecimal rate, Integer term, BigDecimal monthlyPayment) {
        //let's say user paid loan for 4 years

        int getTerm =(term-4)*12;
        double countMonth=0;

        for(int i=0;i<getTerm;i++)
        {
            countMonth+=monthlyPayment.doubleValue();
        }

        double balance=totalPayment.doubleValue()-countMonth;
        return BigDecimal.valueOf(balance);
    }

    private DecimalFormat format;
    public BigDecimal calculateInterest(CreditDTO dto)
    {
        format=new DecimalFormat("0.00");
        double interest=Double.valueOf(format.format(dto.getPsk().doubleValue()-dto.getAmount().doubleValue()));
        log.info("Interest To Pay{}",interest);
        return BigDecimal.valueOf(interest);
    }
    private LoanOfferDTO createLoanOffer(LoanOfferDTO loanOfferDTO, boolean isInsuranceEnabled, boolean isSalaryClient, LoanApplicationRequestDTO requestDTO) {

        log.info("Loan Offer created");
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

}
