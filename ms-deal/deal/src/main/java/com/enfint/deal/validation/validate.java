package com.enfint.deal.validation;

import com.enfint.deal.exception.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.regex.Pattern;

@Slf4j
public class validate {

    public static boolean checkAmount(BigDecimal amount)
    {
        if(String.valueOf(amount).equals("") || String.valueOf(amount)==null || String.valueOf(amount).isEmpty())
        {
            throw new ApiException("Amount is required");
        }
        else
        {
            if(amount.compareTo(BigDecimal.valueOf(10000))>0)
            {
                log.info("Amount is correct:{}",amount);
            }
            else
            {
                throw new ApiException("Amount it muss be greater tha 10 000");
            }
            return true;
        }
    }

    public static boolean checkTerm(Integer term) {
        if(term==null)
        {
            throw new ApiException("Term is empty");
        }
        else
        {
            if(term<6)
            {
                throw new ApiException("Term must be greater than 6");
            }
            else if(term>=6)
            {
                log.info("Term is correct:{}",term);
            }
            
            return true;
        }
    }

    public static boolean checkFirstName(String firstName) {

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
                    log.info("First Name is correct:{}",firstName);
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
            
            return true;
        }
    }

    public static boolean checkLastName(String lastName) {
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
                    log.info("last Name is correct:{}",lastName);
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
            return true;
        }
    }

    public static boolean checkEmail(String email) {
        if(!email.isEmpty() || !email.equals("") || email!=null)
        {
            //on the email I used to avoid illegal escape character
            if(Pattern.matches("[\\w\\.]{2,50}@[\\w\\.]{2,20}",email))
            {
                log.info("Email Is correct:{}",email);
            }
            else
            {
                throw new ApiException("Email is not in correct format");

            }
            return true;
        }
        else {
            throw new ApiException("Email is required");
        }
    }

    public static boolean checkBirthdate(LocalDate birthdate) {
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
                log.info("Date is correct:{}",birthdate);
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

            return true;
        }
    }

    public static boolean checkPassportSeries(String passportSeries) {
        if(String.valueOf(passportSeries).equals("") || String.valueOf(passportSeries).isEmpty() || String.valueOf(passportSeries)==null)
        {

            throw new ApiException("passport series number is required");
        }
        else {
            //or we can use [0-9]+
            //String.valueOf(passportSeries).length()==6
            if(Pattern.matches("[\\d]{4}",passportSeries))
            {
                log.info("passport series is Correct:{}",passportSeries);
            }
            else
            {
                throw new ApiException("passport series is incorrect");
            }
            return true;
        }
    }

    public static boolean checkPassportNumber(String passportNumber) {
        if(String.valueOf(passportNumber).equals("") || String.valueOf(passportNumber).isEmpty() || String.valueOf(passportNumber)==null)
        {

            throw new ApiException("Passport number is required");
        }
        else {
            //or we can use [0-9]+
            if(Pattern.matches("[\\d]{6}",passportNumber))
            {
                log.info("Passport number is Correct:{}",passportNumber);
            }
            else
            {
                throw new ApiException("Passport number is incorrect");
            }
            return true;
        }
    }
}
