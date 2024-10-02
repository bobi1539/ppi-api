package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.MUserRole;
import com.grasia.prima.ppi.api.entity.TUserRoleSubMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleSubMenuRepository extends JpaRepository<TUserRoleSubMenu, Long> {

    List<TUserRoleSubMenu> findByUserRoleOrderBySubMenuSequenceAsc(MUserRole userRole);

    void deleteByUserRole(MUserRole userRole);
}
