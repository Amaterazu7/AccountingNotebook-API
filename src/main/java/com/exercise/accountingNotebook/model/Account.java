package com.exercise.accountingNotebook.model;

import com.exercise.accountingNotebook.model.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.persistence.*;
import java.math.BigDecimal;
import java.security.AccessControlContext;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Account Class to handle it's states.")
public class Account extends AbstractBaseModel {
    @Column
    private BigDecimal totalAmount;
    @OneToOne
    @MapsId
    private User user;
    @OneToMany(
            mappedBy = "account",
            cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Transaction> accountTransactions;
    @Version
    private Integer version;

}