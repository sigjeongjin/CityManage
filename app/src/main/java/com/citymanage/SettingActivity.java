package com.citymanage;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity {

    Switch autoLoginOnOffSwitch;
    Button passwordChangeButton;
    ImageView profileChangeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        autoLoginOnOffSwitch = (Switch) findViewById(R.id.autoLoginOnOffSwitch);
        passwordChangeButton = (Button) findViewById(R.id.passwordChangeButton);
        profileChangeImageView = (ImageView) findViewById(R.id.profileChangeImageView);

        profileChangeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener = new DialogInterface.OnClickListener()
            }
        });
    }
}
