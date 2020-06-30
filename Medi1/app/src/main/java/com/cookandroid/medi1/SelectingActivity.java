package com.cookandroid.medi1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class SelectingActivity extends AppCompatActivity {
    ImageView btn_plus_fill;
    RecyclerView rv_list;
    ListItemRecyclerViewAdapter listItemRecyclerViewAdapter;
    ArrayList<ListItem> selectingList = new ArrayList<>();
    MyDBHelper myHelper;
    RelativeLayout rl_check, rl_calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting);

        rl_check = (RelativeLayout) findViewById(R.id.rl_check);
        rl_calendar = (RelativeLayout) findViewById(R.id.rl_calendar);

        rl_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        rl_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        btn_plus_fill = (ImageView) findViewById(R.id.btn_plus_fill);

        btn_plus_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        myHelper = new MyDBHelper(this);

        selectingList.addAll(myHelper.allListItems());

        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        listItemRecyclerViewAdapter = new ListItemRecyclerViewAdapter(selectingList, this);
        rv_list.setAdapter(listItemRecyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(layoutManager);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}


