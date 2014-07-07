package smogride.aerogear.jboss.org.smogride.content;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import org.jboss.aerogear.android.impl.datamanager.SQLStore;

import java.util.ArrayList;
import java.util.Calendar;

import smogride.aerogear.jboss.org.smogride.util.GsonUtils;
import smogride.aerogear.jboss.org.smogride.vo.Ride;

public class RideProvider extends ContentProvider {
    public RideProvider() {
    }

    private SQLStore<Ride> rideStore;
    private ContentResolver resolver;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (selection.equals(RideContract.ID)) {
            rideStore.remove(selectionArgs[0]);
            resolver.notifyChange(uri, null, false);
            return 1;
        }

        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return uri.toString();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Ride ride = GsonUtils.GSON.fromJson(values.getAsString(RideContract.DATA), Ride.class);
        rideStore.save(ride);
        if (values.getAsBoolean(RideContract.NOTIFY) != null && values.getAsBoolean(RideContract.NOTIFY)) {
            resolver.notifyChange(uri, null, false);
        }
        return uri;
    }

    @Override
    public boolean onCreate() {
        resolver = getContext().getContentResolver();
        rideStore = new SQLStore<Ride>(Ride.class, getContext());
        rideStore.openSync();

        if (rideStore.readAll().size() == 0) {
            makeRides(10);
        }
        return true;
    }

    private void makeRides(int count) {
        for (;count > 0; count--) {
            Ride r = new Ride();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) - count);
            r.setDateOfRide(cal.getTime());
            r.setId(count * 5);
            r.setOwner("secondsun");
            r.setDuration(1000 * count);
            r.setMetersTravelled(1024 * count);
            r.setVersion(1);
            rideStore.save(r);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        if (selectionArgs != null && selectionArgs.length == 1) {
            return new SingleColumnJsonArrayList<Ride>(rideStore.read(selectionArgs[0]));
        } else {
            return new SingleColumnJsonArrayList<Ride>(new ArrayList<Ride>(rideStore.readAll()));
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        Ride ride = GsonUtils.GSON.fromJson(values.getAsString(RideContract.DATA), Ride.class);
        rideStore.save(ride);
        if (values.getAsBoolean(RideContract.NOTIFY) != null && values.getAsBoolean(RideContract.NOTIFY)) {
            resolver.notifyChange(uri, null, false);
        }
        return 1;
    }
}
