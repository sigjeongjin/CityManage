package com.citymanage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class WmListActivity extends AppCompatActivity {
    EditText addressInput;
    TextView textView;

    Button wmMapActivityGo;
    Button searchBtn;

    ListView listView;
    WmAdapter adapter;

    public static final String BASE_URL = "http://192.168.0.229:3000/";

    String resultCode;
    String url;// = "http://192.168.0.229:3000/wmList";
    ProgressDialog dialog;

    AddressSearchActivity ad = new AddressSearchActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wm_list);

        textView = (TextView) findViewById(R.id.textView);
        adapter = new WmAdapter(this);
        listView = (ListView) findViewById(R.id.listView);

        // Trailing slash is needed
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public interface WmAPiService {
        // Request method and URL specified in the annotation
        // Callback for the parsed response is the last parameter

        @GET("users/{username}")
        Call<WmItem> getUser(@Path("username") String username);

        @GET("group/{id}/users")
        Call<List<WmItem>> groupList(@Path("id") int groupId, @Query("sort") String sort);

        @POST("users/new")
        Call<WmItem> createUser(@Body WmItem wmItem);
    }
}






