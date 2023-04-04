package com.knits.enterprise.mapper.common;

import com.knits.enterprise.model.common.BinaryData;
import com.knits.enterprise.dto.data.common.BinaryDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FileUploaderMapper extends EntityMapper<BinaryData, BinaryDataDto> {
}
