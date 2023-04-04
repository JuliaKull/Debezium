package com.knits.enterprise.controller.company;

import com.knits.enterprise.dto.api.PaginatedResponseDto;
import com.knits.enterprise.dto.data.company.ContractDto;
import com.knits.enterprise.dto.search.company.ContractSearchDto;
import com.knits.enterprise.service.company.ContractService;
import com.knits.enterprise.validators.ContractFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Slf4j
@Validated
public class    ContractController {

    @Autowired
    private ContractService contractService;

    private static final String ENDPOINT_NAME="/contracts";


    @PostMapping(value = ENDPOINT_NAME, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ContractDto> addContractToEmployee(
            @RequestPart(value = "model") ContractDto contractDto,
            @RequestPart(value = "contract") @Valid @ContractFile MultipartFile contractFile
    ) throws Exception {
        return ResponseEntity.ok(contractService.uploadContract(contractDto, contractFile));
    }

    @GetMapping(value = ENDPOINT_NAME, consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<PaginatedResponseDto<ContractDto>> searchContracts(ContractSearchDto searchDto){
        log.debug("Http Get request to Search contracts with pagination with data : {}",searchDto.toString());
        PaginatedResponseDto<ContractDto> paginatedResponse = contractService.searchContracts(searchDto);
        return ResponseEntity
                .ok()
                .body(paginatedResponse);
    }
}
