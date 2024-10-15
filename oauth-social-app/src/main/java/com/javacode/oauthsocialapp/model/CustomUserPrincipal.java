package com.javacode.oauthsocialapp.model;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Setter
public class CustomUserPrincipal  implements UserDetails, OAuth2User {
    private long id;
    private String username;
    private String password;
    private String email;
    private String bio;
    private String role;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;


    public CustomUserPrincipal(long id, String username, String password, String email, String bio, String role,
       Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.role = role;
        this.authorities = authorities;
    }

    public static CustomUserPrincipal create(AppUser appUser) {
        Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(appUser.getRole()));
        return new CustomUserPrincipal(appUser.getId(), appUser.getUsername(), appUser.getPassword(), appUser.getEmail(),
            appUser.getBio(), appUser.getRole(), authorities);
    }

    public static CustomUserPrincipal create(AppUser appUser, Map<String, Object> attributes) {
        CustomUserPrincipal userPrincipal = create(appUser);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
