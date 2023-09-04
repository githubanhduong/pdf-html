//package ru.kaleev.SpringCourse.security;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import ru.kaleev.SpringCourse.entity.User;
//import ru.kaleev.SpringCourse.repository.UserRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Component
//public class CustomAuthentication implements AuthenticationProvider {
//	@Autowired
//	UserRepository userRepository;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//    	String username = authentication.getName();
//    	String password = authentication.getCredentials().toString();
//
//        User user = userRepository.findUserByUsernameAndPassword(username, password);
//        
//        if(user != null) {
//            return new UsernamePasswordAuthenticationToken(username, password, getGrantedAuthorities(user.getAuthor(), user.getRole()));
//        } else{
//            return new UsernamePasswordAuthenticationToken(username, password);
//        }
//    }
//    
//    private List<GrantedAuthority> getGrantedAuthorities(String author, String role) {
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
// 
//            grantedAuthorities.add(new SimpleGrantedAuthority(author));
//            grantedAuthorities.add(new SimpleGrantedAuthority(role));
//        
//        return grantedAuthorities;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}