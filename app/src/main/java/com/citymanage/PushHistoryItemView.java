package com.citymanage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by we25 on 2017-06-26.
 */

public class PushHistoryItemView extends LinearLayout {
    TextView pushTitle;
    TextView pushDescription;

    public PushHistoryItemView(Context context) {
        super(context);
        init(context);
    }

    public PushHistoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.push_history_item, this, true);

        pushTitle = (TextView) findViewById(R.id.pushTitle);
        pushDescription = (TextView) findViewById(R.id.pushDescription);
    }

    public void setPushTitle(String name) {
        pushTitle.setText(name);
    }

    public void setpushDescription(String mobile) {
        pushDescription.setText(mobile);
    }
}
