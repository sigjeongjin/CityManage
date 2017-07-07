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
    List<String> cityNameList = new ArrayList<>();
    Spinner citySp;
    Spinner spinner2;

    Button choiceBtn;
    String citySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search);

        citySp = (Spinner) findViewById(R.id.citySp);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, cityNameList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySp.setAdapter(adapter);

//        dialog.setMessage("Loading....");
//        dialog.show();
        Log.i("search1", url);
        Log.i("STRING", "TEST");

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String string) {
                parseJsonData(string);
                Log.i("onResponse", "onResponse");

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

        // 아이템 선택 이벤트 처리
        citySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 아이템이 선택되었을 때 호출됨
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {


                String cityCode = cityList.get(position).get("cityCode");

                StringRequest request = new StringRequest(url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String string) {
                        parseJsonData(string);
                        Log.i("onResponse", "onResponse");

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                        //dialog.dismiss();
                    }
                });

            }

            // 아무것도 선택되지 않았을 때 호출됨
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void parseJsonData(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            Log.i("JSONARRAY", jsonObject.toString());

            JSONArray jsonArray = jsonObject.getJSONArray("address");

            for (int i = 0; i < jsonArray.length(); i++){
                String city = jsonArray.getJSONObject(i).getString("city");
                String cityCode = jsonArray.getJSONObject(i).getString("cityCode");
                Log.i("city", city);
                Log.i("cityCode", cityCode);
                HashMap<String,String> cityInfo = new HashMap<>();
                cityInfo.put("city", city);
                cityInfo.put("cityCode", cityCode);

                cityNameList.add(i,city);
                cityList.add(i, cityInfo);
            }

            Log.i("cityList", cityList.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
//        dialog.dismiss();
    }
}


