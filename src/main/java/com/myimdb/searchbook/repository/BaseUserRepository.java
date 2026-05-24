package com.myimdb.searchbook.repository;

import com.myimdb.searchbook.entity.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {
}
