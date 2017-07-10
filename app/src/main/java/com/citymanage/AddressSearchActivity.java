package com.citymanage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddressSearchActivity extends AppCompatActivity {

    String url = "http://192.168.0.229:3000/cityList";
    List<HashMap<String, String>> cityList = new ArrayList<HashMap<String, String>>();
    List<String> cityNameList = new ArrayList<String>();
    String [] cityNameTest;
    Spinner citySp;
    Spinner stateSp;

    Button choiceBtn;
    String citySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search);

        citySp = (Spinner) findViewById(R.id.citySp);
        final ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("4");
        list.add("5");
        String[] list2 = new String[2];
        list2[0] = "안녕";
        list2[1] = "하세요";

        Log.i("list2", list2.toString());
//        dialog.setMessage("Loading....");
//        dialog.show();
        Log.i("Url", url);

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String pString) {
                parseJsonData(pString);
                Log.i("onResponse", "onResponse");

                Log.i("cityNameTest4", cityNameTest[0]);


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,cityNameTest);

//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityNameList);
//                //simple_spinner_item
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySp.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                //dialog.dismiss();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(AddressSearchActivity.this);
        rQueue.add(request);

        citySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddressSearchActivity.this,"선택된 아이템 : "+citySp.getItemAtPosition(position),Toast.LENGTH_SHORT).show();

//                String text = (String) citySp.getSelectedItem().toString();
//                System.out.println(text);
//                Log.i("test", "선택");
//                String cityCode = cityList.get(position).toString();
//                cityList.get(position).get("cityCode").toString();
//                Log.i("cityCode", cityCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void parseJsonData(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            Log.i("JSONARRAY", jsonObject.toString());

            JSONArray jsonArray = jsonObject.getJSONArray("address");
            cityNameTest = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++){

                String city = jsonArray.getJSONObject(i).getString("city");
                String cityCode = jsonArray.getJSONObject(i).getString("cityCode");
                Log.i("city", city);
                Log.i("cityCode", cityCode);
                HashMap<String,String> cityInfo = new HashMap<>();
                cityInfo.put("city", city);
                cityInfo.put("cityCode", cityCode);

                cityNameTest[i] = city;
                cityNameList.add(i,city);
                cityList.add(i, cityInfo);
            }

            Log.i("cityNameTest", cityNameTest[0]);
            Log.i("cityNameTest", cityNameTest[1]);
            Log.i("cityNameTest", cityNameTest[2]);

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        dialog.dismiss();
    }
}


