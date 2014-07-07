package smogride.aerogear.jboss.org.smogride.content;


import android.database.AbstractCursor;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import smogride.aerogear.jboss.org.smogride.util.GsonUtils;

public class SingleColumnJsonArrayList<T> extends AbstractCursor {

    private final List<T> cursorList;

    private static final Gson GSON = GsonUtils.GSON;

    public SingleColumnJsonArrayList(List<T> cursorList) {
        this.cursorList = cursorList;
    }

    public SingleColumnJsonArrayList(T value) {
        cursorList = new ArrayList<T>(1);
        cursorList.add(value);
    }

    @Override
    public int getCount() {
        return cursorList.size();
    }

    @Override
    public String[] getColumnNames() {
        return RideContract.COLUMNS;
    }

    @Override
    public String getString(int column) {
        return GSON.toJson(cursorList.get(super.getPosition()));
    }

    @Override
    public short getShort(int column) {
        return 0;
    }

    @Override
    public int getInt(int column) {
        return 0;
    }

    @Override
    public long getLong(int column) {
        return 0;
    }

    @Override
    public float getFloat(int column) {
        return 0;
    }

    @Override
    public double getDouble(int column) {
        return 0;
    }

    @Override
    public boolean isNull(int column) {
        return false;
    }

}
