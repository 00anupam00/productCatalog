package com.adcash.productcatalog.configuration;

import com.adcash.productcatalog.dao.Customer;
import com.adcash.productcatalog.service.CustomerService;
import com.adcash.productcatalog.util.Constants;
import com.adcash.productcatalog.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomerService customerService;

    @Autowired
    ApplicationContext applicationContext;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header= request.getHeader(Constants.HEADER_STRING);
        if (header == null || !header.startsWith(Constants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(request));
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req){
        String token = req.getHeader(Constants.HEADER_STRING).split(" ")[1];
            ServletContext context= req.getServletContext();
            WebApplicationContext webApplicationContext= WebApplicationContextUtils.getWebApplicationContext(context);
            this.jwtTokenUtil= webApplicationContext.getBean(JwtTokenUtil.class);
            this.customerService= webApplicationContext.getBean(CustomerService.class);
        if (token != null) {
            // parse the token.
            int user_id = jwtTokenUtil.getCustomerIdFromKey(token);
            Customer customer= customerService.findById(user_id)
                    .orElseThrow(() -> new UsernameNotFoundException("The customer with id: "+user_id+", could not be found."));
            if (user_id != 0) {
                return new UsernamePasswordAuthenticationToken(
                        customer.getCustomerId(),
                        customer.getPassword(),
                        new ArrayList<>()
                );
            }
            return null;
        }
        return null;
    }
}
