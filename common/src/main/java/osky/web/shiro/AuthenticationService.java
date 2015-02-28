package osky.web.shiro;

import java.util.List;

public interface AuthenticationService {

    List<String> getAuthorities(Long id);

    AuthenticationPrincipal getPrincipal(String username, String password);
}
