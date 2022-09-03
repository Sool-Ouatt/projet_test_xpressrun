package com.xpress.auth.test.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xpress.auth.test.context.SpringApplicationContext;
import com.xpress.auth.test.exceptions.UserServiceException;
import com.xpress.auth.test.services.BannedIpService;
import com.xpress.auth.test.utils.ErrorMessages;

public class AddressFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("########## Initiating MyAdressFilter filter ##########");
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    	BannedIpService ipService = (BannedIpService) SpringApplicationContext.getBean("bannedIpServiceImpl");		
    	
    	HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        String adresIpClient = request.getRemoteAddr();
        
        if(ipService.isBanned(adresIpClient)) {
        	throw new UserServiceException(ErrorMessages.IP_ADD_BANNED.getErrorMessage());
        }
 
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
