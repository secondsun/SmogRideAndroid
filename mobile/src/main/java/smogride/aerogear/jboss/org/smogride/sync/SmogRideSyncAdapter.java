package smogride.aerogear.jboss.org.smogride.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Created by summers on 7/10/14.
 */
public class SmogRideSyncAdapter extends AbstractThreadedSyncAdapter {


    private final ContentResolver mContentResolver;

    /**
     * Set up the sync adapter
     */
    public SmogRideSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
* If your app uses a content resolver, get an instance of it
* from the incoming Context
*/
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SmogRideSyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
* If your app uses a content resolver, get an instance of it
* from the incoming Context
*/
        mContentResolver = context.getContentResolver();

    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

    }
}
