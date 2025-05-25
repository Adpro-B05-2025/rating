package id.ac.ui.cs.advprog.rating.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;

    private final String plainSecret = "ini-secret-key-super-panjang-dan-aman";

    private String base64Secret;

    @BeforeEach
    void setUp() {
        base64Secret = Base64.getEncoder().encodeToString(plainSecret.getBytes());
        jwtUtils = new JwtUtils(base64Secret);
    }

    private String generateToken(String userId, long expirationMillis) {
        byte[] keyBytes = Base64.getDecoder().decode(base64Secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);

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
        String fakePlainSecret = "fake-secret-key-that-does-not-match";
        String fakeBase64Secret = Base64.getEncoder().encodeToString(fakePlainSecret.getBytes());
        byte[] fakeKeyBytes = Base64.getDecoder().decode(fakeBase64Secret);
        Key fakeKey = Keys.hmacShaKeyFor(fakeKeyBytes);

        String token = Jwts.builder()
                .setSubject("userX")
                .signWith(fakeKey, SignatureAlgorithm.HS256)
                .compact();

        assertFalse(jwtUtils.validateJwtToken(token));
    }

    @Test
    void testGetSigningKey_whenJwtSecretIsNull_throwsException() {
        JwtUtils jwtUtilsWithNullSecret = new JwtUtils(null);
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> jwtUtilsWithNullSecret.getUserIdFromJwtToken("dummy.token.here"));
        assertEquals("JWT secret key is not initialized", exception.getMessage());
    }

    @Test
    void testValidateJwtToken_InvalidTokenFormat() {
        String invalidToken = "thisIsNotAJwtToken";
        assertFalse(jwtUtils.validateJwtToken(invalidToken));
    }
}
