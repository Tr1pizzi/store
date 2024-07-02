package com.example.storemio.Exception;

import lombok.Getter;

@Getter
public class messaggiodirisposta {
    private String message;

    public messaggiodirisposta(String message) {
        this.message = message;
    }
}
