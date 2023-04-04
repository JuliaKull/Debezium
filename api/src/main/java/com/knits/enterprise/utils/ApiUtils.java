package com.knits.enterprise.utils;

import com.knits.enterprise.dto.api.UpdateReportDto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ApiUtils {

    public static Map<Long, UpdateReportDto>  calculateUpdateReports (Set<Long> updateIds, Set<Long> existingIds,  Set<Long> existingInParentIds){

        Set<Long> employeeIdsDuplicated = updateIds.stream()
                .filter(id -> existingInParentIds.contains(id))
                .collect(Collectors.toSet());

        Set<Long> employeeIdsNotFound = new HashSet<>();
        if (existingIds.size() < updateIds.size()) {
            employeeIdsNotFound = updateIds.stream()
                    .filter(id -> !existingIds.contains(id))
                    .collect(Collectors.toSet());
        }

        Set<Long> newInserts = new HashSet<>(updateIds);
        newInserts.removeAll(employeeIdsDuplicated);
        newInserts.removeAll(employeeIdsNotFound);
        return reportsToMap(employeeIdsNotFound, employeeIdsDuplicated, newInserts);
    }

    private static Map<Long, UpdateReportDto> reportsToMap(Set<Long> idsNotFound, Set<Long> IdsExistingInParent, Set<Long> newInserts) {
        Map<Long,UpdateReportDto> reports = new HashMap();
        idsNotFound.forEach(id -> reports.put(id,new UpdateReportDto(id, "Entity Not found", UpdateReportDto.NOT_FOUND)));
        IdsExistingInParent.forEach(id -> reports.put(id,new UpdateReportDto(id, "Entity Already added. Will be skipped", UpdateReportDto.DUPLICATED)));
        newInserts.forEach(id -> reports.put(id,new UpdateReportDto(id, "Entity Successfully added", UpdateReportDto.SUCCESS)));
        return reports;
    }
}
