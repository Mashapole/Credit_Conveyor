package com.enfint.deal.entity;

import com.enfint.deal.model.PaymentScheduleElement;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name="Credit")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDef(name="json", typeClass = JsonBinaryType.class)
public class Credit {

    @Id
    @Column(name="credit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CreditId;

    @Column(name="amount" )
    private BigDecimal amount;

    @Column(name = "term")
    private Integer term;

    @Column(name="monthly_payment")
    private BigDecimal monthlyPayment;

    @Column(name="rate")
    private BigDecimal rate;

    @Column(name = "flc")
    private BigDecimal flc;

    @Type(type = "json")
    @Column(name = "payment_schedule", columnDefinition = "json")
    private List<PaymentScheduleElement> paymentScheduleElement;

    @Column(name="is_insurance_enabled")
    private Boolean isInsuranceENabled;

    @Column(name="is_salary_client")
    private Boolean isSalaryClient;



}
