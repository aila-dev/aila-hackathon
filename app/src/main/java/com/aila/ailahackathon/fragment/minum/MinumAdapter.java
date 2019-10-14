package com.aila.ailahackathon.fragment.minum;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aila.ailahackathon.R;
import com.aila.ailahackathon.model.MinumModel;
import com.aila.ailahackathon.schedule.ScheduleAdapter;

import java.util.ArrayList;
import java.util.List;

public class MinumAdapter extends RecyclerView.Adapter<MinumAdapter.MinumViewHolder> {
    private List<MinumModel> listMinum;

    public MinumAdapter(List<MinumModel> list){
        listMinum = list;
    }

    public class MinumViewHolder extends RecyclerView.ViewHolder{
        private TextView tvJudul,tvDeskripsi,tvWaktu;
        public MinumViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvJudul = itemView.findViewById(R.id.list_judul);
//            tvDeskripsi = itemView.findViewById(R.id.list_deskripsi);
//            tvWaktu = itemView.findViewById(R.id.list_waktu);
        }
    }

    @NonNull
    @Override
    public MinumAdapter.MinumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View minumView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.minum_list,parent,false);

        return new MinumViewHolder(minumView);
    }

    @Override
    public void onBindViewHolder(@NonNull MinumViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listMinum.size();
    }
}
