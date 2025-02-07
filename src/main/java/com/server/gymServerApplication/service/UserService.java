package com.server.gymServerApplication.service;

import com.server.gymServerApplication.entity.mysql.AccountDetails;
import com.server.gymServerApplication.entity.mysql.User;
import com.server.gymServerApplication.repository.mysql.IUserrepository;
import com.server.gymServerApplication.utils.OtherFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    //    @Qualifier("primaryEntityManagerFactory")
    private final IUserrepository iUserrepository;

    @Autowired
    public UserService(IUserrepository iUserrepository) {
        this.iUserrepository = iUserrepository;
    }


    @Override
//    @Cacheable(value = "users", key = "#username")
//    @Transactional("primaryTransactionManager")
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
