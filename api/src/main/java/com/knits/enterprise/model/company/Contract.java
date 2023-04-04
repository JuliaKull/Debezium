package com.knits.enterprise.model.company;

import com.knits.enterprise.model.common.BinaryData;
import com.knits.enterprise.model.security.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "contract")
public class Contract implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "binary_id")
    private BinaryData binaryData;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn (name = "creator_id")
    private User createdBy;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "employee_id")
    private  Employee employee;

    @Column (name = "active")
    private Boolean active = true;

    @Column
    private LocalDateTime createdAt;

    @PrePersist
    public void onSave() {
        if (this.id == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
