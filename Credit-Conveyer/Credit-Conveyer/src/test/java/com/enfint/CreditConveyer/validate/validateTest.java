package com.enfint.CreditConveyer.validate;

import com.enfint.CreditConveyer.dto.EmploymentDTO;
import com.enfint.CreditConveyer.dto.LoanApplicationRequestDTO;
import com.enfint.CreditConveyer.dto.LoanOfferDTO;
import com.enfint.CreditConveyer.dto.ScoringDataDTO;
import com.enfint.CreditConveyer.dto.modelenum.EmploymentStatus;
import com.enfint.CreditConveyer.dto.modelenum.Gender;
import com.enfint.CreditConveyer.dto.modelenum.MaritalStatus;
import com.enfint.CreditConveyer.dto.modelenum.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class validateTest {

    private LoanApplicationRequestDTO dto;
    private List<LoanOfferDTO> offers;
    private ScoringDataDTO scoringDataDTO;

    private validate v;

    @BeforeEach
    void setUp() {
        dto= new LoanApplicationRequestDTO();
        offers= new ArrayList<>();
        scoringDataDTO= new ScoringDataDTO();

        dto.setAmount(BigDecimal.valueOf(40000));
        dto.setTerm(13);
        dto.setFirstName("Mashapole");
        dto.setLastName("Raletsemo");
        dto.setMiddleName("Shapozen");
        dto.setEmail("ralezemoshake@gmail.com");
        dto.setBirthdate(LocalDate.parse("1998-09-19"));
        dto.setPassportSeries("2020");
        dto.setPassportNumber("191919");

        ///lets add scoring
        scoringDataDTO.setGender(Gender.MALE);
        scoringDataDTO.setMaritalStatus(MaritalStatus.DIVORCED);
        scoringDataDTO.setPassportIssueBranch("Johannesburg");
        scoringDataDTO.setPassportIssueDate(LocalDate.parse("2022-09-11"));
        scoringDataDTO.setDependentAmount(2);
        scoringDataDTO.setEmployment(new EmploymentDTO(EmploymentStatus.BUSINESS_OWNER,"employee",BigDecimal.valueOf(70000), Position.MIDDLE_MANAGER,12,9));
        scoringDataDTO.setAccount("Capitec");
        scoringDataDTO.isInsuranceEnabled();
        scoringDataDTO.isSalaryClient();
    }

    @AfterEach
    void tearDown()
    {

    }

    @Test
    void checkAmount()
    {
        v= new validate();
        boolean output=v.checkAmount(dto.getAmount());
        assertEquals(true, output);
    }

    @Test
    void checkTerm() {
        v= new validate();
        boolean output=v.checkTerm(dto.getTerm());
        assertEquals(true, output);
    }

    @Test
    void checkFirstName() {
        v= new validate();
        boolean output=v.checkFirstName(dto.getFirstName());
        assertEquals(true, output);
    }

    @Test
    void checkLastName() {
        v= new validate();
        boolean output=v.checkLastName(dto.getLastName());
        assertEquals(true, output);
    }

    @Test
    void checkEmail() {
        v= new validate();
        boolean output=v.checkEmail(dto.getEmail());
        assertEquals(true, output);
    }

    @Test
    void checkBirthdate() {
        v= new validate();
        boolean output=v.checkBirthdate(dto.getBirthdate());
        assertEquals(true, output);
    }

    @Test
    void checkPassportSeries() {
        v= new validate();
        boolean output=v.checkPassportSeries(dto.getPassportSeries());
        assertEquals(true, output);
    }

    @Test
    void checkPassportNumber() {
        v= new validate();
        boolean output=v.checkPassportNumber(dto.getPassportNumber());
        assertEquals(true, output);
    }

    @Test
    void checkGender() {
        v= new validate();
        boolean output=v.checkGender(scoringDataDTO.getGender());
        assertEquals(true, output);
    }

    @Test
    void checkPassportIssueDate() {
        v= new validate();
        boolean output=v.checkPassportIssueDate(scoringDataDTO.getPassportIssueDate());
        assertEquals(true, output);
    }

    @Test
    void checkPassportBranch() {
        v= new validate();
        boolean output=v.checkPassportBranch(scoringDataDTO.getPassportIssueBranch());
        assertEquals(true, output);
    }

    @Test
    void checkMaritalStatus() {
        v= new validate();
        boolean output=v.checkMaritalStatus(scoringDataDTO.getMaritalStatus());
        assertEquals(true, output);
    }

    @Test
    void checkDependentAmount() {
        v= new validate();
        boolean output=v.checkDependentAmount(scoringDataDTO.getDependentAmount());
        assertEquals(true, output);
    }

    @Test
    void checkEmploymentStatus() {
        v= new validate();
        boolean output=v.checkEmploymentStatus(scoringDataDTO.getEmployment().getEmploymentStatus());
        assertEquals(true, output);
    }

    @Test
    void checkValidAccount() {
        v= new validate();
        boolean output=v.checkValidAccount(scoringDataDTO.getAccount());
        assertEquals(true, output);
    }

    @Test
    void checkEmployment() {
        v= new validate();
        boolean output=v.checkEmployment(scoringDataDTO.getEmployment());
        assertEquals(true, output);
    }

    @Test
    void checkINN() {
        v= new validate();
        boolean output=v.checkINN(scoringDataDTO.getEmployment().getEmployerINN());
        assertEquals(true, output);
    }

    @Test
    void checkSalary() {
        v= new validate();
        boolean output=v.checkSalary(scoringDataDTO.getEmployment().getSalary());
        assertEquals(true, output);
    }

    @Test
    void checkPosition() {
        v= new validate();
        boolean output=v.checkPosition(scoringDataDTO.getEmployment().getPosition());
        assertEquals(true, output);
    }

    @Test
    void checkWorkExprience() {
        v= new validate();
        boolean output=v.checkWorkExprience(scoringDataDTO.getEmployment().getWorkExperienceTotal());
        assertEquals(true, output);
    }

    @Test
    void checkCurrentWorkExprience() {
        v= new validate();
        boolean output=v.checkCurrentWorkExprience(scoringDataDTO.getEmployment().getGetWorkExperienceCurrent());
        assertEquals(true, output);
    }
}