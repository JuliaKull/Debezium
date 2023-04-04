package com.knits.enterprise.mapper.common;

import com.knits.enterprise.dto.data.common.ContactDto;
import com.knits.enterprise.model.common.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactPersonMapper extends EntityMapper<Contact, ContactDto> {


}
