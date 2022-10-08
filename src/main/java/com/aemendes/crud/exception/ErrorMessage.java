package com.aemendes.crud.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {
    private int statusCode;

    private Date timestamp;

    private String message;

    private String description;
}
