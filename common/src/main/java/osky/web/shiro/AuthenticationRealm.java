package osky.web.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.List;


public class AuthenticationRealm extends AuthorizingRealm {

    @Resource(name = "authenticationService")
    private AuthenticationService authenticationService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token)
            throws AuthenticationException {
        AuthenticationToken authenticationToken = (AuthenticationToken) token;
        String username = authenticationToken.getUsername();
        char[] password = authenticationToken.getPassword();
        if (username != null && password != null) {
            AuthenticationPrincipal principal = authenticationService.getPrincipal(username, new String(password));
            if (principal != null) {
                return new SimpleAuthenticationInfo(principal, password, getName());
            }
        }
        throw new UnknownAccountException();
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        AuthenticationPrincipal principal = (AuthenticationPrincipal) principals.fromRealm(getName()).iterator().next();
        if (principal != null) {
            List<String> authorities = authenticationService.getAuthorities(principal.getId());
            if (authorities != null) {
                SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
                authorizationInfo.addStringPermissions(authorities);
                return authorizationInfo;
            }
        }
        return null;
    }
}