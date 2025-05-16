package id.ac.ui.cs.advprog.rating.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;
    private final String secret = "ini-secret-key-super-panjang-dan-aman";

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils(secret);
    }

    private String generateToken(String userId, long expirationMillis) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Test
    void testValidateJwtToken_Valid() {
        String token = generateToken("user123", 10000);
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    void testValidateJwtToken_Expired() throws InterruptedException {
        String token = generateToken("user123", 1); // expired almost immediately
        Thread.sleep(10);
        assertFalse(jwtUtils.validateJwtToken(token));
    }

    @Test
    void testGetUserIdFromJwtToken() {
        String token = generateToken("userABC", 10000);
        String userId = jwtUtils.getUserIdFromJwtToken(token);
        assertEquals("userABC", userId);
    }

    @Test
    void testValidateJwtToken_InvalidSignature() {
        // Token signed with different key
        String fakeSecret = "fake-secret-key-that-does-not-match";
        Key fakeKey = Keys.hmacShaKeyFor(fakeSecret.getBytes());
        String token = Jwts.builder()
                .setSubject("userX")
                .signWith(fakeKey, SignatureAlgorithm.HS256)
                .compact();

        assertFalse(jwtUtils.validateJwtToken(token));
    }
}
