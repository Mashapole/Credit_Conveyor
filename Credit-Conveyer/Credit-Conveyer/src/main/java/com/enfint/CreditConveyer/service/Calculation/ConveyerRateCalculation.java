package com.enfint.CreditConveyer.service.Calculation;


import com.enfint.CreditConveyer.dto.modelenum.EmploymentStatus;
import com.enfint.CreditConveyer.dto.modelenum.Gender;
import com.enfint.CreditConveyer.dto.modelenum.MaritalStatus;
import com.enfint.CreditConveyer.dto.modelenum.Position;
import com.enfint.CreditConveyer.dto.ScoringDataDTO;
import com.enfint.CreditConveyer.exception.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;


@Slf4j
public class ConveyerRateCalculation {



    public static BigDecimal storeRate=BigDecimal.ZERO;
    public static BigDecimal scoringRate(BigDecimal calculateRate, ScoringDataDTO scoringDataDTO)
    {
        //let's add the rate for isInsurance and isClient
        storeRate=storeRate.add(calculateRate);

        //working status rate

        EmploymentStatus emp=scoringDataDTO.getEmployment().getEmploymentStatus();
        switch (emp)
        {
            case UNEMPLOYED:
                throw new ApiException("Refusal-> You don't qualify for loan due to been unemployed");
            case SELF_EMPLOYED:
                storeRate=storeRate.add(BigDecimal.valueOf(1));
                break;
            case BUSINESS_OWNER:
                storeRate=storeRate.add(BigDecimal.valueOf(3));
                break;
        }

        //Position status

        Position pos=scoringDataDTO.getEmployment().getPosition();
        switch (pos)
        {
            case TOP_MANAGER:
                storeRate=storeRate.add(BigDecimal.valueOf(2));
                break;
            case MIDDLE_MANAGER:
                storeRate=storeRate.subtract(BigDecimal.valueOf(4));
                break;
        }

        //check loan amount
        double salary=scoringDataDTO.getEmployment().getSalary().doubleValue()*20;
        if(scoringDataDTO.getAmount().doubleValue()>salary)
        {
            throw new ApiException("Refusal-> You don't qualify for loan, your salary is less");
        }

        //check marital status
        MaritalStatus status=scoringDataDTO.getMaritalStatus();
        switch (status)
        {
            case MARRIED:
                storeRate=storeRate.subtract(BigDecimal.valueOf(3));
                break;
            case DIVORCED:
                storeRate=storeRate.add(BigDecimal.valueOf(1));
                break;
        }

       if(scoringDataDTO.getDependentAmount()>1)
        {
            storeRate=storeRate.add(BigDecimal.valueOf(1));
        }

       if(ageRestriction(scoringDataDTO.getBirthdate()))
       {
        throw new ApiException("Refusal-> the age restriction is over 20 and less than 60");
       }

        Gender gender=scoringDataDTO.getGender();
       switch (gender)
       {
           case MALE:
               if(maleAge(scoringDataDTO.getBirthdate()))
               {
                  storeRate=storeRate.subtract(BigDecimal.valueOf(3));
               }
               break;
           case FEMALE:
               if(femaleAge(scoringDataDTO.getBirthdate()))
               {
                   storeRate=storeRate.subtract(BigDecimal.valueOf(3));
               }
               break;
       }

       if(scoringDataDTO.getEmployment().getWorkExperienceTotal()<12){
           throw new ApiException("Refusal-> Total Experience is less");
       }
       if(scoringDataDTO.getEmployment().getGetWorkExperienceCurrent()<3)
       {
           throw new ApiException("Refusal-> Current Experience is less");
       }
    return storeRate;
    }

    private static boolean femaleAge(LocalDate year) {
        return Period.between(year, LocalDate.now()).getYears()>=35 || Period.between(year, LocalDate.now()).getYears()<60;
    }

    private static boolean maleAge(LocalDate year) {

        return Period.between(year, LocalDate.now()).getYears()>=30 || Period.between(year, LocalDate.now()).getYears()<55;
    }

    private static boolean ageRestriction(LocalDate year)
    {
        return Period.between(year, LocalDate.now()).getYears()>60 || Period.between(year, LocalDate.now()).getYears()<20;
    }
}
