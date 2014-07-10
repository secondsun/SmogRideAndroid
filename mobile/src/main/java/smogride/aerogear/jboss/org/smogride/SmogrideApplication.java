package smogride.aerogear.jboss.org.smogride;


import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.common.collect.Lists;

import org.jboss.aerogear.android.Callback;
import org.jboss.aerogear.android.authorization.AuthzModule;
import org.jboss.aerogear.android.impl.authz.AuthzConfig;
import org.jboss.aerogear.android.impl.authz.AuthzService;
import org.jboss.aerogear.android.impl.authz.AuthzTypes;
import org.jboss.aerogear.android.impl.authz.oauth2.OAuth2AuthzModule;
import org.jboss.aerogear.android.unifiedpush.PushConfig;
import org.jboss.aerogear.android.unifiedpush.PushRegistrar;
import org.jboss.aerogear.android.unifiedpush.Registrations;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import smogride.aerogear.jboss.org.smogride.auth.AerogearOAuth2AndroidAuthenticator;

public class SmogrideApplication extends Application {

    private static final String MY_ALIAS = null;
    private final String VARIANT_ID       = "";
    private final String SECRET           = "";
    private final String GCM_SENDER_ID    = "";
    private final String UNIFIED_PUSH_URL = "";

    public static AuthzService AUTHZ_SERVICE;

    private PushRegistrar registration;
    public static SmogrideApplication INSTANCE;
    public AuthzModule authzModule;

    @Override
    public void onCreate() {
        super.onCreate();

        AuthzConfig config = null;
        try {
            config = new AuthzConfig(new URL("http://10.0.2.2:8080/auth"), "keycloak");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("illegal url");
        }
        config.setClientId("CLI");
        config.setAccountId(AerogearOAuth2AndroidAuthenticator.ACCOUNT_TYPE);
        config.setRedirectURL("http://10.0.2.2:8080/auth");
        config.setAuthzEndpoint("/realms/smogride/tokens/login");
        config.setAccessTokenEndpoint("/realms/smogride/tokens/access/codes");
        config.setClientSecret("8d3f54af-d346-45b8-83f2-a1c336463527");
        config.setScopes(Lists.newArrayList("user"));
        config.setType(AuthzTypes.OAUTH2);

        authzModule = new OAuth2AuthzModule(config);

        bindService(new Intent(this, AuthzService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                 AUTHZ_SERVICE = ((AuthzService.AuthzBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                AUTHZ_SERVICE = null;
            }
        }, BIND_AUTO_CREATE);

        INSTANCE = this;

    }

    private void setupPush() {
        Registrations registrations = new Registrations();

        try {
            PushConfig config = new PushConfig(new URI(UNIFIED_PUSH_URL), GCM_SENDER_ID);
            config.setVariantID(VARIANT_ID);
            config.setSecret(SECRET);
            config.setAlias(MY_ALIAS);

            registration = registrations.push("unifiedpush", config);

            registration.register(getApplicationContext(), new Callback<Void>() {
                private static final long serialVersionUID = 1L;

                @Override
                public void onSuccess(Void ignore) {
                    Toast.makeText(getApplicationContext(), "Registration Succeeded!",
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Exception exception) {
                    Log.e("MainActivity", exception.getMessage(), exception);
                }
            });

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}