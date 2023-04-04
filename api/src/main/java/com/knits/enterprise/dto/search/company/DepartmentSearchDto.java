package com.knits.enterprise.dto.search.company;

import com.knits.enterprise.dto.search.common.GenericSearchDto;
import com.knits.enterprise.model.company.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
public class DepartmentSearchDto extends GenericSearchDto<Department> {

    private String name;
    private String startDateFrom;
    private String startDateTo;
    private Long createdBy;

    @Override
    public Specification<Department> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Predicate noFiltersApplied = criteriaBuilder.conjunction();
            List<Predicate> filters = new ArrayList<>();
            filters.add(noFiltersApplied);

            if (Strings.isNotBlank(name)) {
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                filters.add(namePredicate);
            }

            if (createdBy != null) {
                Predicate createdByPredicate = criteriaBuilder.equal(root.get("createdBy").get("id"), createdBy);
                filters.add(createdByPredicate);
            }

            if (Strings.isNotBlank(startDateFrom)) {
                ZonedDateTime from = ZonedDateTime.parse(startDateFrom, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Predicate startDateFromPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), from);
                filters.add(startDateFromPredicate);
            }

            if (Strings.isNotBlank(startDateTo)) {
                ZonedDateTime to = ZonedDateTime.parse(startDateTo, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Predicate startDateToPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("startDateTo"), to);
                filters.add(startDateToPredicate);
            }

            return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
        };
    }
}
