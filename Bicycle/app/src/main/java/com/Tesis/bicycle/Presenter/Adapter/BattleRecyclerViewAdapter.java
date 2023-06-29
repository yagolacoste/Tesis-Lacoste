package com.Tesis.bicycle.Presenter.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.StatisticsDto;
import com.Tesis.bicycle.Model.Status;
import com.Tesis.bicycle.Presenter.ListView.RankingListViewActivity;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.google.android.material.chip.Chip;

import java.util.List;

public class BattleRecyclerViewAdapter extends RecyclerView.Adapter<BattleRecyclerViewAdapter.ViewHolder> {

    private List<BattleDto> battles;
      private OnItemClickListener listener;

      private Long userId;



    public BattleRecyclerViewAdapter(List<BattleDto> battles,Long userId){
        this.battles=battles;
        this.userId=userId;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_battle,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtValueNameRoute.setText(String.valueOf(battles.get(position).getRoute().getName()));
        holder.txtValueCompleteDate.setText(String.valueOf(battles.get(position).getCompleteDate()));
        holder.txtValueCantParticipant.setText(String.valueOf(battles.get(position).getCantParticipant()));
        holder.status.setText(battles.get(position).getStatus());
        if(battles.get(position).getStatus().equals(Status.FINISHED.getStatus())){
            holder.status.setChipBackgroundColor(ColorStateList.valueOf(Color.RED));
            holder.itemView.setOnClickListener(null);
        }
        else
            if(battles.get(position).getStatus().equals(Status.PROGRESS.getStatus())){
                holder.status.setChipBackgroundColor(ColorStateList.valueOf(Color.BLUE));
                holder.itemView.setOnClickListener(null);
            }
            else {
                holder.status.setChipBackgroundColor(ColorStateList.valueOf(Color.GRAY));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = holder.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && listener != null) {
                            listener.onItemClick(battles.get(position));
                        }
                    }
                });
            }

        holder.battleDto=battles.get(position);


    }

    @Override
    public int getItemCount() {
        return battles.size();
    }

    public interface OnItemClickListener{
        void onItemClick(BattleDto battleDto);
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {
        //agregar la imagen despues del mapa en chiquito
        private TextView txtValueNameRoute, txtValueCompleteDate, txtValueCantParticipant;

        private Button btn_Information;
        private View rootView;

        private Chip status;

        private BattleDto battleDto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValueNameRoute = (TextView) itemView.findViewById(R.id.txtValueNameRoute);
            txtValueCompleteDate = (TextView) itemView.findViewById(R.id.txtValueCompleteDate);
            txtValueCantParticipant = itemView.findViewById(R.id.txtValueCantParticipant);
            btn_Information = itemView.findViewById(R.id.btn_Information);
            status = (Chip) itemView.findViewById(R.id.chipStatus);

            rootView = itemView;

            btn_Information.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(rootView.getContext(), RankingListViewActivity.class);
                    intent.putExtra(Constants.BATTLE_ITEM, battleDto);
                    rootView.getContext().startActivity(intent);
                }
            });

        }
    }
}
