package com.ssafy.square4us.api.mvc.model.repository;

import com.ssafy.square4us.api.mvc.model.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
    public List<Study> findAll();
}
