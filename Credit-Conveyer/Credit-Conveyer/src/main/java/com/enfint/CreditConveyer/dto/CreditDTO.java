package com.enfint.CreditConveyer.dto;
import com.enfint.CreditConveyer.model.PaymentScheduleElement;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreditDTO {
    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal psk;
    private boolean isInsuranceEnabled;
    private boolean isSalaryClient;
    private List<PaymentScheduleElement> paymentSchedule;
}
