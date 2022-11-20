package com.enfint.CreditConveyer.service.Calculation;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class OfferMonthlyInstallment {


    private BigDecimal amount;
    private Integer term;
    private BigDecimal rate;

    public OfferMonthlyInstallment(BigDecimal amount, Integer term, BigDecimal rate) {
        this.amount = amount;
        this.term = term;
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getTerm() {
        return term;
    }

    public BigDecimal getRate()
    {
        return rate;
    }
    protected DecimalFormat format;
    public BigDecimal monthlyInstallment()
    {
        double rate=getRate().doubleValue()/1200;

        format=new DecimalFormat("0.00");
        double total=getAmount().doubleValue()*rate/(1-1/Math.pow(1+rate,getTerm()*12));

        double convert=Double.valueOf(format.format(total));
        return BigDecimal.valueOf(convert);
    }
    public BigDecimal totalAmount()
    {
        format=new DecimalFormat("0.00");
        double totalAmount=(monthlyInstallment().doubleValue()*getTerm()*12);
     return BigDecimal.valueOf(Double.valueOf(format.format(totalAmount)));
    }

}
