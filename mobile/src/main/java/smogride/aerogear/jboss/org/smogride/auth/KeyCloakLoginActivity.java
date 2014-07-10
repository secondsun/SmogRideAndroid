package smogride.aerogear.jboss.org.smogride.auth;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import org.jboss.aerogear.android.Callback;

import smogride.aerogear.jboss.org.smogride.R;
import smogride.aerogear.jboss.org.smogride.SmogrideApplication;

public class KeyCloakLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_cloak_login);
    }

    @Override
    protected void onResume() {
        super.onStart();
        SmogrideApplication app = (SmogrideApplication) getApplication();
        app.authzModule.requestAccess(this, new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
