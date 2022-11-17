package com.enfint.deal.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Passport implements Serializable {

    private String series;
    private String number;
    private LocalDate issueDate;
    private String issueBranch;

    public Passport(String series, String number) {
        this.series = series;
        this.number = number;
    }

    public Passport(String series, String number, LocalDate issueDate, String issueBranch) {
        this.series = series;
        this.number = number;
        this.issueDate = issueDate;
        this.issueBranch = issueBranch;
    }
}
