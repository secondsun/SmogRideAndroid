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
        Bundle toReturn = new Bundle();
        toReturn.putParcelable(AccountManager.KEY_INTENT, new Intent(context, KeyCloakLoginActivity.class).putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response));
        toReturn.putParcelable(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        return toReturn;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        final Bundle toReturn = new Bundle();
        SmogrideApplication app = SmogrideApplication.INSTANCE;
        AuthzService service = SmogrideApplication.AUTHZ_SERVICE;
        OAuth2AuthzSession session = service.getAccount(ACCOUNT_TYPE);

        if (session == null ) {
            toReturn.putString(AccountManager.KEY_ERROR_CODE, "0");
            toReturn.putString(AccountManager.KEY_ERROR_MESSAGE, "No Session");
        } else if (session.tokenIsNotExpired()) {
            toReturn.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            toReturn.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            toReturn.putString(AccountManager.KEY_AUTHTOKEN, session.getAccessToken());
        } else try {
            if (service.fetchAccessToken(ACCOUNT_TYPE, null) != null) {
                session = service.getAccount(ACCOUNT_TYPE);
                toReturn.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
                toReturn.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                toReturn.putString(AccountManager.KEY_AUTHTOKEN, session.getAccessToken());
            } else {
                toReturn.putString(AccountManager.KEY_ERROR_CODE, "0");
                toReturn.putString(AccountManager.KEY_ERROR_MESSAGE, "No Session");
            }
        } catch (OAuth2AuthorizationException e) {
            toReturn.putString(AccountManager.KEY_ERROR_CODE, "0");
            toReturn.putString(AccountManager.KEY_ERROR_MESSAGE, e.getError());
        }
        return toReturn;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return "SmogRide Login";
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
