package com.knits.enterprise.repository.common;

import com.knits.enterprise.model.common.Contact;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends ActiveEntityRepository<Contact> {
}
