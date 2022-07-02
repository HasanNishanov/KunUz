package com.company.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Authentication
        auth.inMemoryAuthentication()
                .withUser("Ali").password("{noop}test123").roles("ADMIN")
                .and()
                .withUser("Vali").password("{noop}test123").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Authorization
        http.authorizeRequests()


                .antMatchers("/article","/article/user/*").hasAnyRole("PROFILE","ADMIN")
                .antMatchers("/auth","/auth/*").hasAnyRole("PROFILE","ADMIN")
                .antMatchers("/article_like","/article_like/*").hasAnyRole("PROFILE","ADMIN")
                .antMatchers("/types","/types/user/*","/types/*").hasAnyRole("PROFILE","ADMIN")
                .antMatchers("/category","/category/user/*","/category/*").hasAnyRole("PROFILE","ADMIN")
                .antMatchers("/region","/region/user/*","/region/*").hasAnyRole("PROFILE","ADMIN")


                .antMatchers("/article","/article/adm/*").hasAnyRole("ADMIN")
                .antMatchers("/types","/types/adm/*").hasAnyRole("ADMIN")
                .antMatchers("/category","/category/adm/*").hasAnyRole("ADMIN")
                .antMatchers("/profile","/profile/adm/*").hasAnyRole("ADMIN")
                .antMatchers("/region","/region/adm/*").hasAnyRole("ADMIN")

                .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic();
    }

}

