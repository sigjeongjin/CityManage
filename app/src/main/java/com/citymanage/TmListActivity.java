package com.citymanage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

public class TmListActivity extends AppCompatActivity {
    EditText addressInput;

    Button wmMapActivityGo;
    ListView listView;
    TmListAdapter adapter;

    String resultCode;
    String url = "";
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tm_list);

//        wmMapActivityGo = (Button) findViewById(R.id.wmMapActivityGo);
//
//        wmMapActivityGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), TmMapActivity.class);
//                startActivity(intent);
//                //finish();
//            }
//        });
//
//        listView = (ListView) findViewById(R.id.listView);
//
//        adapter = new WmAdapter();
//
//        adapter.addItem(new WmItem("서울시 금천구", "정상", "정상"));
//        adapter.addItem(new WmItem("서울시 구로구", "비정상", "정상"));
//        adapter.addItem(new WmItem("서울시 관악구", "정상", "비정상"));
//
//
//        listView.setAdapter(adapter);
//
//        addressInput = (EditText) findViewById(R.id.addressInput);
//
//        Button button = (Button) findViewById(R.id.searchBtn);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = addressInput.getText().toString();
//                String mobile = "010-1000-1000";
//
//                //adapter.addItem(new WmItem(name, mobile, age, R.drawable.muji01));
//                adapter.notifyDataSetChanged();
//            }
//        });
//
//        // liseView click --> WminfoActivity
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                WmItem item = (WmItem) adapter.getItem(position);
//                Toast.makeText(getApplicationContext(), "선택 : " + item.getAddress(), Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getApplicationContext(), WmInfoActivity.class);
//
//                intent.putExtra("address", adapter.items.get(position).address);
//                intent.putExtra("waterQuality", adapter.items.get(position).waterQuality);
//                intent.putExtra("waterLevel", adapter.items.get(position).waterLevel);
//
//
//                startActivityForResult(intent, 1001);
//
//            }
//        });
    }

    void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(url);    // JSONObject에 객체를 저장

            resultCode = object.getString("resultCode");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }

}
