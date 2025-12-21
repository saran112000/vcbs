package com.bookings.vcbs.config;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LogOutSuccessHandler implements LogoutSuccessHandler {
	
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException {
    	
    	try {
    		response.sendRedirect(request.getContextPath() + "/login?logout");

        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/login?logout"); // fallback
        }
    }
}
