package id.ac.ui.cs.advprog.rating.config;

import jakarta.servlet.Filter;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {
    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void testJwtAuthenticationFilterBean() {
        JwtAuthenticationFilter filter = securityConfig.jwtAuthenticationFilter();
        assertNotNull(filter);
    }

    @Test
    void testAuthenticationManager() throws Exception {
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(mock(AuthenticationManager.class));

        AuthenticationManager result = securityConfig.authenticationManager(authenticationConfiguration);

        assertNotNull(result);
        verify(authenticationConfiguration).getAuthenticationManager();
    }

    @Test
    void testFilterChainBeanCreation() throws Exception {
        HttpSecurity http = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        when(http.csrf(any())).thenReturn(http);
        when(http.sessionManagement(any())).thenReturn(http);
        when(http.authorizeHttpRequests(any())).thenReturn(http);
        when(http.addFilterBefore(any(Filter.class), eq(UsernamePasswordAuthenticationFilter.class))).thenReturn(http);

        SecurityFilterChain chain = securityConfig.filterChain(http);

        assertNotNull(chain);
        verify(http).csrf(any());
        verify(http).sessionManagement(any());
        verify(http).authorizeHttpRequests(any());
        verify(http).addFilterBefore(any(Filter.class), eq(UsernamePasswordAuthenticationFilter.class));
        verify(http).build();
    }
}