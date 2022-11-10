package com.enfint.CreditConveyer.service.Calculation;

import java.math.BigDecimal;

public class OfferRateCalculation {

    private boolean insurance;
    private boolean salaryClient;
    private BigDecimal defaultValue;

    public OfferRateCalculation(boolean insurance, boolean salaryClient, BigDecimal defaultValue) {
        this.insurance = insurance;
        this.salaryClient = salaryClient;
        this.defaultValue = defaultValue;
    }

    public BigDecimal getDefaultValue()
    {
        return defaultValue;
    }
    public boolean isInsurance() {
        return insurance;
    }


    public boolean isSalaryClient()
    {
        return salaryClient;
    }

    public BigDecimal calculateRate()
    {
        BigDecimal rate;
        if(isInsurance()!=true)
        {
            rate=getDefaultValue().subtract(BigDecimal.valueOf(4));
        }
        else
        {
            rate=getDefaultValue().add(BigDecimal.valueOf(2));

        }

        if(isSalaryClient()!=true)
        {
            rate=getDefaultValue().subtract(BigDecimal.valueOf(1));

        }
        else
        {
            rate=getDefaultValue().add(BigDecimal.valueOf(3));
        }
        return rate;
    }
}
