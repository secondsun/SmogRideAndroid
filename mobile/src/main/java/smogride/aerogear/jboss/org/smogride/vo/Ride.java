package smogride.aerogear.jboss.org.smogride.vo;

import org.jboss.aerogear.android.RecordId;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ride implements Serializable {

    @RecordId
    private long id;
    private String owner;

    private int metersTravelled;
    private int duration;

    private Date dateOfRide;

    private long version;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMetersTravelled() {
        return metersTravelled;
    }

    public void setMetersTravelled(int metersTravelled) {
        this.metersTravelled = metersTravelled;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDateOfRide() {
        return dateOfRide;
    }

    public void setDateOfRide(Date dateOfRide) {
        this.dateOfRide = dateOfRide;
    }


    @Override
    public String toString() {
        return metersTravelled + "m on " + SimpleDateFormat.getDateInstance(DateFormat.SHORT).format(dateOfRide);
    }
}

