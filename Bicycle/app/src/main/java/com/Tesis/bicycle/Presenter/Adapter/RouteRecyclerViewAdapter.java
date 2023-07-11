package com.Tesis.bicycle.Presenter.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Activity.RouteDetailsActivity;
import com.Tesis.bicycle.Activity.TrackingActivity;
import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Presenter.ListView.StatisticsListViewActivity;
import com.Tesis.bicycle.Presenter.OpenStreetMap;
import com.Tesis.bicycle.R;

import org.osmdroid.views.MapView;

import java.util.List;

public class RouteRecyclerViewAdapter extends RecyclerView.Adapter<RouteRecyclerViewAdapter.ViewHolder> {

    public List<RouteDetailsDto> items;

    public RouteRecyclerViewAdapter(List<RouteDetailsDto> items) {
        this.items = items;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_my_route_list_view,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.route=items.get(position);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtValueDistance,txtValueTime;

        private MapView mapView;

        private OpenStreetMap mapValueView;

        private Button runButton,statsButton;
        private RouteDetailsDto route;
        private View rootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValueDistance=itemView.findViewById(R.id.txtValueDistance);
            txtValueTime=itemView.findViewById(R.id.txtValueTime);
            mapView=itemView.findViewById(R.id.mapValueView);
            runButton=itemView.findViewById(R.id.runButton);
            statsButton=itemView.findViewById(R.id.statsButton);

            rootView=itemView;

            run.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // se va a la activity
                    Intent intent=new Intent(rootView.getContext(), TrackingActivity.class);
                    intent.setAction(Constants.REPLAY_MY_ROUTE);
                    intent.putExtra(Constants.ROUTE,route);
                    rootView.getContext().startActivity(intent);
                }
            });

            statistics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(rootView.getContext(), StatisticsListViewActivity.class);
                    intent.setAction(Constants.VIEW_STATISTICS);
                    intent.putExtra(Constants.ROUTE_ID,route.getId());
                    rootView.getContext().startActivity(intent);
                }
            });
            

        }
    }


}
