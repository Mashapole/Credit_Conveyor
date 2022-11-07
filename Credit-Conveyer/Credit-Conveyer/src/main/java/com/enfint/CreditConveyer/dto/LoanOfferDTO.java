package com.enfint.CreditConveyer.dto;

import com.sun.javafx.beans.IDProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class LoanOfferDTO {

    //private long applicationId;
    private BigDecimal requestAmount;
    private BigDecimal totalAmount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private boolean isInsuranceEnabled;
    private boolean isSalaryClient;
}

