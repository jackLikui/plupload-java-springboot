package com.lik.upload.mutipartupload.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: mutipartupload
 * @Package: com.lik.upload.mutipartupload.service
 * @ClassName: UserDetailService
 * @Author: chinasoft.k.li
 * @Description: 认证服务中查询用户的服务
 * @Date: 2019/5/7 9:58
 * @Version: 1.0
 */
@Service
public class UserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String UserName) throws UsernameNotFoundException {
        return new User("lk","123456", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
    }
}
