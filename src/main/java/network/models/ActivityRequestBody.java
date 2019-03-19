package network.models;

import java.util.List;

public class ActivityRequestBody {
    private List<ActivityRequestBodyItem> activities;

    public ActivityRequestBody(List<ActivityRequestBodyItem> activities) {
        this.activities = activities;
    }
}
