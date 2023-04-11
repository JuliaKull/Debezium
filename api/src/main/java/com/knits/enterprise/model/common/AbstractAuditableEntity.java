package com.knits.enterprise.model.common;

import com.knits.enterprise.model.common.AbstractActiveEntity;
import com.knits.enterprise.model.security.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
public abstract class AbstractAuditableEntity extends AbstractActiveEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
}
