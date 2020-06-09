package com.exercise.accountingNotebook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "User Class to handle the user data.")
public class User extends AbstractBaseModel {
    private static final long serialVersionUID = 2L;

    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String codeId;
    @Column
    private String email;

    public User(String name, String surname, String codeId, String email) {
        this.name = name;
        this.surname = surname;
        this.codeId = codeId;
        this.email = email;
    }
}
