package com.enfint.CreditConveyer.dto;

import com.enfint.CreditConveyer.dto.ModelEnum.Gender;
import com.enfint.CreditConveyer.dto.ModelEnum.MaritalStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ScoringDataDTO {
    private BigDecimal amount;
    private String firstName;
    private String lastName;
    private String middleName;
    private Gender gender;
    private LocalDate birthdate;
    private String passportSeries;
    private LocalDate passportIssueDate;
    private String passportIssueBranch;
    private MaritalStatus maritalStatus;
    private Integer dependentAmount;
    private EmploymentDTO employment;
    private String account;
    private boolean isInsuranceEnabled;
    private boolean isSalaryClient;
}
