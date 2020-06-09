package com.exercise.accountingNotebook.model.transaction;

import com.exercise.accountingNotebook.model.AbstractBaseModel;
import com.exercise.accountingNotebook.model.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Transaction Class to handle the states of each transactions.")
public class Transaction extends AbstractBaseModel {
    private static final long serialVersionUID = 2L;

    @Column
    private BigDecimal amount;
    @Column
    @Enumerated(EnumType.ORDINAL)
    private Type type;
    @Column
    @Enumerated(EnumType.ORDINAL)
    private Status status;
    @Column
    private String description;
    @JsonIgnore
    @ManyToOne
    private Account account;

}
