package com.aila.ailahackathon.fragment.makan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aila.ailahackathon.R;
import com.aila.ailahackathon.fragment.minum.MinumAdapter;
import com.aila.ailahackathon.model.MakanModel;
import com.aila.ailahackathon.model.MinumModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MakanAdapter extends RecyclerView.Adapter<MakanAdapter.MakanViewHolder> {
    public List<MakanModel> listMakan;

    public MakanAdapter(List<MakanModel> listMakan) {
        this.listMakan = listMakan;
    }

    @NonNull
    @Override
    public MakanAdapter.MakanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View makanView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.makan_list,parent,false);

        return new MakanViewHolder(makanView);
    }

    @Override
    public void onBindViewHolder(@NonNull MakanAdapter.MakanViewHolder holder, int position) {
        MakanModel makanModel = listMakan.get(position);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//        String tanggal = simpleDateFormat.format(minumModel.getTanggal().toDate());

        holder.tvNama.setText("Hari ini anda makan "+makanModel.getNama_makanan() + " per hari");
        holder.tvJenis.setText("Jenis makanan yg anda makan "+makanModel.getJenis_makanan() + " per hari");
        holder.tvJumlahKalori.setText("Jumlah kalori yang anda dapat "+makanModel.getJumlah_kalori());
        holder.tvTanggal.setText(makanModel.getTanggal());
    }

    @Override
    public int getItemCount() {
        return listMakan.size();
    }

    public class MakanViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNama,tvJumlahKalori,tvTanggal,tvJenis;
        public MakanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.makan_apa);
            tvJumlahKalori = itemView.findViewById(R.id.jumlah_kalori);
            tvTanggal = itemView.findViewById(R.id.tanggal_makan);
            tvJenis = itemView.findViewById(R.id.jenis_makanan);
        }
    }
}
