package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.MStudent;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface StudentRepository extends BaseRepository<MStudent, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT COUNT(education_id), mspl.name " +
                    "FROM m_student ms " +
                    "RIGHT JOIN m_system_parameter_list mspl ON mspl.id = ms.education_id " +
                    "WHERE mspl.system_parameter_id = 2 " +
                    "GROUP BY education_id, mspl.id, mspl.name " +
                    "ORDER BY mspl.id"
    )
    List<Map<String, Object>> countByEducation();
}
