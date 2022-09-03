package com.xpress.auth.test.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xpress.auth.test.security.AddressFilter;

@Configuration
public class MyAppConfig {
	@Bean
    FilterRegistrationBean<AddressFilter> filterRegistrationBean() {
        FilterRegistrationBean<AddressFilter> registrationBean = new FilterRegistrationBean();
        AddressFilter myAdressFilter = new AddressFilter();
        registrationBean.setFilter(myAdressFilter);
        registrationBean.addUrlPatterns("/users/*");
        //registrationBean.setOrder(2);  //set precedence
        return registrationBean;
    }
}
