package com.enfint.CreditConveyer.service;

import com.enfint.CreditConveyer.dto.EmploymentDTO;
import com.enfint.CreditConveyer.dto.LoanApplicationRequestDTO;
import com.enfint.CreditConveyer.dto.LoanOfferDTO;
import com.enfint.CreditConveyer.dto.ModelEnum.EmploymentStatus;
import com.enfint.CreditConveyer.dto.ModelEnum.Gender;
import com.enfint.CreditConveyer.dto.ModelEnum.MaritalStatus;
import com.enfint.CreditConveyer.dto.ModelEnum.Position;
import com.enfint.CreditConveyer.dto.ScoringDataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OfferAndCalculationServiceTest {

    @Autowired
    private OfferAndCalculationService offerAndCalculationService;

    private LoanApplicationRequestDTO dto;
    private List<LoanOfferDTO> offers;
    private ScoringDataDTO scoringDataDTO;

    @BeforeEach
    void setUp() throws Exception
    {
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
        offers =offerAndCalculationService.offers(dto);

        ///lets add scoring
        scoringDataDTO.setAmount(BigDecimal.valueOf(60000));
        scoringDataDTO.setTerm(14);
        scoringDataDTO.setFirstName("Mashapole");
        scoringDataDTO.setLastName("Raletsemo");
        scoringDataDTO.setMiddleName("Shapozen");
        scoringDataDTO.setGender(Gender.MALE);
        scoringDataDTO.setBirthdate(LocalDate.parse("1998-09-19"));
        scoringDataDTO.setPassportSeries("2020");
        scoringDataDTO.setPassportNumber("191919");
        scoringDataDTO.setMaritalStatus(MaritalStatus.DIVORCED);
        scoringDataDTO.setDependentAmount(2);
        scoringDataDTO.setEmployment(new EmploymentDTO(EmploymentStatus.BUSINESS_OWNER,"employee",BigDecimal.valueOf(70000), Position.MIDDLE_MANAGER,12,9));
        scoringDataDTO.setAccount("Capitec");
        scoringDataDTO.isInsuranceEnabled();
        scoringDataDTO.isSalaryClient();

    }

    @Test
    void validateName()
    {
        assertTrue(dto.getFirstName().matches("[a-zA-Z]{2,30}"));
    }
    @Test
    void validatePassportNumber()
    {
        assertTrue(dto.getPassportNumber().matches("[\\d]{6}"));
    }

    @Test
    void validatePassportSeries() {
        assertTrue(dto.getPassportSeries().matches("[\\d]{4}"));
    }

    @Test
    void validateSurname() {
        assertTrue(dto.getLastName().matches("[a-zA-Z]{2,30}"));
    }

    @Test
    void offers()
    {

        assertNotNull(offers);
        assertEquals(4,offers.size());


        for(int i=0;i<offers.size();i++)
        {
            // from worst to the best
            //index 0
            //false-false
            //index 1
            //false-true
            //index 2
            //true-false
            //index 2
            //true-true
            assertEquals(dto.getAmount(),offers.get(0).getRequestAmount());
            assertEquals(dto.getAmount(),offers.get(1).getRequestAmount());
            assertEquals(dto.getAmount(),offers.get(2).getRequestAmount());
            assertEquals(dto.getAmount(),offers.get(3).getRequestAmount());

            assertEquals(dto.getTerm(),offers.get(0).getTerm());
            assertEquals(dto.getTerm(),offers.get(1).getTerm());
            assertEquals(dto.getTerm(),offers.get(2).getTerm());
            assertEquals(dto.getTerm(),offers.get(3).getTerm());

            assertEquals(-1,BigDecimal.valueOf(40000).compareTo(offers.get(0).getTotalAmount()));
            assertEquals(-1,BigDecimal.valueOf(50000).compareTo(offers.get(1).getTotalAmount()));
            assertEquals(-1,BigDecimal.valueOf(50000).compareTo(offers.get(2).getTotalAmount()));
            assertEquals(-1,BigDecimal.valueOf(50000).compareTo(offers.get(3).getTotalAmount()));


            assertEquals(1,BigDecimal.valueOf(600).compareTo(offers.get(0).getMonthlyPayment()));
            assertEquals(1,BigDecimal.valueOf(700).compareTo(offers.get(1).getMonthlyPayment()));
            assertEquals(1,BigDecimal.valueOf(700).compareTo(offers.get(2).getMonthlyPayment()));
            assertEquals(1,BigDecimal.valueOf(700).compareTo(offers.get(3).getMonthlyPayment()));

            assertFalse(offers.get(0).isInsuranceEnabled());
            assertFalse(offers.get(1).isInsuranceEnabled());
            assertTrue(offers.get(2).isInsuranceEnabled());
            assertTrue(offers.get(3).isInsuranceEnabled());

            assertFalse(offers.get(0).isSalaryClient());
            assertTrue(offers.get(1).isSalaryClient());
            assertFalse(offers.get(2).isSalaryClient());
            assertTrue(offers.get(3).isSalaryClient());
        }
    }

    @Test
    void calculationService()
    {
        assertNotNull(scoringDataDTO);
    }

}