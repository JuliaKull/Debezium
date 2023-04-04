package com.knits.enterprise.service.company;

import com.knits.enterprise.dto.api.PaginatedResponseDto;
import com.knits.enterprise.dto.data.company.ContractDto;
import com.knits.enterprise.dto.search.company.ContractSearchDto;
import com.knits.enterprise.mapper.common.ContractMapper;
import com.knits.enterprise.model.company.Contract;
import com.knits.enterprise.model.company.Employee;
import com.knits.enterprise.model.security.User;
import com.knits.enterprise.repository.common.ContractRepository;
import com.knits.enterprise.repository.company.EmployeeRepository;
import com.knits.enterprise.repository.security.UserRepository;
import com.knits.enterprise.service.common.FileUploaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@Primary
public class ContractService extends FileUploaderService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public ContractDto uploadContract(ContractDto contractDto, MultipartFile contractFile) throws IOException {
        Employee employee = employeeRepository.findById(contractDto.getEmployee().getId()).orElseThrow();
        User user = userRepository.findById(contractDto.getCreatedBy().getId()).orElseThrow();
        List<Contract> contracts = contractMapper.toEntities(getAllEmployeeContracts(employee.getId()));
        for (Contract previousContract : contracts) {
            if (previousContract.getActive()) {
                previousContract.setActive(false);
                contractRepository.save(previousContract);
            }
        }
        Contract contract = new Contract();
        contract.setEmployee(employee);
        contract.setCreatedBy(user);
        contract.setBinaryData(mapper.toEntity(upload(contractFile)));
        return contractMapper.toDto(contractRepository.save(contract));
    }

    private List<ContractDto> getAllEmployeeContracts(Long employeeId) {
        List<Contract> contracts = contractRepository.findByEmployee_Id(employeeId);
        return contractMapper.toDtos(contracts);
    }

    public PaginatedResponseDto<ContractDto> searchContracts(ContractSearchDto searchDto) {

        Page<Contract> contractPage = contractRepository.findAll(searchDto.getSpecification(), searchDto.getPageable());
        List<ContractDto> contractDtos = contractMapper.toDtos(contractPage.getContent());

        return PaginatedResponseDto.<ContractDto>builder()
                .page(searchDto.getPage())
                .size(contractDtos.size())
                .sortingFields(searchDto.getSort())
                .sortDirection(searchDto.getDir().name())
                .data(contractDtos)
                .build();
    }
}
