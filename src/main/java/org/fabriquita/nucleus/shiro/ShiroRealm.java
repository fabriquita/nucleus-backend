package org.fabriquita.nucleus.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.fabriquita.nucleus.models.Role;
import org.fabriquita.nucleus.models.RoleResource;
import org.fabriquita.nucleus.models.User;
import org.fabriquita.nucleus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    public ShiroRealm() {
        setName("ShiroRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Long userId = (Long) principals.fromRealm(this.getName()).iterator().next();
        User user = userService.get(userId);
        if (user != null) {
            SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
            Role role = user.getRole();
            for (RoleResource roleResource : role.getRoleResources()) {
                for (String permission : roleResource.getRoleResourcePermissions()) {
                    authInfo.addStringPermission(permission);
                }
            }
            return authInfo;
        } else {
            return null;
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if (token == null) {
            return null;
        }
        User user = userService.getLogin(token.getUsername(), new String(token.getPassword()));
        if (user != null) {
            return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), this.getName());
        } else {
            throw new UnknownAccountException("User doesn't exist.");
        }
    }

}
