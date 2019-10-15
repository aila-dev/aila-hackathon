package com.aila.ailahackathon.fragment.minum;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aila.ailahackathon.R;
import com.aila.ailahackathon.model.MinumModel;
import com.aila.ailahackathon.schedule.ScheduleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MinumAdapter extends RecyclerView.Adapter<MinumAdapter.MinumViewHolder> {
    private static final String TAG = "MinumAdapter";
    private List<MinumModel> listMinum;

    public MinumAdapter(List<MinumModel> list){
        listMinum = list;
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
        MinumModel minumModel = listMinum.get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String tanggal = simpleDateFormat.format(minumModel.getTanggal().toDate());

        holder.tvMinumBerapa.setText("Hari ini anda minum "+minumModel.getMinum_perhari() + "/ hari");
        holder.tvTotalMinum.setText("Aila Menyarankan untuk "+minumModel.getTotal_minum() + " / hari");
        holder.tvTanggalMinum.setText(" "+tanggal);
    }

    @Override
    public int getItemCount() {
        return listMinum.size();
    }

    public class MinumViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTotalMinum,tvMinumBerapa,tvTanggalMinum;
        public MinumViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTotalMinum = itemView.findViewById(R.id.total_minum);
            tvMinumBerapa = itemView.findViewById(R.id.minum_berapa);
            tvTanggalMinum = itemView.findViewById(R.id.tanggal_minum);
        }
    }
}
