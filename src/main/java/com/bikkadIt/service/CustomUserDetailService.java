package com.bikkadIt.service;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.entity.User;
import com.bikkadIt.exception.ResourseNotFoundException;
import com.bikkadIt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND));
        return user;
    }
}
