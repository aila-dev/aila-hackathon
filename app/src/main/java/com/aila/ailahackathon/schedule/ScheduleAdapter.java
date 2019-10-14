package com.aila.ailahackathon.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aila.ailahackathon.R;
import com.aila.ailahackathon.model.ScheduleModel;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private List<ScheduleModel> scheduleList;
    @NonNull
    @Override
    public ScheduleAdapter.ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View scheduleView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_list,parent,false);

        return new ScheduleViewHolder(scheduleView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ScheduleViewHolder holder, int position) {
        ScheduleModel schedule = scheduleList.get(position);
        holder.tvJudul.setText(schedule.getJudul());
        holder.tvWaktu.setText(schedule.getWaktu().toString());
        holder.tvDeskripsi.setText(schedule.getIsi());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder{
        private TextView tvJudul,tvDeskripsi,tvWaktu;
        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.list_judul);
            tvDeskripsi = itemView.findViewById(R.id.list_deskripsi);
            tvWaktu = itemView.findViewById(R.id.list_waktu);
        }
    }
}
