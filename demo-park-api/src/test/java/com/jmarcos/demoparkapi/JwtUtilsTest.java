package com.jmarcos.demoparkapi;

import com.jmarcos.demoparkapi.jwt.JwtToken;
import com.jmarcos.demoparkapi.jwt.JwtUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilsTest {
    @Test
    public void testCreateToken() {
        String username = "testuser";
        String role = "ROLE_USER";
        JwtToken token = JwtUtils.createToken(username, role);

        assertNotNull(token);
        assertTrue(JwtUtils.isTokenValid(token.getToken()));
        assertEquals(username, JwtUtils.getUsernameFromToken(token.getToken()));
        assertEquals(role, JwtUtils.getClaimsFromToken(token.getToken()).get("role", String.class));
    }
}
