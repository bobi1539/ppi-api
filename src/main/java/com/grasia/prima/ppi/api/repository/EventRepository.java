package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.MEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EventRepository extends BaseRepository<MEvent, Long> {

    Optional<MEvent> findBySlug(String slug);

    @Query(
            nativeQuery = true,
            value = "SELECT " +
                    "EXTRACT(MONTH FROM start_date) AS event_month, " +
                    "COUNT(*) AS total_event " +
                    "FROM m_event me " +
                    "WHERE EXTRACT(YEAR FROM start_date) = :year " +
                    "GROUP BY event_month " +
                    "ORDER BY event_month"
    )
    List<Map<String, Object>> countPerMonthByYear(@Param("year") int year);
}
