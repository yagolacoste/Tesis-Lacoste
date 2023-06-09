package com.Tesis.bicycle.Presenter.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;
import com.Tesis.bicycle.Presenter.ListView.RankingListViewActivity;
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
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtValueNameRoute.setText(String.valueOf(battles.get(position).getRouteName()));
        holder.txtValueCompleteDate.setText(String.valueOf(battles.get(position).getCompleteDate()));
        holder.txtValueCantParticipant.setText(String.valueOf(battles.get(position).getCantParticipant()));
        holder.battleDto=battles.get(position);
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

        private BattleDto battleDto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValueNameRoute=(TextView) itemView.findViewById(R.id.txtValueNameRoute);
            txtValueCompleteDate=(TextView) itemView.findViewById(R.id.txtValueCompleteDate);
            txtValueCantParticipant=itemView.findViewById(R.id.txtValueCantParticipant);
            btn_Information=itemView.findViewById(R.id.btn_Information);

            rootView=itemView;

            btn_Information.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(rootView.getContext(), RankingListViewActivity.class);
                   intent.putExtra(Constants.BATTLE_ITEM,battleDto);
                    rootView.getContext().startActivity(intent);
                }
            });


        }
    }
}
