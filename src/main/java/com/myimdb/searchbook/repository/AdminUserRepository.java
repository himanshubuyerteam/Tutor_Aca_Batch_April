package com.myimdb.searchbook.repository;

import com.myimdb.searchbook.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
}
