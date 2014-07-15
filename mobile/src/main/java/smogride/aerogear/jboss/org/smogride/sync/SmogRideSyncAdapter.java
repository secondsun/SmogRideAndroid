package smogride.aerogear.jboss.org.smogride.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import org.jboss.aerogear.android.Callback;
import org.jboss.aerogear.android.Pipeline;
import org.jboss.aerogear.android.impl.pipeline.PipeConfig;
import org.jboss.aerogear.android.pipeline.Pipe;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import smogride.aerogear.jboss.org.smogride.content.RideContract;
import smogride.aerogear.jboss.org.smogride.vo.Ride;

/**
 * Created by summers on 7/10/14.
 */
public class SmogRideSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final URL SMOGRIDE_URL;

    static {
        try {
            SMOGRIDE_URL = new URL("http://10.0.2.2/smogride/app/v1");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private final ContentResolver mContentResolver;
    private final PipeConfig config = new PipeConfig(SMOGRIDE_URL, Ride.class);
    private final Pipeline pipeline = new Pipeline(SMOGRIDE_URL);
    private final Pipe<Ride> ridePipe = pipeline.pipe(Ride.class, config);

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
    public void onPerformSync(Account account, Bundle extras, String authority, final ContentProviderClient provider, SyncResult syncResult) {
        ridePipe.read(new Callback<List<Ride>>() {
            @Override
            public void onSuccess(List<Ride> rides) {
                mContentResolver.delete(RideContract.URI, null, null);
                for (Ride ride : rides) {
                    ContentValues rideValue = RideContract.asValues(ride);
                    rideValue.put(RideContract.NOTIFY, true);
                    mContentResolver.insert(RideContract.URI, rideValue);
                }

            }

            @Override
            public void onFailure(Exception e) {
                Log.e("Sync", e.getLocalizedMessage(), e);
            }
        });
    }
}
