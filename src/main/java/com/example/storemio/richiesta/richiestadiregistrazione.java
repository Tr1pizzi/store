package com.example.storemio.richiesta;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class richiestadiregistrazione {

    @NotBlank
    private String nome;

    @NotBlank
    private String cognome;

    @NotBlank
    private String dataNascita;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
