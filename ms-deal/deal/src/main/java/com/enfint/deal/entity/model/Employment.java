package com.enfint.deal.entity.model;

import com.enfint.deal.dto.enumm.EmploymentStatus;
import com.enfint.deal.dto.enumm.Position;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Employment implements Serializable
{
    private EmploymentStatus employmentStatus;
    private String employer;
    private BigDecimal salary;
    private Position position;
    private Integer workExprienceTotal;
    private Integer workExprienceCurrent;

}
