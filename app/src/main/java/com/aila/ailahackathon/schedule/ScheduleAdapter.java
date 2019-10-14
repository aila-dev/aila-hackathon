package com.aila.ailahackathon.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aila.ailahackathon.MainActivity;
import com.aila.ailahackathon.R;
import com.aila.ailahackathon.model.ScheduleModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private List<ScheduleModel> scheduleList;

    public ScheduleAdapter(List<ScheduleModel> list){
        scheduleList = list;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View scheduleView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_list,parent,false);

        return new ScheduleViewHolder(scheduleView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ScheduleAdapter.ScheduleViewHolder holder, final int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        ScheduleModel schedule = scheduleList.get(position);
        String date = simpleDateFormat.format(schedule.getWaktu().toDate());
        holder.tvJudul.setText(schedule.getJudul());
        holder.tvWaktu.setText(date);
//        holder.tvDeskripsi.setText(schedule.getIsi());

        holder.tvJudul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder{
        private TextView tvJudul,tvDeskripsi,tvWaktu;
        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.list_judul);
//            tvDeskripsi = itemView.findViewById(R.id.list_deskripsi);
            tvWaktu = itemView.findViewById(R.id.list_waktu);
        }
    }
}
