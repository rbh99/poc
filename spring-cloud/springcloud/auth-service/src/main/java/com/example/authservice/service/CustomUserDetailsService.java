package com.example.authservice.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    //private final UserRepository userRepository;

    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    	
    	return new UserDetails() {
			private static final long serialVersionUID = 1L;

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				// TODO Auto-generated method stub
				return Collections.singleton(new GrantedAuthority() {

					private static final long serialVersionUID = 1L;

					@Override
					public String getAuthority() {
						// TODO Auto-generated method stub
						return "AAA";
					}
					
					}
				);
			}

			@Override
			public String getPassword() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getUsername() {
				// TODO Auto-generated method stub
				return username;
			}

			@Override
			public boolean isAccountNonExpired() {
				// TODO Auto-generated method stub
				return username.equals("admin");
			}

			@Override
			public boolean isAccountNonLocked() {
				// TODO Auto-generated method stub
				return username.equals("admin");
			}

			@Override
			public boolean isCredentialsNonExpired() {
				// TODO Auto-generated method stub
				return username.equals("admin");
			}

			@Override
			public boolean isEnabled() {
				// TODO Auto-generated method stub
				return username.equals("admin");
			}
    		
    		
    	};
    	
    }

}
