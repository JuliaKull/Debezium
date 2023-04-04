package com.knits.enterprise.dto.api;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class BulkUpdateResponseDto<T>{

    public static final int SUCCESS=1024;
    public static final int NOT_FOUND = -100;
    public static final int DUPLICATED = -200;
    public static final int AUTH_DENIED = -300;

    private T parent;
    private Map<Long, UpdateReportDto> reports;


}
