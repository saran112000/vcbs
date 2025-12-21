package com.bookings.vcbs.master.modal;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "login")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id")
    private Long loginId;
    
    @Column(name = "user_name")
    private String username;
    
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "role_id")
    private String roleId;

	public String getUserName() {
		return this.username;
	}
    
    
}