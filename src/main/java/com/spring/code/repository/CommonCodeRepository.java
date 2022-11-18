package com.spring.code.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.code.model.CodeType;
import com.spring.code.model.CommonCodeEntity;


@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCodeEntity, Long> {
	Page<CommonCodeEntity> findByType(Pageable pageable, CodeType keyword);

	List<CommonCodeEntity> findByType(CodeType keyword);
}
