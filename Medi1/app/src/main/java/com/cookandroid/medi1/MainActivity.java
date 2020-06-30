package com.cookandroid.medi1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView name, time1, time2, time3, time4, time5;
    SQLiteDatabase sqlDB;
    MyDBHelper myHelper;
    RelativeLayout rl_select, rl_calendar;
    RecyclerView pill_list;
    PillListRecyclerViewAdapter PillListRecyclerViewAdapter;
    ArrayList<PillList> pillList = new ArrayList<>();
    LinearLayout lay1, lay2, lay3, lay4, lay5;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        name = (TextView) findViewById(R.id.txt_pill_item_list_mediName);
        time1 = (TextView) findViewById(R.id.txt_pill_item_list_time1);
        time2 = (TextView) findViewById(R.id.txt_pill_item_list_time2);
        time3 = (TextView) findViewById(R.id.txt_pill_item_list_time3);
        time4 = (TextView) findViewById(R.id.txt_pill_item_list_time4);
        time5 = (TextView) findViewById(R.id.txt_pill_item_list_time5);

        lay1 = (LinearLayout) findViewById(R.id.lay1);
        lay2 = (LinearLayout) findViewById(R.id.lay2);
        lay3 = (LinearLayout) findViewById(R.id.lay3);
        lay4 = (LinearLayout) findViewById(R.id.lay4);
        lay5 = (LinearLayout) findViewById(R.id.lay5);

        myHelper = new MyDBHelper(this);
        pillList.addAll(myHelper.allPListItems());

        rl_select = (RelativeLayout) findViewById(R.id.rl_select);
        rl_calendar = (RelativeLayout) findViewById(R.id.rl_calendar);

        rl_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectingActivity.class);
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



        pill_list = (RecyclerView) findViewById(R.id.pill_list);
        PillListRecyclerViewAdapter = new PillListRecyclerViewAdapter(pillList, this);
        pill_list.setAdapter(PillListRecyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pill_list.setLayoutManager(layoutManager);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
