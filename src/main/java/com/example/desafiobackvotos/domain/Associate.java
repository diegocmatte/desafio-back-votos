package com.example.desafiobackvotos.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Associate {

    @Id
    private Integer cpf;
}
