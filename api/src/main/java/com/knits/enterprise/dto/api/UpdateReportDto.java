package com.knits.enterprise.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class UpdateReportDto {

    public static final int SUCCESS=1024;
    public static final int NOT_FOUND = -100;
    public static final int DUPLICATED = -200;
    public static final int AUTH_DENIED = -300;

    @EqualsAndHashCode.Include
    private Long id;
    private String message;
    private int code;
}
