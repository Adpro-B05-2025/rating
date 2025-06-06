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
}