package smogride.aerogear.jboss.org.smogride.content;

import android.content.ContentValues;
import android.net.Uri;

import smogride.aerogear.jboss.org.smogride.util.GsonUtils;
import smogride.aerogear.jboss.org.smogride.vo.Ride;

/**
 * Created by summers on 7/5/14.
 */
public class RideContract {
    public static final Uri URI = Uri.parse("content://smogride.rides");
    public static final String ID = "ID";
    public static final String DATA = "DATA";
    public static final String NOTIFY = "NOTIFY";

    public static final int RIDE = 1000;
    public static final int RIDE_ID  = 1001;
    public static final String[] COLUMNS = new String[] {ID, DATA};

    public static Uri idUri(Integer room_id) {
        return URI.buildUpon().appendEncodedPath(room_id.toString()).build();
    }


    public static ContentValues asValues(Ride ride) {
        ContentValues cv = new ContentValues(2);
        cv.put(DATA, GsonUtils.GSON.toJsonTree(ride).toString());
        if (ride.getId() > 0) {
            cv.put(ID, ride.getId());
        }
        return cv;
    }
}
