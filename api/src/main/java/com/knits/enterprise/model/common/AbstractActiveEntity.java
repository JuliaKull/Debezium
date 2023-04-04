package com.knits.enterprise.model.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder=true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class AbstractActiveEntity extends AbstractEntity{

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;
}
