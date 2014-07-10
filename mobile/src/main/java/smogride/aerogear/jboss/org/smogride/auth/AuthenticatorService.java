package smogride.aerogear.jboss.org.smogride.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthenticatorService extends Service {

    private AerogearOAuth2AndroidAuthenticator mAuthenticator;


    public AuthenticatorService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAuthenticator = new AerogearOAuth2AndroidAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
