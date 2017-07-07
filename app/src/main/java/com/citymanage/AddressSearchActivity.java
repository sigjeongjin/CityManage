package com.citymanage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddressSearchActivity extends AppCompatActivity {

   // SharedPreferences city;

    WmVariableList wmVal = new WmVariableList();

    EditText addressText1;
    EditText addressText2;

    Button choiceButton;
    String citySearch;

    //String url = "http://192.168.0.229:3000/wmList?address=서울시 금천구&addressInfo=가산동";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search);

        addressText1 = (EditText) findViewById(R.id.addressText1);
        addressText2 = (EditText) findViewById(R.id.addressText2);

        System.out.println("ㅎㅎㅎ");
        System.out.println((1111|0001)|0000);
        savePreferences();
      //  getPreferences();

//        System.out.println(city.getString("city" , ""));
//        System.out.println(city.getString("city"+1 , ""));

        choiceButton = (Button) findViewById(R.id.choiceButton);
        choiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                citySearch = addressText1.getText().toString();
                Log.i("City", citySearch);



                Intent intent = new Intent(getApplicationContext(), WmListActivity.class);
                startActivity(intent);
            }
        });
    }

    //private void SavePreferencesCitycode
    public void getPreferences() {
        SharedPreferences city = getSharedPreferences("city", 0);
        city.getString("city"+ 1 , "");
        city.getString("city"+ 2 , "");
        System.out.println(city.getString("city"+1 , ""));
        System.out.println(city.getString("city"+2 , ""));
    }

    public void savePreferences() {
        SharedPreferences city = getSharedPreferences("city", 0);
        SharedPreferences.Editor editor = city.edit();
        editor.putString("city"+ 1 , "서울시|0001");
        editor.putString("city"+ 2 , "경기도|0002");
        editor.commit();
    }
}


