package com.bookings.vcbs.config;


import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookings.vcbs.master.modal.Login;
import com.bookings.vcbs.master.repository.LoginRepository;

@Service
public class LoginDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
    private LoginRepository loginRepository;
	
    @Autowired
    private HttpServletRequest request;

    @Override
    @Transactional(readOnly = false)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Login login = loginRepository.findByUsername(username);
        if(login != null) 
        {
	        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	        String IpAddress="Not Available";
	     		try
	     		{
		     		IpAddress = request.getRemoteAddr();
		     		if("0:0:0:0:0:0:0:1".equalsIgnoreCase(IpAddress))
		     		{
		     			InetAddress ip = InetAddress.getLocalHost();
		     			IpAddress= ip.getHostAddress();
		     		}
	     		}
	     		catch(Exception e)
	     		{
		     		IpAddress="Not Available";	
		     		e.printStackTrace();	
	     		}
	     		
	     		return new org.springframework.security.core.userdetails.User(login.getUsername(), login.getPassword(), grantedAuthorities);
        }
        else {
        	   throw new UsernameNotFoundException("username not found");
        }
    }
}
