package com.Tesis.bicycle.Presenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;
import com.Tesis.bicycle.Dto.ApiRest.Battle.NewBattleDto;
import com.Tesis.bicycle.R;

import java.util.List;

public class BattleRecyclerViewAdapter extends RecyclerView.Adapter<BattleRecyclerViewAdapter.ViewHolder> {

    List<BattleDto> battles;

    public BattleRecyclerViewAdapter(List<BattleDto> battles){
        this.battles=battles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_battle,parent,false);
        BattleRecyclerViewAdapter.ViewHolder viewHolder=new BattleRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BattleRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.txtValueNameRoute.setText(String.valueOf(battles.get(position).getRouteName()));
        holder.txtValueCompleteDate.setText(String.valueOf(battles.get(position).getCompleteDate()));
        holder.txtValueCantParticipant.setText(String.valueOf(battles.get(position).getCantParticipant()));
    }

    @Override
    public int getItemCount() {
        return battles.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //agregar la imagen despues del mapa en chiquito
        private TextView txtValueNameRoute,txtValueCompleteDate,txtValueCantParticipant;

        private Button btn_Information;
        private View rootView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValueNameRoute=(TextView) itemView.findViewById(R.id.txtValueNameRoute);
            txtValueCompleteDate=(TextView) itemView.findViewById(R.id.txtValueCompleteDate);
            txtValueCantParticipant=itemView.findViewById(R.id.txtValueCantParticipant);
//            btn_Information=itemView.findViewById(R.id.btn_Information);
//            btn_Information.setOnClickListener(view -> {
//
//            });
        }
    }
}
