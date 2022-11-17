package com.enfint.deal.entity;


import com.enfint.deal.dto.ApplicationStatusHistoryDTO;
import com.enfint.deal.entity.model.ApplicationStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Application")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@TypeDef(name="json", typeClass = JsonBinaryType.class)
public class Application {

    @Id
    @Column(name="applocation_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="client_Id", referencedColumnName = "client_id")
    private Client clientId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="credit_id", referencedColumnName = "credit_id")
    private Credit credit;

    @Column(name="status")
    private ApplicationStatus status;

    @Column(name="creation_date")
    private LocalDate creationDate;

    @Column(name="applied_offer")
    private String appliedOffer;

    @Column(name="sign_date")
    private LocalDate signDate;

    @Column(name="ses_code")
    private Long sesCode;

    @Type(type ="json")
    @Column(name="status_history", columnDefinition = "json")
    private List<ApplicationStatusHistoryDTO> statusHistoryy;


}
