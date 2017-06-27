package com.citymanage;

/**
 * Created by we25 on 2017-06-26.
 */

public class PushHistoryItem {
    String pushTitle;
    String pushDescription;

    public PushHistoryItem(String deviceId, String pushDescription) {
        this.pushTitle = deviceId;
        this.pushDescription = pushDescription;
    }

    public String getPushTitle() {
        return pushTitle;
    }

    public void setPushTitle(String deviceId) {
        this.pushTitle = deviceId;
    }

    public String getPushDescription() {
        return pushDescription;
    }

    public void setPushDescription(String pushDescription) {
        this.pushDescription = pushDescription;
    }
}
