package com.enfint.CreditConveyer.dto;

import com.enfint.CreditConveyer.dto.ModelEnum.EmploymentStatus;
import com.enfint.CreditConveyer.dto.ModelEnum.Position;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmploymentDTO {
    private EmploymentStatus employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private Position position;
    private Integer workExperienceTotal;
    private Integer getWorkExperienceCurrent;
}
