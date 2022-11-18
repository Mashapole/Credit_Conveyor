package com.enfint.CreditConveyer.validate;


import com.enfint.CreditConveyer.dto.EmploymentDTO;
import com.enfint.CreditConveyer.dto.modelenum.EmploymentStatus;
import com.enfint.CreditConveyer.dto.modelenum.Gender;
import com.enfint.CreditConveyer.dto.modelenum.MaritalStatus;
import com.enfint.CreditConveyer.dto.modelenum.Position;
import com.enfint.CreditConveyer.exception.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.regex.Pattern;

@Slf4j
public class validate {

    public boolean checkAmount(BigDecimal amount)
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

    public  boolean checkTerm(Integer term) {
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

    public  boolean checkFirstName(String firstName) {

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

    public  boolean checkLastName(String lastName) {
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

    public  boolean checkEmail(String email) {
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

    public  boolean checkBirthdate(LocalDate birthdate) {
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

    public  boolean checkPassportSeries(String passportSeries) {
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

    public  boolean checkPassportNumber(String passportNumber) {
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
    public  boolean checkGender(Gender gender) {
        if(gender==null)
        {
            throw new ApiException("Enter your Gender");
        }
        else
        {
            log.info("Gender Is Correct:{}",gender);
            return true;
        }
    }
    public  boolean checkPassportIssueDate(LocalDate passportIssueDate) {
        if (String.valueOf(passportIssueDate) == null || String.valueOf(passportIssueDate).equals("") || String.valueOf(passportIssueDate).isEmpty()) {
            throw new ApiException("Enter Passport Issue Date");
        } else {
            Date date;
            SimpleDateFormat simpleDateFormat;

            try {
                simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
                date = simpleDateFormat.parse(String.valueOf(passportIssueDate));
                log.info("Date is correct{}",date);
            } catch (Exception ex) {
                log.info("Error: " + ex.toString());
                throw new ApiException("Passport Issue Date is in wrong format-required(yyyy-mm-dd)");
            }
            return true;
        }
    }

    public  boolean checkPassportBranch(String passportIssueBranch) {
        if(passportIssueBranch==null || passportIssueBranch.equals("") || passportIssueBranch.isEmpty())
        {
            throw new ApiException("Enter Passport Issue Branch");
        }
        else
        {
            log.info("Passport Issue Branch Is Correct:{}",passportIssueBranch);
            return  true;
        }

    }

    public  boolean checkMaritalStatus(MaritalStatus maritalStatus) {
        if(maritalStatus==null)
        {
            throw  new ApiException("Marital Status Is Required");
        }
        else
        {
            MaritalStatus m1=MaritalStatus.DIVORCED;
            MaritalStatus m2=MaritalStatus.MARRIED;
            if(String.valueOf(m1).equalsIgnoreCase(String.valueOf(maritalStatus)) || String.valueOf(m2).equalsIgnoreCase(String.valueOf(maritalStatus)))
            {
                log.info("Marital Status is correct:{}",maritalStatus);

            }
            else
            {
                throw new ApiException("Enter Marital Status between Married and Divorced");
            }
            return true;
        }
    }

    public  boolean checkDependentAmount(Integer dependentAmount) {
        if(dependentAmount==null)
        {
            throw new ApiException("Dependent Amount Is Required");
        }
        else
        {
            log.info("Dependent Amount Is Correct:{}",dependentAmount);
            return true;
        }
    }

    public  boolean checkEmploymentStatus(EmploymentStatus employment) {
        if(employment==null)
        {
            throw new ApiException("employment type is required");
        }
        else
        {
            EmploymentStatus emp1=EmploymentStatus.BUSINESS_OWNER;
            EmploymentStatus emp3=EmploymentStatus.SELF_EMPLOYED;
            EmploymentStatus emp4=EmploymentStatus.UNEMPLOYED;

            if(employment.equals(emp1)|| employment.equals(emp3) || employment.equals(emp4))
            {
                log.info("Employment status is correct:{}",employment);

            }
            else
            {
                throw new ApiException("enter employment status between business owner, employed, self employed, unemployed");
            }
            return true;
        }
    }

    public  boolean checkValidAccount(String account) {
        if(account==null || account.equals("") || account.isEmpty())
        {
            throw new ApiException("enter account");
        }
        else
        {
            log.info("Account is ccorrect:{}",account);
            return true;
        }
    }

    public  boolean checkEmployment(EmploymentDTO employment) {
        if(employment==null)
        {
            throw new ApiException("employment details are required");
        }
        else
        {
            log.info("employment is ccorrect:{}",employment);
            return true;
        }
    }

    public  boolean checkINN(String employerINN) {
        if(employerINN==null || employerINN.isEmpty() || employerINN=="")
        {
            throw new ApiException("employmentINN is required");
        }
        else {
            log.info("employemntINN is correct:{}",employerINN);
            return  true;
        }
    }

    public  boolean checkSalary(BigDecimal salary) {
        if(salary==null)
        {
            throw new ApiException("employmentINN is required");
        }
        else {
            log.info("Salary is correct:{}",salary);
            return true;
        }
    }

    public  boolean checkPosition(Position position) {
        if(position==null)
        {
            throw new ApiException("position is required");
        }
        else
        {
            Position emp1=Position.MIDDLE_MANAGER;
            Position emp2=Position.TOP_MANAGER;

            if(position.equals(emp1)|| position.equals(emp2))
            {
                log.info("position status is correct:{}",position);

            }
            else
            {
                throw new ApiException("enter position status between business MIDDLE_MANAGER AND TOP_MANAGER");
            }
            return true;
        }
    }

    public  boolean checkWorkExprience(Integer workExperienceTotal) {
        if(workExperienceTotal==null)
        {
            throw new ApiException("workExperienceTotal is required");
        }
        else
        {
            log.info("work Experience Total is correct{}}", workExperienceTotal);
            return true;
        }
    }

    public  boolean checkCurrentWorkExprience(Integer getWorkExperienceCurrent) {
        if(getWorkExperienceCurrent==null)
        {
            throw new ApiException("work Experience current is required");
        }
        else
        {
            log.info("work Experience current is correct: {}",getWorkExperienceCurrent);
            return true;
        }
    }
}

