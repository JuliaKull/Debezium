package com.knits.enterprise.dto.data.common;

import com.knits.enterprise.dto.data.company.AbstractOrganizationStructureDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
public class OrganizationDto extends AbstractOrganizationStructureDto {
     private String alias;
    private String vatNumber;
    private String registrationCode;
    private CountryDto taxRegistrationCountry;
    private AddressDto legalAddress;
    private ContactDto contactPerson;

}
