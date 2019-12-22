package com.example.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryNumber extends AppCompatActivity {
    @BindView(R.id.list_view_country)
    ListView countryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_number);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> countryNameList = (ArrayList<String>) bundle.getSerializable("countryNameList");
        ArrayList<String> countryNumberList = (ArrayList<String>) bundle.getSerializable("countryNumberList");
        final List<Map<String,Object>> lists = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < countryNameList.size(); i++){
            Map<String,Object> item = new HashMap<String, Object>();
            item.put("countryName",countryNameList.get(i));
            item.put("countryNumber","+" + countryNumberList.get(i));
            lists.add(item);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,lists,R.layout.country_item,
                new String[]{"countryName","countryNumber"},new int[]{R.id.ct_view,R.id.num_view});
        countryListView.setAdapter(simpleAdapter);
        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> it= lists.get(position);
                Intent intent = new Intent(CountryNumber.this, UserRegister.class);
                intent.putExtra("Message",it.toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
