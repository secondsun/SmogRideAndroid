package smogride.aerogear.jboss.org.smogride.auth;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.jboss.aerogear.android.impl.authz.AuthzService;
import org.jboss.aerogear.android.impl.authz.OAuth2AuthorizationException;
import org.jboss.aerogear.android.impl.authz.oauth2.OAuth2AuthzSession;

import smogride.aerogear.jboss.org.smogride.SmogrideApplication;

/**
 * Created by summers on 7/9/14.
 */
public class AerogearOAuth2AndroidAuthenticator extends AbstractAccountAuthenticator {

    private final Context context;
    public static final String ACCOUNT_TYPE = "smogride.jboss.org";

    public AerogearOAuth2AndroidAuthenticator(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        throw new UnsupportedOperationException();

    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }
}
