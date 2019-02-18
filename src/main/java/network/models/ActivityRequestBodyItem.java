package network.models;

import models.Activity;

import java.util.Date;

/**
 * A data structure representing single activity request body.
 * It is used as an element in a list of activities  to submit.
 * Used in activity submission requests.
 */
public class ActivityRequestBodyItem {
    private String type;
    private Date startTime;
    private Date endTime;
    private String ipAddress;
    private String macAddress;
    private String value;
    private String executableName;

    /**
     * Forms a request body from {@link Activity} POJO model.
     * @param pojoActivity a POJO model to form body from.
     */
    public ActivityRequestBodyItem(Activity pojoActivity) {
        type = pojoActivity.getPlatformPrefix() + pojoActivity.getType().toString().toLowerCase();
        startTime = pojoActivity.getStartDate();
        endTime = pojoActivity.getEndDate();
        ipAddress = pojoActivity.getIpAddress();
        macAddress = pojoActivity.getMacAddress();
        value = pojoActivity.getValue();
        executableName = pojoActivity.getSenderName();
    }
}
