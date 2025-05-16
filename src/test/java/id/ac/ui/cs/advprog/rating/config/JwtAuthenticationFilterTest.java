package id.ac.ui.cs.advprog.rating.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;


import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private final JwtUtils jwtUtils = mock(JwtUtils.class);
    private final JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtils);

    @Test
    void testDoFilterInternal_WithValidToken() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String token = "valid.jwt.token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        when(jwtUtils.getUserIdFromJwtToken(token)).thenReturn("user123");

        filter.doFilterInternal(request, response, filterChain);

        // Check authentication set
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        assert authentication.getPrincipal().equals("user123");

        verify(filterChain).doFilter(request, response);

        // Clear context after test
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_NoToken() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        // No authentication should be set
        assert SecurityContextHolder.getContext().getAuthentication() == null;

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_InvalidToken() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String token = "invalid.jwt.token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.validateJwtToken(token)).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        // No authentication should be set
        assert SecurityContextHolder.getContext().getAuthentication() == null;

        verify(filterChain).doFilter(request, response);
    }
}
