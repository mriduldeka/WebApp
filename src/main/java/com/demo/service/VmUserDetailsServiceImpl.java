package com.demo.service;

import com.demo.controller.VmUserController;
import com.demo.model.VmUser;
import com.demo.repository.VmUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class VmUserDetailsServiceImpl implements UserDetailsService {
    public static final Logger logger = LoggerFactory.getLogger(VmUserDetailsServiceImpl.class);
    private VmUserRepository vmUserRepository;

    public VmUserDetailsServiceImpl(VmUserRepository vmUserRepository) {
        this.vmUserRepository = vmUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername in VmUserDetailsServiceImpl");
        VmUser vmUser = vmUserRepository.findByUsername(username);
        if (vmUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(vmUser.getUsername(), vmUser.getPassword(), emptyList());
    }
}
