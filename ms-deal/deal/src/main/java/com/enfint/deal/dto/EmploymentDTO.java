package com.enfint.deal.dto;


import com.enfint.deal.dto.enumm.EmploymentStatus;
import com.enfint.deal.dto.enumm.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor
public class EmploymentDTO {
    private EmploymentStatus employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private Position position;
    private Integer workExperienceTotal;
    private Integer getWorkExperienceCurrent;
}
