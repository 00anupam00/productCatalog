package com.adcash.productcatalog.configuration;

import com.adcash.productcatalog.dao.Customer;
import com.adcash.productcatalog.dto.CustomerRequestObj;
import com.adcash.productcatalog.service.CustomerService;
import com.adcash.productcatalog.util.Constants;
import com.adcash.productcatalog.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomerService customerService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.authenticationManager= authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            String email= jwtTokenUtil.getCustomerEmailFromKey(request.getHeader(Constants.HEADER_STRING).split(" ")[1]);
            Customer customer= customerService.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("The customer with id: "+email+", could not be found."));
            List<GrantedAuthority> authorities= jwtTokenUtil.getAuthoritiesList(customer.getAuthorities());
           return authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           customer.getCustomerId(),
                           customer.getPassword(),
                           authorities
                   )
           );
        } catch (UsernameNotFoundException e) {
            throw new AuthenticationCredentialsNotFoundException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        String token= jwtTokenUtil
                .generateToken(((CustomerRequestObj)authResult.getPrincipal()).getEmail());
        response.setHeader(Constants.HEADER_STRING,Constants.TOKEN_PREFIX + token);
    }
}
