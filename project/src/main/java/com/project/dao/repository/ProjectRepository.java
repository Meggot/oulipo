// Copyright (c) 2019 Travelex Ltd

package com.project.dao.repository;

import com.project.dao.entites.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepository  extends PagingAndSortingRepository<Project, Integer>, QuerydslPredicateExecutor<Project> {

}
