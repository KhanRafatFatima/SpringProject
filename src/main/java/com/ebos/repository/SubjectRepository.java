package com.ebos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebos.tables.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>{

}
