// Copyright (c) 2019 Travelex Ltd

package com.user.dao.repository;

import com.user.dao.entites.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Integer> {

}
