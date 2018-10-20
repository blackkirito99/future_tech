package auth;
import java.util.HashSet;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;

import domain.User;
public class AppRealm extends JdbcRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // identify account to log to
        UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
        final String username = userPassToken.getUsername();
        final char[] password = userPassToken.getPassword();
        final User user = User.login(username, new String(password));
        if (user == null) {
            System.out.println("No account found for user with username " + username);
            return null;
        }

        return new SimpleAuthenticationInfo(user.getUserID(), user.getPassword(), user.getUsername());

    }


    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        Set < String > roles = new HashSet <> ();
        if (principals.isEmpty()) {
            System.out.println("Given principlas to authorize are empty.");
            return null;
        }
        int userID = (Integer) principals.getPrimaryPrincipal();
        final User user = User.getUser(userID);
        if (user == null) {
            System.out.println("No account found for user with username" + userID);
        }
        // add roles of user according to its type
        if (user.getType().equals(AppSession.CUSTOMER_ROLE)) {
            roles.add(AppSession.CUSTOMER_ROLE);
        } else if (user.getType().equals(AppSession.RETAILER_ROLE)) {
            roles.add(AppSession.RETAILER_ROLE);
        }

        return new SimpleAuthorizationInfo(roles);

    }
}