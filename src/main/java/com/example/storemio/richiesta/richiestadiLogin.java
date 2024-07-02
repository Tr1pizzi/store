package com.example.storemio.richiesta;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class richiestadiLogin {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private Boolean remember;

}