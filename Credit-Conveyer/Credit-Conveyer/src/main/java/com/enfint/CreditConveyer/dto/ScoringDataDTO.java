package com.enfint.CreditConveyer.dto;

import com.enfint.CreditConveyer.dto.ModelEnum.Gender;
import com.enfint.CreditConveyer.dto.ModelEnum.MaritalStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
public class ScoringDataDTO {

    private BigDecimal amount;
    private Integer term;
    private String firstName;
    private String lastName;
    private String middleName;
    private Gender gender;
    private LocalDate birthdate;
    private String passportSeries;
    private String passportNumber;
    private LocalDate passportIssueDate;
    private String passportIssueBranch;
    private MaritalStatus maritalStatus;
    private Integer dependentAmount;
    private EmploymentDTO employment;
    private String account;
    private boolean isInsuranceEnabled;
    private boolean isSalaryClient;
}
