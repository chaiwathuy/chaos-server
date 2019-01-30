package net.cofcool.chaos.server.security.shiro.access;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;
import org.apache.shiro.realm.Realm;

/**
 * @author CofCool
 */
public class ExceptionAuthenticationStrategy extends AbstractAuthenticationStrategy {


    @Override
    public AuthenticationInfo afterAllAttempts(AuthenticationToken token,
        AuthenticationInfo aggregate) throws AuthenticationException {
        return super.afterAllAttempts(token, aggregate);
    }

    @Override
    public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token,
        AuthenticationInfo singleRealmInfo, AuthenticationInfo aggregateInfo, Throwable t)
        throws AuthenticationException {
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }
        return super.afterAttempt(realm, token, singleRealmInfo, aggregateInfo, t);
    }
}
