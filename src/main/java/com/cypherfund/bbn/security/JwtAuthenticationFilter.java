package com.cypherfund.bbn.security;

import com.cypherfund.bbn.dto.TUserDto;
import com.cypherfund.bbn.proxies.UserFeignClient;
import com.cypherfund.bbn.utils.Enumerations;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private TUserDto tUserDto;
    private final UserFeignClient userFeignClient;

    public JwtAuthenticationFilter(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && validateTokenExternally(jwt)) {
                UserDetails userDetails = createUserDetails(tUserDto);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }


    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        log.error("No token found in request");
        return null;
    }

    private boolean validateTokenExternally(String token) {
        try {
            tUserDto = userFeignClient.validateToken(token).getData();
            if (tUserDto == null) {
                return false;
            }
        } catch (Exception ex) {
            log.error("Could not validate token", ex);
            return false;
        }
        return true;
    }

    private UserDetails createUserDetails(TUserDto user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(SimpleGrantedAuthority::new
        ).collect(Collectors.toList());


        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return authorities;
            }

            @Override
            public String getPassword() {
                return user.getUserId();
            }

            @Override
            public String getUsername() {
                return user.getUserId();
            }

            @Override
            public boolean isAccountNonExpired() {
                return user.getStatus().equals(Enumerations.USER_STATUS.active);
            }

            @Override
            public boolean isAccountNonLocked() {
                return !user.getStatus().equals(Enumerations.USER_STATUS.blocked);
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return !user.getStatus().equals(Enumerations.USER_STATUS.suspended);
            }

            @Override
            public boolean isEnabled() {
                return !user.getStatus().equals(Enumerations.USER_STATUS.disabled);
            }
        };
    }
}
