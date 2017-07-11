package com.citymanage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class WmListActivity extends AppCompatActivity {
    EditText addressInput;
    TextView textView;

    Button wmMapActivityGo;
    Button searchBtn;

    ListView listView;
    WmAdapter adapter;

    String resultCode;
    String url;// = "http://192.168.0.229:3000/wmList";
    ProgressDialog dialog;

Context context;
    AddressSearchActivity ad = new AddressSearchActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wm_list);
//
//        textView = (TextView) findViewById(R.id.textView);
//        adapter = new WmAdapter(this);
//        listView = (ListView) findViewById(R.id.listView);
//
//        SharedPreferences city = getSharedPreferences("city", 0);
//        System.out.println(city.getString("city"+1 , ""));
//        System.out.println(city.getString("city"+2 , ""));
//
//        addressInput = (EditText) findViewById(R.id.addressInput);
//        searchBtn = (Button) findViewById(R.id.searchBtn);
//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
////                String serarchInfo = addressInput.getText().toString();
////                Log.i("search", serarchInfo);
////
////                String aaa = ("http://192.168.0.229:3000/wmList?" + "address=" + "경기도안양시" +
////                        "&addressInfo=" + serarchInfo);
////
////                String sbuURL1 = "경기도안양시";
////                String sbuURL2 = serarchInfo;
////                try {
////                    sbuURL1 = URLEncoder.encode(sbuURL1 , "UTF-8");
////                    sbuURL2 = URLEncoder.encode(serarchInfo , "UTF-8");
////                } catch (UnsupportedEncodingException e) {
////                    e.printStackTrace();
////                }
//////
////                String bbb = ("http://192.168.0.229:3000/wmList?" + "address=" + sbuURL1 +
////
////                        "&addressInfo=" + sbuURL2);
////
////                url = bbb;
////                Log.i("search", url);
//                //        dialog = new ProgressDialog(WmListActivity.this);
////        dialog.setMessage("Loading....");
////        dialog.show();
//                Log.i("search1", url);
//                Log.i("STRING", "TEST");
//
//                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String string) {
//
//                        Log.i("onResponse", "onResponse");
//                        parseJsonData(string);
//                        listView.setAdapter(adapter);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
//                        //dialog.dismiss();
//                    }
//                });
//                RequestQueue rQueue = Volley.newRequestQueue(WmListActivity.this);
//                rQueue.add(request);
//            }
//        });
//
//
//        wmMapActivityGo = (Button) findViewById(R.id.wmMapActivityGo);
//
//        wmMapActivityGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), WmMapActivity.class);
//                startActivity(intent);
//                //finish();
//            }
//        });
//    }
//
//
//    void parseJsonData(String jsonString) {
//        //wmArrayList = new ArrayList<HashMap<String, String>>();
//        try {
//            JSONObject object = new JSONObject(jsonString);
//
//            Log.i("JSONOBJECT", object.toString());
//            Log.d("RESULTCODE", "ListTest");
//
//            resultCode = object.getString("resultCode");
//
//            textView.setText(resultCode);
//            JSONArray wmArray = object.getJSONArray("list");
//
//            for (int i = 0; i < wmArray.length(); i++) {
//                String sensorId = wmArray.getJSONObject(i).getString("sensorId");
//                String address = wmArray.getJSONObject(i).getString("address");
//                String addressInfo = wmArray.getJSONObject(i).getString("addressInfo");
//                String waterQuality = wmArray.getJSONObject(i).getString("waterQuality");
//                String waterLevel = wmArray.getJSONObject(i).getString("waterLevel");
//
//
//                adapter.addItem(new WmItem(sensorId, address, addressInfo, waterQuality, waterLevel));
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
////        dialog.dismiss();
    }
}






