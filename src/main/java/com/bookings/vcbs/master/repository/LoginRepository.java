package com.bookings.vcbs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookings.vcbs.master.modal.Login;
import com.bookings.vcbs.master.projection.LoginProjection;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
 
	Login findByUsername(String Username);
 
	 @Query(value = """
	 		
	 	SELECT l.login_id, l.user_name, l.role_id, r.role_name, l.emp_id, e.emp_no, e.emp_name, e.extension_no, e.email, e.desig_id, ed.designation, e.division_id, eg.division_code, eg.division_name, eg.division_head_id 
	    FROM login l 
	    INNER JOIN role_security r ON r.role_id = l.role_id 
	    INNER JOIN employee e ON e.emp_id = l.emp_id AND e.is_active = '1' 
	    LEFT JOIN employee_desig ed ON ed.desig_id = e.desig_id 
	    INNER JOIN employee_division eg ON eg.division_id = e.division_id AND eg.is_active = '1' 
	    WHERE l.is_active = '1';
	 		
	 		""", nativeQuery = true)
	 public List<LoginProjection> getUserManagerList();
	 
}
