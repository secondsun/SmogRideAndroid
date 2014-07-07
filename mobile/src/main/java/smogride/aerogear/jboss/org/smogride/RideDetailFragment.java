package smogride.aerogear.jboss.org.smogride;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import smogride.aerogear.jboss.org.smogride.content.RideContract;
import smogride.aerogear.jboss.org.smogride.util.GsonUtils;
import smogride.aerogear.jboss.org.smogride.vo.Ride;


/**
 * A fragment representing a single Ride detail screen.
 * This fragment is either contained in a {@link RideListActivity}
 * in two-pane mode (on tablets) or a {@link RideDetailActivity}
 * on handsets.
 */
public class RideDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Ride mRide;
    private View rootView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RideDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            getLoaderManager().initLoader(1, getArguments(), this);

            mRide = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ride_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mRide != null) {
            ((TextView) rootView.findViewById(R.id.ride_detail)).setText(mRide.getDateOfRide().toString());
        }

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(getActivity(), RideContract.idUri((int)args.getLong(ARG_ITEM_ID)), null, null, new String[]{args.get(ARG_ITEM_ID).toString()}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor.moveToNext()) {
            mRide = GsonUtils.GSON.fromJson(cursor.getString(0), Ride.class);
            if (rootView != null) {
                ((TextView) rootView.findViewById(R.id.ride_detail)).setText(mRide.getDateOfRide().toString());
            }

        }
        cursor.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
