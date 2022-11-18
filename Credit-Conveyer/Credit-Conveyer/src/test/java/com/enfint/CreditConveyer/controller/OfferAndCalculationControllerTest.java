package com.enfint.CreditConveyer.controller;

import com.enfint.CreditConveyer.dto.EmploymentDTO;
import com.enfint.CreditConveyer.dto.LoanApplicationRequestDTO;
import com.enfint.CreditConveyer.dto.LoanOfferDTO;
import com.enfint.CreditConveyer.dto.modelenum.EmploymentStatus;
import com.enfint.CreditConveyer.dto.modelenum.Gender;
import com.enfint.CreditConveyer.dto.modelenum.MaritalStatus;
import com.enfint.CreditConveyer.dto.modelenum.Position;
import com.enfint.CreditConveyer.dto.ScoringDataDTO;
import com.enfint.CreditConveyer.service.OfferAndCalculationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = OfferAndCalculationController.class)
class OfferAndCalculationControllerTest {

    /*With the help of SpringJUnit4ClassRunner and MockMVc*/
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper= new ObjectMapper();

    @Autowired
    private JsonMapper jsonMapper= new JsonMapper();


    @MockBean
    private OfferAndCalculationService service;

    @MockBean
    private ScoringDataDTO scoringDataDTO;
    private List<LoanOfferDTO> loanOfferDTOS;
    private LoanApplicationRequestDTO dto;

    private String url="/loanOffers";
    @BeforeEach
    void setUp()
    {
        dto= new LoanApplicationRequestDTO();

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
    void loanOffer() throws Exception {

        jsonMapper=JsonMapper.builder().findAndAddModules().build();
        String template=jsonMapper.writeValueAsString(dto);

        MvcResult result=mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(template))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertNotNull(dto);
        assertEquals(200,result.getResponse().getContentAsString());
    }

    @Test
    void calculationService() throws Exception {
        jsonMapper=JsonMapper.builder().findAndAddModules().build();
        String template=jsonMapper.writeValueAsString(scoringDataDTO);

        MvcResult result=mockMvc.perform(post("/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(template))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertNotNull(scoringDataDTO);
        assertEquals(200,result.getResponse().getContentAsString());
    }
}