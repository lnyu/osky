package osky.web.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;


public class AuthenticationToken extends UsernamePasswordToken {


    public AuthenticationToken(String username, String password) {
        super(username, password);
    }
}
