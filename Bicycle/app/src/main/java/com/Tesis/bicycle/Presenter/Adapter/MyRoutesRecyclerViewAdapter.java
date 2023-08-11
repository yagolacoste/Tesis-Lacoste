package com.Tesis.bicycle.Presenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.R;

import java.util.List;

public class MyRoutesRecyclerViewAdapter extends RecyclerView.Adapter<MyRoutesRecyclerViewAdapter.ViewHolder> {

    private  List<RouteDetailsDto> items;
    private  OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MyRoutesRecyclerViewAdapter(List<RouteDetailsDto> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_routes,parent,false);
        MyRoutesRecyclerViewAdapter.ViewHolder viewHolder=new MyRoutesRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.txtValueNameRoute.setText(items.get(position).getName());
//        holder.txtValueDescription.setText(items.get(position).getDescription());
//        holder.txtValueDistance.setText("0");//ver distancia
        RouteDetailsDto route=items.get(position);
        holder.bind(route);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtValueNameRoute,txtValueDescription,txtValueDistance;
        //imagen
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValueNameRoute=itemView.findViewById(R.id.txtValueNameRoute);
            txtValueDescription=itemView.findViewById(R.id.txtValueDescription);
            txtValueDistance=itemView.findViewById(R.id.txtValueDistance);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(items.get(position));
                    }
                }
            });
        }

        public void bind(RouteDetailsDto route) {
            txtValueNameRoute.setText(route.getName());
            txtValueDescription.setText(route.getDescription());
            txtValueDistance.setText(String.valueOf(route.getDistance()));
        }
    }
}
