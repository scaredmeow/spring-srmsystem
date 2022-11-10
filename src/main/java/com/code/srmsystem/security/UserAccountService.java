package com.code.srmsystem.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.code.srmsystem.dao.UserDao;
import com.code.srmsystem.model.User;

@Service
public class UserAccountService implements UserDetailsService {

    private UserDao userDao;

    public UserAccountService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userDao.loginByUserName(username);
        UserAccount userAccount = new UserAccount(user);
        return userAccount;
    }

}
