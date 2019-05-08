package com.lik.upload.mutipartupload.config;

import com.lik.upload.mutipartupload.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: mutipartupload
 * @Package: com.lik.upload.mutipartupload.config
 * @ClassName: ResouceServerConfig
 * @Author: chinasoft.k.li
 * @Description: websecurity的配置
 * @Date: 2019/5/7 9:53
 * @Version: 1.0
 */
@Component
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailService userDetailService;

    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence);
            }
        });
        auth.inMemoryAuthentication();
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .and()
                .authorizeRequests()
                .antMatchers("/download/**","/lib/**").permitAll()
                .antMatchers(HttpMethod.GET,"/download.html").permitAll()
                .anyRequest().authenticated();
        http.csrf().disable();
    }
}
