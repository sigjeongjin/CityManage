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
    EditText editText;

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

//        adapter.addItem(new WmItem("박현진", "010-1000-1000", 23, R.drawable.muji01));
//        adapter.addItem(new WmItem("방규식", "010-2000-2000", 29, R.drawable.muji02));
//        adapter.addItem(new WmItem("박진주", "010-3000-3000", 29, R.drawable.muji03));
//        adapter.addItem(new WmItem("강현명", "010-4000-4000", 29, R.drawable.muji04));
//        adapter.addItem(new WmItem("김민정", "010-5000-5000", 26, R.drawable.muji05));

        listView.setAdapter(adapter);

        editText = (EditText) findViewById(R.id.editText);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                String mobile = "010-1000-1000";
                int age = 20;

                //adapter.addItem(new WmItem(name, mobile, age, R.drawable.muji01));
                adapter.notifyDataSetChanged();
            }
        });

        // liseView click --> WminfoActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                WmItem item = (WmItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), WmInfoActivity.class);

                intent.putExtra("name", adapter.items.get(position).name);
                intent.putExtra("mobile", adapter.items.get(position).mobile);
                intent.putExtra("age", adapter.items.get(position).age);
                intent.putExtra("resId", adapter.items.get(position).resId);


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
            view.setName(item.getName());
            view.setMobile(item.getMobile());
            view.setAge(item.getAge());
            view.setImage(item.getResId());

            return view;
        }
    }
}
