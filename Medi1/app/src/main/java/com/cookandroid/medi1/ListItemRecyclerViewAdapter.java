package com.cookandroid.medi1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListItemRecyclerViewAdapter extends RecyclerView.Adapter<ListItemRecyclerViewAdapter.ListItemRecyclerViewHolder> {
    private ArrayList<ListItem> sList;
    private LayoutInflater sInflate;
    private Context sContext;
    MyDBHelper myHelper;

    public ListItemRecyclerViewAdapter(ArrayList<ListItem> sList, Context sContext) {
        this.sList = sList;
        this.sInflate = LayoutInflater.from(sContext);
        this.sContext = sContext;
    }

    @NonNull
    @Override
    public ListItemRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = sInflate.inflate(R.layout.rv_item_list, viewGroup, false);
        ListItemRecyclerViewHolder viewHolder = new ListItemRecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemRecyclerViewHolder holder, final int i) {
        holder.mediName.setText(sList.get(i).mediName);
        holder.startDate.setText(sList.get(i).startDate);
        holder.endDate.setText(sList.get(i).endDate);
        holder.timesPerDay.setText(String.valueOf(sList.get(i).timesPerDay));
        holder.lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(sContext, R.style.MyAlertDialogStyle);
                dlg.setTitle("다음 활동");
                dlg.setMessage("수행할 일을 선택하세요.");
                dlg.setPositiveButton("약 설정 수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(sContext, ModifyActivity.class);
                        intent.putExtra("mediName", sList.get(i).mediName);
                        intent.putExtra("startDate", sList.get(i).startDate);
                        intent.putExtra("endDate", sList.get(i).endDate);
                        intent.putExtra("timesPerDay", sList.get(i).timesPerDay);
                        intent.putExtra("mon", sList.get(i).mon);
                        intent.putExtra("tue", sList.get(i).tue);
                        intent.putExtra("wed", sList.get(i).wed);
                        intent.putExtra("thu", sList.get(i).thu);
                        intent.putExtra("fri", sList.get(i).fri);
                        intent.putExtra("sat", sList.get(i).sat);
                        intent.putExtra("sun", sList.get(i).sun);
                        intent.putExtra("oneTime", sList.get(i).oneTime);
                        intent.putExtra("twoTime", sList.get(i).twoTime);
                        intent.putExtra("threeTime", sList.get(i).threeTime);
                        intent.putExtra("fourTime", sList.get(i).fourTime);
                        intent.putExtra("fiveTime", sList.get(i).fiveTime);
                        intent.putExtra("mediId", sList.get(i).mediId);
                        sContext.startActivity(intent);
                    }
                });
                dlg.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myHelper = new MyDBHelper(sContext);
                        myHelper.deleteItem(sList.get(i).mediId);
                        sList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, sList.size());
                    }
                });
                dlg.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public static class ListItemRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView mediName, startDate, endDate, timesPerDay;
        public LinearLayout lay1;

        public ListItemRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            mediName = itemView.findViewById(R.id.txt_rv_item_list_mediName);
            startDate = itemView.findViewById(R.id.txt_rv_item_list_startDate);
            endDate = itemView.findViewById(R.id.txt_rv_item_list_endDate);
            timesPerDay = itemView.findViewById(R.id.txt_rv_item_list_timesPerDay);
            lay1 = itemView.findViewById(R.id.lay1);
        }
    }
}