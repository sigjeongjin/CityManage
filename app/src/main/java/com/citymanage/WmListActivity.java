package com.citymanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WmListActivity extends AppCompatActivity {
    EditText addressInput;

    Button wmMapActivityGo;
    ListView listView;
    WmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wm_list);

        wmMapActivityGo = (Button) findViewById(R.id.wmMapActivityGo);

        wmMapActivityGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WmMapActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        listView = (ListView) findViewById(R.id.listView);

        adapter = new WmAdapter();

        adapter.addItem(new WmItem("서울시 금천구", "정상", "정상"));
        adapter.addItem(new WmItem("서울시 구로구", "비정상", "정상"));
        adapter.addItem(new WmItem("서울시 관악구", "정상", "비정상"));


        listView.setAdapter(adapter);

        addressInput = (EditText) findViewById(R.id.addressInput);

        Button button = (Button) findViewById(R.id.searchBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = addressInput.getText().toString();
                String mobile = "010-1000-1000";

                //adapter.addItem(new WmItem(name, mobile, age, R.drawable.muji01));
                adapter.notifyDataSetChanged();
            }
        });

        // liseView click --> WminfoActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                WmItem item = (WmItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getAddress(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), WmInfoActivity.class);

                intent.putExtra("address", adapter.items.get(position).address);
                intent.putExtra("waterQuality", adapter.items.get(position).waterQuality);
                intent.putExtra("waterLevel", adapter.items.get(position).waterLevel);


                startActivityForResult(intent, 1001);

            }
        });
    }
    class WmAdapter extends BaseAdapter {
        ArrayList<WmItem> items = new ArrayList<WmItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(WmItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            //WmItemView view = new WmItemView(getApplicationContext());

            WmItemView view = null;

            if (convertView == null) {
                view = new WmItemView(getApplicationContext());
            } else {
                view = (WmItemView) convertView;
            }

            WmItem item = items.get(position);
            view.setAddress(item.getAddress());
            view.setWaterQuality(item.getWaterQuality());
            view.setWaterLevel(item.getWaterLevel());


            return view;
        }
    }
}
