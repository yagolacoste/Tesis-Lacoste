package com.Tesis.bicycle.Presenter.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Activity.TrackingActivity;
import com.Tesis.bicycle.Activity.ui.Fragment.StatisticsFragment;
import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.Presenter.OpenStreetMap;
import com.Tesis.bicycle.R;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.osmdroid.views.MapView;

import java.util.List;

public class RouteRecyclerViewAdapter extends RecyclerView.Adapter<RouteRecyclerViewAdapter.ViewHolder> {

    public  List<RouteDetailsDto> items;

    private  OnItemClickListener listener;


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
        holder.txtValueTitle.setText(items.get(position).getName());
        holder.txtValueDistance.setText(items.get(position).getDistance());
        holder.txtValueTime.setText(String.valueOf(items.get(position).getTime()));
        holder.openStreetMap.initLayer(holder.rootView.getContext(),items.get(position).getCoordinates().get(0));
        holder.openStreetMap.drawStatic((items.get(position).getCoordinates()));
        holder.txtValueDescription.setText(items.get(position).getDescription());
        holder.route=items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<RouteDetailsDto> getItems() {
        return items;
    }

    public void setItems(List<RouteDetailsDto> items) {
        this.items = items;
    }

    public OnItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public  class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtValueTitle,txtValueDistance,txtValueTime,txtValueDescription;

        private MapView mapImage;
        private OpenStreetMap openStreetMap;

        private Button runButton,statisButton;
        private RouteDetailsDto route;
        private View rootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValueTitle=itemView.findViewById(R.id.txtValueTitle);
            txtValueDistance=itemView.findViewById(R.id.txtValueDistance);
            txtValueTime=itemView.findViewById(R.id.txtValueTime);
            mapImage=itemView.findViewById(R.id.mapValueView);
            runButton=itemView.findViewById(R.id.runButton);
            statisButton=itemView.findViewById(R.id.statButton);
            rootView=itemView;
            openStreetMap=new OpenStreetMap(mapImage);
            txtValueDescription=itemView.findViewById(R.id.txtValueDescription);

            runButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // se va a la activity
                    Intent intent=new Intent(rootView.getContext(), TrackingActivity.class);
                    intent.setAction(Constants.REPLAY_MY_ROUTE);
                    intent.putExtra(Constants.ROUTE,route);
                    rootView.getContext().startActivity(intent);
                }
            });

            statisButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putString(Constants.ROUTE_ID, route.getId());
                    StatisticsFragment statisticsFragment=new StatisticsFragment();
                    statisticsFragment.setArguments(args);
                    replaceFragment(statisticsFragment);

                }
                private void replaceFragment(Fragment fragment){
                    FragmentManager fragmentManager=((AppCompatActivity) rootView.getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,fragment);
                    fragmentTransaction.commit();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(items.get(position));
                    }
                };
            });
        }
    }
}
