package com.ssafy.square4us.api.mvc.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.square4us.api.mvc.model.entity.Study;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long>{

}
