package com.cookandroid.medi1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.BatchUpdateException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {

    private int year;
    private int month;
    private int dayOfMonth;

    private TextView txtV1;
    private TextView txtV4;
    private String fileName;

    private EditText edt1;
    private TextView txtV2;
    private TextView btn1;
    private TextView btn3;
    private TextView btn4;

    private ImageView btn_back;
    private ImageView btn_left;
    private ImageView btn_right;

    private RelativeLayout relativeLayout;
    private MyDBHelper myDBHelper;
    private String date;
    private String dayOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_left = (ImageView) findViewById(R.id.btn_left);
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtn_left();
            }
        });

        btn_right = (ImageView) findViewById(R.id.btn_right);
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtn_right();
            }
        });

        final Intent intent = getIntent();
        year = intent.getIntExtra("year", 0);
        month = intent.getIntExtra("month", 0);
        dayOfMonth = intent.getIntExtra("dayOfMonth", 0);

        edt1 = (EditText) findViewById(R.id.edt1);
        edt1.setHorizontallyScrolling(false);
        txtV2 = (TextView) findViewById(R.id.txtV2);
        txtV2.setMovementMethod(new ScrollingMovementMethod());
        btn1 = (TextView) findViewById(R.id.btn1);
        btn3 = (TextView) findViewById(R.id.btn3);
        relativeLayout = (RelativeLayout) findViewById(R.id.memoLayout);

        txtV1 = (TextView) findViewById(R.id.txtV1);
        date = "" + "" + year + "." + month + "." + dayOfMonth;
        todayDate();

        txtV2 = (TextView) findViewById(R.id.txtV2);
        displayMemo();

        txtV4 = (TextView) findViewById(R.id.txtV4);
        txtV4.setMovementMethod(new ScrollingMovementMethod());
        operateDB();

        //수정 중/수정 완료 구분
        if (edt1.getVisibility() == View.VISIBLE)
            relativeLayout.setBackgroundResource(R.drawable.primary_border_fill6);
        else
            relativeLayout.setBackgroundResource(R.drawable.primary_border_fill3);

        //저장하기
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(fileName);
            }
        });

        //수정하기
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setVisibility(View.VISIBLE);
                //텍스트 맨 마지막에 커서 올리기
                edt1.setSelection(edt1.getText().length());
                txtV2.setVisibility(View.INVISIBLE);
                btn1.setVisibility(View.VISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                relativeLayout.setBackgroundResource(R.drawable.primary_border_fill6);
            }
        });

        //삭제하기
        btn4 = (TextView) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });


    }

    //저장된 메모 보여주기
    private void displayMemo() {
        fileName = year + "" + month + "" + dayOfMonth + ".txt";
        Intent intent2 = getIntent();

        FileInputStream fis = null;
        try {
            fis = openFileInput(fileName);
            //메모가 있으면 textView 로 보여주기
            if (fis != null) {
                edt1.setVisibility(View.INVISIBLE);
                txtV2.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.VISIBLE);
            }
            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            String str = new String(fileData);
            //한국어 설정 (fileData, "EUC-KR") 추가 설정
            edt1.setText(str);
            txtV2.setText(str);
        } catch (Exception e) {
        }
    }

    //저장
    private void save(String readDay) {
        FileOutputStream fos = null;
        try {
            //MODE_NO_LOCALIZED_COLLATORS
            String content = edt1.getText().toString();
            String testContent = content.trim();
            if (testContent.equals("")) {
                Toast toast = new Toast(ScheduleActivity.this);
                View toastView = (View) View.inflate(ScheduleActivity.this, R.layout.toast, null);
                TextView toastText = (TextView) toastView.findViewById(R.id.toast1);
                toastText.setText("내용을 입력해주세요.");
                toast.setView(toastView);
                toast.show();
//                Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_LONG).show();
                return;
            } else {
                fos = openFileOutput(readDay, Context.MODE_NO_LOCALIZED_COLLATORS);
                fos.write(content.getBytes());
                fos.close();

                Toast toast = new Toast(ScheduleActivity.this);
                View toastView = (View) View.inflate(ScheduleActivity.this, R.layout.toast, null);
                TextView toastText = (TextView) toastView.findViewById(R.id.toast1);
                toastText.setText("내용을 저장되었습니다.");
                toast.setView(toastView);
                toast.show();
//                Toast.makeText(getApplicationContext(), "저장되었습니다", Toast.LENGTH_LONG).show();
                //저장하면 곧바로 저장이 잘 되었는지 사용자에게 보여주기
                displayMemo();
                relativeLayout.setBackgroundResource(R.drawable.primary_border_fill3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //삭제
    public void delete() {
        if (txtV2.getVisibility() == View.VISIBLE) {
            AlertDialog.Builder alert = new AlertDialog.Builder(ScheduleActivity.this);
            alert.setMessage("삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fileName = year + "" + month + "" + dayOfMonth + ".txt";
                            deleteFile(fileName);

                            Toast toast = new Toast(ScheduleActivity.this);
                            View toastView = (View) View.inflate(ScheduleActivity.this, R.layout.toast, null);
                            TextView toastText = (TextView) toastView.findViewById(R.id.toast1);
                            toastText.setText("삭제되었습니다.");
                            toast.setView(toastView);
                            toast.show();
//                            Toast.makeText(getApplicationContext(), "삭제되었습니다", Toast.LENGTH_LONG).show();
                            ScheduleActivity.super.onBackPressed();
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            AlertDialog alertDialog = alert.create();
            alert.show();
        } else {


            Toast toast = new Toast(ScheduleActivity.this);
            View toastView = (View) View.inflate(ScheduleActivity.this, R.layout.toast, null);
            TextView toastText = (TextView) toastView.findViewById(R.id.toast1);
            toastText.setText("먼저 메모를 저장해주세요.");
            toast.setView(toastView);
            toast.show();
//            Toast.makeText(getApplicationContext(), "먼저 메모를 저장해주세요", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        final Intent intent4 = new Intent(getApplicationContext(), ScheduleActivity.class);
        final Intent i = new Intent(this, CalendarActivity.class);

        //입력 도중일 때 되돌아가기 누를 경우
        if (edt1.getVisibility() == View.VISIBLE) {
            String content = edt1.getText().toString();
            String testContent = content.trim();

            if (!testContent.equals("")) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ScheduleActivity.this);
                alert.setMessage("메모가 저장되지 않았습니다. 되돌아가시겠습니까?").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                intent4.putExtra("year", year);
                                intent4.putExtra("month", month);
                                intent4.putExtra("dayOfMonth", dayOfMonth);
                                ScheduleActivity.super.onBackPressed();
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog alertDialog = alert.create();
                alert.show();
            } else {
                super.onBackPressed();
            }
        } else {
            intent4.putExtra("year", year);
            intent4.putExtra("month", month);
            intent4.putExtra("dayOfMonth", dayOfMonth);
            super.onBackPressed();
        }

    }

    private boolean isLeapYear(int year) {
        return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
    }

    //왼쪽 버튼
    public void setBtn_left() {
        if (edt1.getVisibility() == View.VISIBLE) {
            String content = edt1.getText().toString();
            String testContent = content.trim();
            if (!testContent.equals("")) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ScheduleActivity.this);
                alert.setMessage("메모가 저장되지 않았습니다. 날짜를 이동하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                move_left();
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog alertDialog = alert.create();
                alert.show();
            } else
                move_left();
        } else
            move_left();
    }

    //오른쪽 버튼
    public void setBtn_right() {
        if (edt1.getVisibility() == View.VISIBLE) {
            String content = edt1.getText().toString();
            String testContent = content.trim();
            if (!testContent.equals("")) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ScheduleActivity.this);
                alert.setMessage("메모가 저장되지 않았습니다. 날짜를 이동하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                move_right();
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog alertDialog = alert.create();
                alert.show();
            } else
                move_right();
        } else
            move_right();
    }

    //왼쪽 이동에 맞게 주는 데이터값
    private void move_left() {
        Intent intent3 = new Intent(getApplicationContext(), ScheduleActivity.class);
        if (month == 1 && dayOfMonth == 1) {
            intent3.putExtra("year", year - 1);
            intent3.putExtra("month", 12);
            intent3.putExtra("dayOfMonth", 31);
        } else if (dayOfMonth == 1) {
            if (month == 5 || month == 7 || month == 10 || month == 12) {
                intent3.putExtra("year", year);
                intent3.putExtra("month", month - 1);
                intent3.putExtra("dayOfMonth", 30);
            } else if (month == 3 && isLeapYear(year) == true) {
                intent3.putExtra("year", year);
                intent3.putExtra("month", month - 1);
                intent3.putExtra("dayOfMonth", 29);
            } else if (month == 3 && isLeapYear(year) == false) {
                intent3.putExtra("year", year);
                intent3.putExtra("month", month - 1);
                intent3.putExtra("dayOfMonth", 28);
            } else {
                intent3.putExtra("year", year);
                intent3.putExtra("month", month - 1);
                intent3.putExtra("dayOfMonth", 31);
            }
        } else {
            intent3.putExtra("year", year);
            intent3.putExtra("month", month );
            intent3.putExtra("dayOfMonth", dayOfMonth - 1);
        }
        startActivity(intent3);
        finish();
    }

    //오른쪽 이동에 맞게 주는 데이터값
    private void move_right() {
        Intent intent3 = new Intent(getApplicationContext(), ScheduleActivity.class);
        if (month == 12 && dayOfMonth == 31) {
            intent3.putExtra("year", year + 1);
            intent3.putExtra("month", 1);
            intent3.putExtra("dayOfMonth", 1);
        } else if ((month == 4 || month == 6 || month == 9 || month == 11) && dayOfMonth == 30) {
            intent3.putExtra("year", year);
            intent3.putExtra("month", month + 1 );
            intent3.putExtra("dayOfMonth", 1);
        } else if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10) && dayOfMonth == 31) {
            intent3.putExtra("year", year);
            intent3.putExtra("month", month + 1 );
            intent3.putExtra("dayOfMonth", 1);
        } else if ((month == 2 && isLeapYear(year)) && dayOfMonth == 29) {
            intent3.putExtra("year", year);
            intent3.putExtra("month", month + 1 );
            intent3.putExtra("dayOfMonth", 1);
        } else if ((month == 2 && !isLeapYear(year)) && dayOfMonth == 28) {
            intent3.putExtra("year", year);
            intent3.putExtra("month", month + 1 );
            intent3.putExtra("dayOfMonth", 1);
        }else {
            intent3.putExtra("year", year);
            intent3.putExtra("month", month );
            intent3.putExtra("dayOfMonth", dayOfMonth + 1);
        }
        startActivity(intent3);
        finish();
    }

    //DB에서 약 이름 가져오기
    private void operateDB() {
        myDBHelper = new MyDBHelper(this);
        SQLiteDatabase db = myDBHelper.getReadableDatabase();
        Cursor cursor;

        String date2 = "" + year +"-" + month + "-" + dayOfMonth;

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd");
        String dbDate = "";
        Date d = null;
        try {
            d = dt.parse(date2);
        }catch (Exception e) {}
        dbDate = dt.format(d);

        cursor = db.rawQuery("SELECT * FROM medi WHERE startDate <='" + dbDate + "' AND endDate >= '" + dbDate +"';", null);

        String text = "";

        while (cursor.moveToNext()) {
            if (dayOfWeek.equals("일") && cursor.getInt(11) == 1) {
                text = text +  cursor.getString(1) + "\n";
            }  else if (dayOfWeek.equals("월") && cursor.getInt(5) == 1) {
                text = text +  cursor.getString(1) + "\n";
            }  else if (dayOfWeek.equals("화") && cursor.getInt(6) == 1) {
                text = text +  cursor.getString(1) + "\n";
            }  else if (dayOfWeek.equals("수") && cursor.getInt(7) == 1) {
                text = text +  cursor.getString(1) + "\n";
            } else if (dayOfWeek.equals("목") && cursor.getInt(8) == 1) {
                text = text +  cursor.getString(1) + "\n";
            } else if (dayOfWeek.equals("금") && cursor.getInt(9) == 1) {
                text = text +  cursor.getString(1) + "\n";
            } else if (dayOfWeek.equals("토") && cursor.getInt(10) == 1) {
                text = text +  cursor.getString(1) + "\n";
            }

        }
        cursor.close();
        db.close();

        if (!text.trim().equals("")) {
            txtV4.setText(text);
        }

    }

    //오늘의 날짜 yyyy-MM-dd 로 수정하기
    private void todayDate() {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy.MM.dd");
        Date d = null;
        try {
            d = dt.parse(date);
        }catch (Exception e) {

        }
        date= dt.format(d);

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK);

        String day = "";

        switch(dayNum){
            case 1:
                day = "일";
                break ;
            case 2:
                day = "월";
                break ;
            case 3:
                day = "화";
                break ;
            case 4:
                day = "수";
                break ;
            case 5:
                day = "목";
                break ;
            case 6:
                day = "금";
                break ;
            case 7:
                day = "토";
                break ;

        }
        txtV1.setText(date + " " + day);
        dayOfWeek = day;
    }


}
