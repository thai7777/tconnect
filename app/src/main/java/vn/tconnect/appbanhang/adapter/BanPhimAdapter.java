package vn.tconnect.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import vn.tconnect.appbanhang.R;
import vn.tconnect.appbanhang.models.BanPhimModel;
import vn.tconnect.appbanhang.models.BanPhimModel;

public class BanPhimAdapter extends RecyclerView.Adapter<BanPhimAdapter.ViewHolder>{
    Context context;
    List<BanPhimModel> list;

    public BanPhimAdapter(Context context, List<BanPhimModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BanPhimAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banphim_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.popImg);
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImg;
        TextView name,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popImg=itemView.findViewById(R.id.MH_img);
            name= itemView.findViewById(R.id.MH_name);
            price= itemView.findViewById(R.id.MH_price);
        }
    }
}
