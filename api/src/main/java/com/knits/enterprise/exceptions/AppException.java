package com.knits.enterprise.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AppException extends RuntimeException{

    private int code;

    public AppException(String message,int code){
        super(message);
        setCode(code);
    }

    public AppException(String message) {
        super(message);
        setCode(ExceptionCodes.UNMAPPED_EXCEPTION_CODE);
    }

    public AppException(Exception e){
        super(e);
    }
}
