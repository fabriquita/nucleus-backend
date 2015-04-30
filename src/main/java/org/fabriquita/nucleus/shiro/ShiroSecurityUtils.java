package org.fabriquita.nucleus.shiro;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

@Service
public class ShiroSecurityUtils {

    private static Map<String, NucleusToken> tokens;

    private static Map<String, NucleusToken> getTokens() {
        if (tokens == null) {
            tokens = new HashMap<>();
        }
        return tokens;
    }

    public static void addToken(String user, NucleusToken token) {
        getTokens().put(user, token);
    }

    public static NucleusToken getToken(String user) {
        return getTokens().get(user);
    }

    public static NucleusToken generateToken(String user, String password, String host) {
        NucleusToken token = new NucleusToken(user, password, host);
        String credentials = user + System.currentTimeMillis();
        String hashedCredentials = new Sha256Hash(credentials).toString();
        token.setCredentials(Base64.encodeToString(hashedCredentials.getBytes()));
        return token;
    }

    public static void login(UsernamePasswordToken token) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
    }
}
