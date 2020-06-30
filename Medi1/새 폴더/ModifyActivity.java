package com.cookandroid.medi1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ModifyActivity extends AppCompatActivity {
    ImageView btn_back;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button timeplus, btn_modify, start, end;
    EditText number, name;
    LinearLayout timelayout1, timelayout2, timelayout3, timelayout4, timelayout5;
    SQLiteDatabase sqlDB;
    MyDBHelper myHelper;
    Integer timesPerDay;
    Integer[] day_array;
    String[] time_array;
    String startday, endday;
    TextView[] tv = new TextView[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        myHelper = new MyDBHelper(this);

        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_modify = (Button) findViewById(R.id.btn_modify);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        start = (Button) findViewById(R.id.startBtn);
        end = (Button) findViewById(R.id.endBtn);

        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.number);

        final EditText startDate = (EditText) findViewById(R.id.startDate);
        final EditText endDate = (EditText) findViewById(R.id.endDate);

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);//몇년도인지
        mMonth = cal.get(Calendar.MONTH);//몇월인지
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);



        startday = null;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ModifyActivity.this, R.style.DialogTheme, mDateSetListener, mYear, mMonth, mDay).show();
            }
            public DatePickerDialog.OnDateSetListener mDateSetListener =
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            UpdateNow();
                        }
                    };

            void UpdateNow() {
                if(mMonth < 9) {
                    if(mDay < 10) {
                        startDate.setText(String.format(" %d 년 0%d 월 0%d 일", mYear, mMonth + 1, mDay));
                        startday = String.format("%d-0%d-0%d", mYear, mMonth + 1, mDay);
                    } else {
                        startDate.setText(String.format(" %d 년 0%d 월 %d 일", mYear, mMonth + 1, mDay));
                        startday = String.format("%d-0%d-%d", mYear, mMonth + 1, mDay);
                    }
                } else {
                    if(mDay < 10) {
                        startDate.setText(String.format(" %d 년 %d 월 0%d 일", mYear, mMonth + 1, mDay));
                        startday = String.format("%d-%d-0%d", mYear, mMonth + 1, mDay);
                    } else {
                        startDate.setText(String.format(" %d 년 %d 월 %d 일", mYear, mMonth + 1, mDay));
                        startday = String.format("%d-%d-%d", mYear, mMonth + 1, mDay);
                    }
                }
            }
        });

        endday = null;

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ModifyActivity.this, R.style.DialogTheme, mDateSetListener, mYear, mMonth, mDay).show();
            }
            public DatePickerDialog.OnDateSetListener mDateSetListener =
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            UpdateNow();
                        }
                    };

            void UpdateNow() {
                if(mMonth < 9) {
                    if(mDay < 10) {
                        endDate.setText(String.format(" %d 년 0%d 월 0%d 일", mYear, mMonth + 1, mDay));
                        endday = String.format("%d-0%d-0%d", mYear, mMonth + 1, mDay);
                    } else {
                        endDate.setText(String.format(" %d 년 0%d 월 %d 일", mYear, mMonth + 1, mDay));
                        endday = String.format("%d-0%d-%d", mYear, mMonth + 1, mDay);
                    }
                } else {
                    if(mDay < 10) {
                        endDate.setText(String.format(" %d 년 %d 월 0%d 일", mYear, mMonth + 1, mDay));
                        endday = String.format("%d-%d-0%d", mYear, mMonth + 1, mDay);
                    } else {
                        endDate.setText(String.format(" %d 년 %d 월 %d 일", mYear, mMonth + 1, mDay));
                        endday = String.format("%d-%d-%d", mYear, mMonth + 1, mDay);
                    }
                }
            }
        });


        final Button[] day = new Button[7];
        final Integer[] dayID = {R.id.mon, R.id.tue, R.id.wed, R.id.thu, R.id.fri, R.id.sat, R.id.sun};
        int i;
        day_array = new Integer[7];

        for(i = 0; i < dayID.length; i++) {
            day[i] = (Button) findViewById(dayID[i]);
        }
        for(i = 0; i < 7; i++) {
            day_array[i] = 0;
        }

        for(i = 0; i < dayID.length; i++) {
            final int index;
            index = i;
            day[index].setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("deprecation")
                public void onClick(View view) {
                    if (day[index].getCurrentTextColor() == Color.BLACK) {
                        day[index].setBackgroundResource(R.drawable.primary_border_fill_4);
                        day[index].setTextColor(Color.WHITE);
                        day_array[index] = 1;
                    }
                    else{
                        day[index].setBackgroundResource(R.drawable.primary_border_fill3);
                        day[index].setTextColor(Color.BLACK);
                        day_array[index] = 0;
                    }
                }
            });
        }

        timeplus = (Button) findViewById(R.id.timeplus);
        registerForContextMenu(timeplus);


        timelayout1 = (LinearLayout) findViewById(R.id.timelayout1);
        timelayout2 = (LinearLayout) findViewById(R.id.timelayout2);
        timelayout3 = (LinearLayout) findViewById(R.id.timelayout3);
        timelayout4 = (LinearLayout) findViewById(R.id.timelayout4);
        timelayout5 = (LinearLayout) findViewById(R.id.timelayout5);

        final Button[] timeSet = new Button[5];
        final Integer[] timeSetID = {R.id.modi1, R.id.modi2, R.id.modi3, R.id.modi4, R.id.modi5};
        int j;

        for(j = 0; j < timeSetID.length; j++) {
            timeSet[j] = (Button) findViewById(timeSetID[j]);
        }

        final View[] timePick = new View[5];
        final Integer[] timePickID = {R.layout.timepicker1, R.layout.timepicker2, R.layout.timepicker3, R.layout.timepicker4, R.layout.timepicker5};
        final TimePicker[] times = new TimePicker[5];
        final Integer[] timesID = {R.id.timepicker1, R.id.timepicker2, R.id.timepicker3, R.id.timepicker4, R.id.timepicker5};

        final Integer[] tvID = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5};
        int k;
        for (k = 0; k < timePickID.length; k++) {
            timePick[k] = (View) View.inflate(ModifyActivity.this, timePickID[k], null);
            times[k] = (TimePicker) timePick[k].findViewById(timesID[k]);
            tv[k] = (TextView) findViewById(tvID[k]);
        }

        time_array = new String[5];
        for(k = 0; k < 5; k ++) {
            time_array[k] = null;
        }

        for(k = 0; k < timePickID.length; k++) {
            final int index;
            index = k;
            timeSet[index].setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("deprecation")
                public void onClick(View view) {
                    AlertDialog.Builder dlg1 = new AlertDialog.Builder(ModifyActivity.this);
                    dlg1.setTitle("시간선택");
                    dlg1.setView(timePick[index]);
                    dlg1.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (times[index].getCurrentHour() < 12) {
                                if(times[index].getCurrentHour() <10){
                                    if(times[index].getCurrentMinute() < 10){
                                        tv[index].setText("AM " + 0 + Integer.toString(times[index].getCurrentHour()) + " : " +
                                                0 + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = 0 + Integer.toString(times[index].getCurrentHour()) + ":" +
                                                0 + Integer.toString(times[index].getCurrentMinute());
                                    } else {
                                        tv[index].setText("AM " + 0 + Integer.toString(times[index].getCurrentHour()) + " : "
                                                + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = 0 + Integer.toString(times[index].getCurrentHour()) + ":"
                                                + Integer.toString(times[index].getCurrentMinute());
                                    }
                                } else {
                                    if(times[index].getCurrentMinute() < 10){
                                        tv[index].setText("AM " + Integer.toString(times[index].getCurrentHour()) + " : "
                                                + 0 + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                                + 0 + Integer.toString(times[index].getCurrentMinute());
                                    } else {
                                        tv[index].setText("AM " + Integer.toString(times[index].getCurrentHour()) + " : "
                                                + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                                + Integer.toString(times[index].getCurrentMinute());
                                    }
                                }
                            } else {
                                if(times[index].getCurrentHour() - 12 < 10){
                                    if(times[index].getCurrentMinute() < 10) {
                                        tv[index].setText("PM " + 0 + Integer.toString(times[index].getCurrentHour() - 12) + " : "
                                                + 0 + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                                + 0 + Integer.toString(times[index].getCurrentMinute());
                                    } else {
                                        tv[index].setText("PM " + 0 + Integer.toString(times[index].getCurrentHour() - 12) + " : "
                                                + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                                + Integer.toString(times[index].getCurrentMinute());
                                    }
                                } else if(times[index].getCurrentMinute() < 10){
                                    tv[index].setText("PM " + Integer.toString(times[index].getCurrentHour() - 12) + " : "
                                            + 0 + Integer.toString(times[index].getCurrentMinute()));
                                    timeSet[index].setText("수정");
                                    time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                            + 0 + Integer.toString(times[index].getCurrentMinute());
                                } else {
                                    tv[index].setText("PM " + Integer.toString(times[index].getCurrentHour() - 12) + " : "
                                            + Integer.toString(times[index].getCurrentMinute()));
                                    timeSet[index].setText("수정");
                                    time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                            + Integer.toString(times[index].getCurrentMinute());
                                }
                            }
                        }
                    });
                    dlg1.setNegativeButton("취소", null);
                    dlg1.show();
                }
            });
        }


        //인텐트로 값 받아오기
        Intent intent = getIntent();
        String mName = intent.getStringExtra("mediName");
        //약 이름
        name.setText(mName);
        String mStart = intent.getStringExtra("startDate");
        startDate.setText(mStart.substring(0,4) + "년 " + mStart.substring(5, 7) +"월 " +
                mStart.substring(8, 10) + "일");
        String mEnd = intent.getStringExtra("endDate");
        endDate.setText(mEnd.substring(0, 4) + "년 " + mEnd.substring(5, 7) +"월 " +
                mEnd.substring(8, 10) + "일");

        int mTimes = intent.getIntExtra("timesPerDay", 0);
        //복용 횟수
        number.setText("1일 " + mTimes + "회 복용");

        String one = intent.getStringExtra("oneTime");
        String two = intent.getStringExtra("twoTime");
        String three = intent.getStringExtra("threeTime");
        String four = intent.getStringExtra("fourTime");
        String five = intent.getStringExtra("fiveTime");

        // 시간 배열로 저장
        String[] iTime = {one, two, three, four, five};
        // 시간 나오는 레이아웃 배열
        LinearLayout[] timelayout = {timelayout1, timelayout2, timelayout3, timelayout4, timelayout5};
        for(int o = 0; o < mTimes; o++){
            if(Integer.parseInt(iTime[o].substring(0, 2)) < 12){
                tv[o].setText("AM " + iTime[o]);
                timelayout[o].setVisibility(View.VISIBLE);
            } else {
                tv[o].setText("PM " + iTime[o]);
                timelayout[o].setVisibility(View.VISIBLE);
            }
        }

        int iMon = intent.getIntExtra("mon", 0);
        int iTue = intent.getIntExtra("tue", 0);
        int iWed = intent.getIntExtra("wed", 0);
        int iThu = intent.getIntExtra("thu", 0);
        int iFri = intent.getIntExtra("fri", 0);
        int iSat = intent.getIntExtra("sat", 0);
        int iSun = intent.getIntExtra("sun", 0);

        // 요일 버튼 값 가져와 색상변경
        Integer[] iday = {iMon, iTue, iWed, iThu, iFri, iSat, iSun};
        for(int m = 0; m < iday.length; m++){
            if(iday[m] == 1){
                day[m].setBackgroundResource(R.drawable.primary_border_fill_4);
                day[m].setTextColor(Color.WHITE);
            } else{
                day[m].setBackgroundResource(R.drawable.primary_border_fill3);
                day[m].setTextColor(Color.BLACK);
            }
        }

        int MediId = intent.getIntExtra("mediId", 0);

        //DB에 수정된 값 update
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("UPDATE mediTest SET (mediName, startDate, endDate, timesPerDay," +
                        "mon, tue, wed, thu, fri, sat, sun) VALUES ('" +
                        name.getText().toString() + "', '" + startday + "', '" +
                        endday + "', '" + timesPerDay + "', '" +
                        day_array[0] + "','" + day_array[1] + "','" + day_array[2] + "','" +
                        day_array[3] + "','" + day_array[4] + "','" + day_array[5] + "','" +
                        day_array[6] + "') WHERE mediId = MediId;");
                sqlDB.execSQL("UPDATE timeTest SET (oneTime, twoTime, threeTime, fourTime, fiveTime) VALUES ('" +
                        time_array[0] + "', '" +
                        time_array[1] + "', '" +
                        time_array[2] + "', '" +
                        time_array[3] + "', '" +
                        time_array[4] + "') WHERE timeId = MediId;");
                sqlDB.close();

                Log.e("db", "modify");
                Intent intent = new Intent(getApplicationContext(), SelectingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater mInflater = getMenuInflater();
        if (v == timeplus) {
            menu.setHeaderTitle("복용횟수설정");
            mInflater.inflate(R.menu.time_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        timesPerDay = null;
        switch (item.getItemId()) {
            case R.id.item1:
                number.setText("1일 1회 복용");
                timesPerDay = 1;
                timelayout1.setVisibility(View.VISIBLE);
                timelayout2.setVisibility(View.GONE);
                timelayout3.setVisibility(View.GONE);
                timelayout4.setVisibility(View.GONE);
                timelayout5.setVisibility(View.GONE);
                break;
            case R.id.item2:
                number.setText("1일 2회 복용");
                timesPerDay = 2;
                timelayout1.setVisibility(View.VISIBLE);
                timelayout2.setVisibility(View.VISIBLE);
                timelayout3.setVisibility(View.GONE);
                timelayout4.setVisibility(View.GONE);
                timelayout5.setVisibility(View.GONE);
                break;
            case R.id.item3:
                number.setText("1일 3회 복용");
                timesPerDay = 3;
                timelayout1.setVisibility(View.VISIBLE);
                timelayout2.setVisibility(View.VISIBLE);
                timelayout3.setVisibility(View.VISIBLE);
                timelayout4.setVisibility(View.GONE);
                timelayout5.setVisibility(View.GONE);
                break;
            case R.id.item4:
                number.setText("1일 4회 복용");
                timesPerDay = 4;
                timelayout1.setVisibility(View.VISIBLE);
                timelayout2.setVisibility(View.VISIBLE);
                timelayout3.setVisibility(View.VISIBLE);
                timelayout4.setVisibility(View.VISIBLE);
                timelayout5.setVisibility(View.GONE);
                break;
            case R.id.item5:
                number.setText("1일 5회 복용");
                timesPerDay = 5;
                timelayout1.setVisibility(View.VISIBLE);
                timelayout2.setVisibility(View.VISIBLE);
                timelayout3.setVisibility(View.VISIBLE);
                timelayout4.setVisibility(View.VISIBLE);
                timelayout5.setVisibility(View.VISIBLE);
                break;
        }
        return false;
    }
}
