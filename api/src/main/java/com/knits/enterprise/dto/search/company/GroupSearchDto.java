package com.knits.enterprise.dto.search.company;

import com.knits.enterprise.dto.search.common.GenericSearchDto;
import com.knits.enterprise.model.company.Group;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class GroupSearchDto extends GenericSearchDto<Group> {

    private String name;
    private String startDateFrom;
    private String startDateTo;
    private Long createdBy;

    @Override
    public Specification<Group> getSpecification() {

        return (root, query, criteriaBuilder) -> {

            query.distinct(true);
            Predicate noFiltersApplied = criteriaBuilder.conjunction();
            List<Predicate> filters = new ArrayList<>();
            filters.add(noFiltersApplied);

            if (Strings.isNotBlank(name)) {
                Predicate titleAsPredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                filters.add(titleAsPredicate);
            }

            if (createdBy != null) {
                Predicate createdGroupAsPredicate = criteriaBuilder.equal(root.get("createdBy").get("id"), createdBy);
                filters.add(createdGroupAsPredicate);
            }

            if (Strings.isNotBlank(startDateFrom)) {
                ZonedDateTime from = ZonedDateTime.parse(startDateFrom, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Predicate startDateFromAsPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), from);
                filters.add(startDateFromAsPredicate);
            }

            if (Strings.isNotBlank(startDateTo)) {
                ZonedDateTime to = ZonedDateTime.parse(startDateTo, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Predicate startDateToAsPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), to);
                filters.add(startDateToAsPredicate);
            }

            return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
        };
    }
}
