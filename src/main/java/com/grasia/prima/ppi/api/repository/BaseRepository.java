package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID> {

    List<T> findAll(Specification<T> specification, Sort sort);

    Page<T> findAll(Specification<T> specification, Pageable pageable);

    Optional<T> findByIdAndIsDeleted(ID id, boolean isDeleted);
}
