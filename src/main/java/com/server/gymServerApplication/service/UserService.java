package com.server.gymServerApplication.service;

import com.server.gymServerApplication.entity.AccountDetails;
import com.server.gymServerApplication.entity.User;
import com.server.gymServerApplication.repository.IUserrepository;
import com.server.gymServerApplication.utils.OtherFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final IUserrepository iUserrepository;

    @Autowired
    public UserService(IUserrepository iUserrepository) {
        this.iUserrepository = iUserrepository;
    }


    @Override
    @Cacheable(value = "users", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        if (OtherFunctions.isValidEmail(username)) {
            user = iUserrepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("EMAIL CHUA DUOC DANG KI!"));

        } else if (OtherFunctions.isValidPhoneNumber(username)) {
            user = iUserrepository.findByPhone(username)
                    .orElseThrow(() -> new UsernameNotFoundException("SO DIEN THOAI CHUA DUOC DANG KY!"));
        } else {
            throw new UsernameNotFoundException("Tai khoan khong ton tai!");
        }
        return new AccountDetails(user);
    }
}
