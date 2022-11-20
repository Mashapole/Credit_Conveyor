package com.enfint.deal.entity;

import com.enfint.deal.dto.enumm.Gender;
import com.enfint.deal.dto.enumm.MaritalStatus;
import com.enfint.deal.entity.model.Employment;
import com.enfint.deal.entity.model.Passport;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Client")
@Entity
@TypeDef(name="json", typeClass = JsonBinaryType.class)
public class Client {

    @Id
    @Column(name="client_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Column(name ="dependent_number")
    private Integer dependentNumber;

    @Type(type = "json")
    @Column(name="passport",columnDefinition = "json")
    private Passport passport;

    @Type(type="json")
    @Column(name="employement",columnDefinition = "json")
    private Employment employement;

    @Column(name="account")
    private String account;




}
