package models;

import util.NetworkAddressesProvider;

import java.util.Date;

/**
 * A POJO data structure representing a user activity.
 */
public class Activity {
    /**
     * Database identifier of an activity if the POJO was derived from the database.
     */
    private String id;
    private ActivityType type;
    private Date startDate;
    private Date endDate;
    private String ipAddress;
    private String macAddress;

    /**
     * Optional value of the activity. Depends on {@code type}.
     */
    private String value;

    /**
     * Name of an entity which initiated the activity. May be a file name or an application name.
     * Depends on @{code type}.
     */
    private String senderName;

    /**
     * A prefix to apply to the activity type when submitting the activity.
     * Identifies name of the platform (platforms include editors like eclipse or intellij)
     */
    private String platformPrefix;

    public Activity() {
        id = null;
        type = null;
        startDate = new Date();
        endDate = new Date();
        ipAddress = NetworkAddressesProvider.getIpAddress();
        macAddress = NetworkAddressesProvider.getMacAddress();
        value = null;
        senderName = null;
        platformPrefix = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getPlatformPrefix() {
        return platformPrefix;
    }

    public void setPlatformPrefix(String platformPrefix) {
        this.platformPrefix = platformPrefix;
    }
}
