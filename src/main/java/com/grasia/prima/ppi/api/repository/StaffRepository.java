package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.MPeriod;
import com.grasia.prima.ppi.api.entity.MStaff;

import java.util.List;

public interface StaffRepository extends BaseRepository<MStaff, Long> {

    List<MStaff> findByDivisionPeriodAndIsHeadOrderByDivisionIdAsc(MPeriod period, Boolean isHead);

    long countByDivisionPeriodId(Long periodId);
}
