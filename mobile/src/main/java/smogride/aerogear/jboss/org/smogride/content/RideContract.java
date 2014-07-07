package smogride.aerogear.jboss.org.smogride.content;

import android.net.Uri;

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


}
