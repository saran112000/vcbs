package com.bookings.vcbs.config;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookings.vcbs.master.modal.Login;


public interface LoginRepository extends JpaRepository<Login, Long> {
 Login findByUsername(String Username);
}
