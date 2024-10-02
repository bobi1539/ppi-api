package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.MUserRole;
import com.grasia.prima.ppi.api.entity.TUserRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleMenuRepository extends JpaRepository<TUserRoleMenu, Long> {

    List<TUserRoleMenu> findByUserRoleOrderByMenuSequenceAsc(MUserRole userRole);

    void deleteByUserRole(MUserRole userRole);
}
