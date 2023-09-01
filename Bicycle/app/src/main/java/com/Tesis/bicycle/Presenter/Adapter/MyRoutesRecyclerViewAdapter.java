package com.Tesis.bicycle.Presenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Presenter.OpenStreetMap;
import com.Tesis.bicycle.R;

import org.osmdroid.views.MapView;

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
        RouteDetailsDto route=items.get(position);
        holder.bind(route);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtValueNameRoute,txtValueDescription,txtValueDistance;
        private MapView mapImage;
        private OpenStreetMap openStreetMap;

        private ImageView imgRoute;
        //imagen
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValueNameRoute=itemView.findViewById(R.id.txtValueNameRoute);
            txtValueDescription=itemView.findViewById(R.id.txtValueDescription);
            txtValueDistance=itemView.findViewById(R.id.txtValueDistance);
            mapImage=new MapView(itemView.getContext());
            openStreetMap=new OpenStreetMap(mapImage);
            imgRoute=itemView.findViewById(R.id.imgRoute);
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
