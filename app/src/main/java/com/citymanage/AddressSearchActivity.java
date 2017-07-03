package com.citymanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddressSearchActivity extends AppCompatActivity {

    WmVariableList wmVal = new WmVariableList();

    EditText addressText1;
    EditText addressText2;

    Button choiceButton;
    String search;

    //String url = "http://192.168.0.229:3000/wmList?address=서울시 금천구&addressInfo=가산동";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search);

        addressText1 = (EditText) findViewById(R.id.addressText1);
        addressText2 = (EditText) findViewById(R.id.addressText2);

        choiceButton = (Button) findViewById(R.id.choiceButton);
        choiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WmListActivity.class);
                search = addressText1.getText().toString(); // + " " + addressText2.getText().toString());
                Log.i("search", search);
                startActivity(intent);
            }
        });
    }
}


