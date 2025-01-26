package com.server.gymServerApplication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
public class AccountDetails implements UserDetails {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true; // Hoặc kiểm tra điều kiện nếu cần
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Kiểm tra nếu tài khoản bị khóa
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;// Kiểm tra nếu mật khẩu hết hạn nếu có
    }

    @Override
    public boolean isEnabled() {
        return !user.isDelete(); // Kiểm tra nếu tài khoản được kích hoạt
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
