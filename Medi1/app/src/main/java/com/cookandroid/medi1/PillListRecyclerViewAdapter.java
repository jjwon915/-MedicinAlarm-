package com.cookandroid.medi1;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class PillListRecyclerViewAdapter  extends RecyclerView.Adapter<PillListRecyclerViewAdapter.ItemViewHolder> {
    // adapter에 들어갈 list 입니다.
    private ArrayList<PillList> pList;
    private LayoutInflater pInflate;
    private Context context;
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;
    private String[] time_array;
    private int times;
    private String oneTime, twoTime, threeTime, fourTime, fiveTime;
    private ItemViewHolder holder;
    final boolean[] checkedState1, checkedState2, checkedState3, checkedState4, checkedState5;
    private SharedPreferences sharedPreferences;

    //**
    SQLiteDatabase sqlDB;
    MyDBHelper myHelper;
    int MediId;


    public PillListRecyclerViewAdapter(ArrayList<PillList> pList, Context context) {
        this.pList = pList;
        this.pInflate = LayoutInflater.from(context);
        this.context = context;
        checkedState1 = new boolean[pList.size()];
        checkedState2 = new boolean[pList.size()];
        checkedState3 = new boolean[pList.size()];
        checkedState4 = new boolean[pList.size()];
        checkedState5 = new boolean[pList.size()];
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pill_item_list, parent, false);
        return new ItemViewHolder(view);
    }

    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        //**
        myHelper = new MyDBHelper(context);
        sqlDB = myHelper.getWritableDatabase();

        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(pList.get(position), position);

        holder.btn_check1.setChecked(false);
        holder.btn_check2.setChecked(false);
        holder.btn_check3.setChecked(false);
        holder.btn_check4.setChecked(false);
        holder.btn_check5.setChecked(false);

        if (checkedState1[position])
        {
            holder.btn_check1.setChecked(true);
        }
        else {
            holder.btn_check1.setChecked(false);
        }

        if (checkedState2[position])
        {
            holder.btn_check2.setChecked(true);
        }
        else {
            holder.btn_check2.setChecked(false);
        }

        if (checkedState3[position])
        {
            holder.btn_check3.setChecked(true);
        }
        else {
            holder.btn_check3.setChecked(false);

        }

        if (checkedState4[position])
        {
            holder.btn_check4.setChecked(true);

        }
        else {
            holder.btn_check4.setChecked(false);

        }
        if (checkedState5[position])
        {
            holder.btn_check5.setChecked(true);
        }
        else {
            holder.btn_check5.setChecked(false);
        }

        holder.btn_check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedState1[position] = false;
                    //sqlDB.execSQL("UPDATE time SET oneCheck = '" + 1 + "' " +
                      //      "WHERE timeId = " + MediId + ";");
                    //Log.d("MediID", MediId + "");
                    //sqlDB.close();
                } else {
                    checkedState1[position] = true;
                    //sqlDB.execSQL("UPDATE time SET oneCheck = '" + 1 + "' " +
                      //      "WHERE timeId = " + MediId + ";");
                    //sqlDB.close();
                }
            }
        });

        holder.btn_check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedState2[position] = false;
                } else {
                    checkedState2[position] = true;
                }

            }
        });

        holder.btn_check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedState3[position] = false;
                } else {
                    checkedState3[position] = true;
                }
            }
        });

        holder.btn_check4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedState4[position] = false;
                } else {
                    checkedState4[position] = true;
                }
            }
        });

        holder.btn_check5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedState5[position] = false;
                } else {
                    checkedState5[position] = true;
                }

            }
        });

    }
    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return pList.size();
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txt_pill_item_list_mediName;
        private TextView txt_pill_item_list_time1, txt_pill_item_list_time2, txt_pill_item_list_time3,
                txt_pill_item_list_time4, txt_pill_item_list_time5;
        private ImageView btn_clock;
        private CheckBox btn_check1, btn_check2, btn_check3, btn_check4, btn_check5;
        private PillList pList;
        private int position;
        private LinearLayout lay1, lay2, lay3, lay4, lay5;


        ItemViewHolder(View itemView) {
            super(itemView);

            txt_pill_item_list_mediName = itemView.findViewById(R.id.txt_pill_item_list_mediName);
            txt_pill_item_list_time1 = itemView.findViewById(R.id.txt_pill_item_list_time1);
            txt_pill_item_list_time2 = itemView.findViewById(R.id.txt_pill_item_list_time2);
            txt_pill_item_list_time3 = itemView.findViewById(R.id.txt_pill_item_list_time3);
            txt_pill_item_list_time4 = itemView.findViewById(R.id.txt_pill_item_list_time4);
            txt_pill_item_list_time5 = itemView.findViewById(R.id.txt_pill_item_list_time5);

            btn_clock = itemView.findViewById(R.id.btn_clock);
            btn_check1 = itemView.findViewById(R.id.btn_check1);
            btn_check2 = itemView.findViewById(R.id.btn_check2);
            btn_check3 = itemView.findViewById(R.id.btn_check3);
            btn_check4 = itemView.findViewById(R.id.btn_check4);
            btn_check5 = itemView.findViewById(R.id.btn_check5);

            lay1 = itemView.findViewById(R.id.lay1);
            lay2 = itemView.findViewById(R.id.lay2);
            lay3 = itemView.findViewById(R.id.lay3);
            lay4 = itemView.findViewById(R.id.lay4);
            lay5 = itemView.findViewById(R.id.lay5);
        }

        void onBind(PillList pList, int position) {
            this.pList = pList;
            this.position = position;

            times = pList.getTimesPerDay();
            oneTime = pList.getOneTime();
            twoTime = pList.getTwoTime();
            threeTime = pList.getThreeTime();
            fourTime = pList.getFourTime();
            fiveTime = pList.getFiveTime();

            time_array = new String[5];
            time_array[0] = oneTime;
            time_array[1] = twoTime;
            time_array[2] = threeTime;
            time_array[3] = fourTime;
            time_array[4] = fiveTime;

            TextView[] pill = new TextView[5];

            pill[0] = txt_pill_item_list_time1;
            pill[1] = txt_pill_item_list_time2;
            pill[2] = txt_pill_item_list_time3;
            pill[3] = txt_pill_item_list_time4;
            pill[4] = txt_pill_item_list_time5;

            for(int i = 0; i < pList.getTimesPerDay(); i++) {
                if (time_array[i] != null) {
                    if (Integer.parseInt(time_array[i].substring(0, 2)) < 12) {
                        pill[i].setText("오전 " + time_array[i].substring(0, 2) + " : "
                                + time_array[i].substring(3, 5));
                    } else if (Integer.parseInt(time_array[i].substring(0, 2)) > 12) {
                        int n = Integer.parseInt(time_array[i].substring(0, 2));
                        if (n - 12 < 10) {
                            pill[i].setText("오후 0" + Integer.toString(n - 12) + " : " + time_array[i].substring(3, 5));
                            //pill[i].setVisibility(View.VISIBLE);
                        } else {
                            pill[i].setText("오후 " + Integer.toString(n - 12) + " : " + time_array[i].substring(3, 5));
                            //pill[i].setVisibility(View.VISIBLE);
                        }
                    } else {
                        pill[i].setText("오후 " + time_array[i].substring(0, 2) + " : " + time_array[i].substring(3, 5));
                        //pill[i].setVisibility(View.VISIBLE);
                    }
                }
            }

            txt_pill_item_list_mediName.setText(pList.getMediName());
            changeVisibility(selectedItems.get(position));

            itemView.setOnClickListener(this);
            txt_pill_item_list_mediName.setOnClickListener(this);
            txt_pill_item_list_time1.setOnClickListener(this);
            txt_pill_item_list_time2.setOnClickListener(this);
            txt_pill_item_list_time3.setOnClickListener(this);
            txt_pill_item_list_time4.setOnClickListener(this);
            txt_pill_item_list_time5.setOnClickListener(this);
            btn_clock.setOnClickListener(this);

            lay1.setOnClickListener(this);
            lay2.setOnClickListener(this);
            lay3.setOnClickListener(this);
            lay4.setOnClickListener(this);
            lay5.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lay_pill:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;
                case R.id.btn_clock:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;
                case R.id.txt_pill_item_list_mediName:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;
            }

        }

        /**
         * 클릭된 Item의 상태 변경
         * @param isExpanded Item을 펼칠 것인지 여부
         */
        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 50;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            MediId = pList.getMediId();
            final String two = pList.getTwoTime();
            final String three = pList.getThreeTime();
            final String four = pList.getFourTime();
            final String five = pList.getFiveTime();

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(150);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView의 높이 변경
                    lay1.getLayoutParams().height = value;
                    lay1.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    lay1.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

                    // imageView의 높이 변경
                    lay2.getLayoutParams().height = value;
                    lay2.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    if (two.equals("null")) {
                        lay2.setVisibility(isExpanded ? View.GONE : View.GONE);
                    } else {
                        lay2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    }

                    // imageView의 높이 변경
                    lay3.getLayoutParams().height = value;
                    lay3.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    if (three.equals("null")) {
                        lay3.setVisibility(isExpanded ? View.GONE : View.GONE);
                    } else {
                        lay3.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    }

                    // imageView의 높이 변경
                    lay4.getLayoutParams().height = value;
                    lay4.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    if (four.equals("null")) {
                        lay4.setVisibility(isExpanded ? View.GONE : View.GONE);
                    } else {
                        lay4.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    }

                    // imageView의 높이 변경
                    lay5.getLayoutParams().height = value;
                    lay5.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    if (five.equals("null")) {
                        lay5.setVisibility(isExpanded ? View.GONE : View.GONE);
                    } else {
                        lay5.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    }
                }
            });
            // Animation start
            va.start();
        }
    }
}
