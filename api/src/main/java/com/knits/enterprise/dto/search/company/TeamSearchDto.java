package com.knits.enterprise.dto.search.company;

import com.knits.enterprise.dto.search.common.GenericSearchDto;
import com.knits.enterprise.model.company.Team;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder=true)
@NoArgsConstructor
public class TeamSearchDto extends GenericSearchDto<Team> {
}
