package com.adcash.productcatalog.configuration;

import com.adcash.productcatalog.service.CustomerService;
import com.adcash.productcatalog.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomerService customerService;

    public WebSecurity(CustomerService customerService) throws Exception {
        this.customerService = customerService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, Constants.SAVE_CUSTOMERS).permitAll()
                .antMatchers(HttpMethod.POST, Constants.LOGIN_CUSTOMERS).permitAll()
                .antMatchers(HttpMethod.GET, Constants.GET_ALL_TAXES).permitAll()
                .antMatchers(HttpMethod.GET, Constants.GET_TAX).permitAll()
                .antMatchers(HttpMethod.GET, Constants.ALL_SHIPPING_REGIONS).permitAll()
                .antMatchers(HttpMethod.GET, Constants.SHIPPING_REGION).permitAll()
                .antMatchers(HttpMethod.GET, Constants.PRODUCTS_API).permitAll()
                .antMatchers(HttpMethod.GET, Constants.ALL_PRODUCTS_API).permitAll()
                .antMatchers(HttpMethod.GET, Constants.DEPARTMENTS_API).permitAll()
                .antMatchers(HttpMethod.GET, Constants.ALL_DEPARTMENTS_API).permitAll()
                .antMatchers(HttpMethod.GET, Constants.CATEGORY_API).permitAll()
                .antMatchers(HttpMethod.GET, Constants.ALL_CATEGORY_API).permitAll()
                .antMatchers(HttpMethod.GET, Constants.ATTRIBUTES_API).permitAll()
                .antMatchers(HttpMethod.GET, Constants.ALL_ATTRIBUTES_API).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .formLogin()
        .loginPage("/customers/login")
        .defaultSuccessUrl("/products")
        .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(new BCryptPasswordEncoder().encode("password"))
                .authorities("ROLE_USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
