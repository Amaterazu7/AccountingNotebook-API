package com.exercise.accountingNotebook.model.transaction;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "TransactionRequest Class to handle the request for each transaction.")
public class Request {
    private BigDecimal amount;
    private Type type;
}
