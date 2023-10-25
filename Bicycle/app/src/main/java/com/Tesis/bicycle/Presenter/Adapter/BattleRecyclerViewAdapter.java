package com.Tesis.bicycle.Presenter.Adapter;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
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

import com.Tesis.bicycle.Activity.ui.Fragment.RankingFragment;
import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;
import com.Tesis.bicycle.Model.Status;
import com.Tesis.bicycle.R;
import com.google.android.material.chip.Chip;

import java.util.List;

public class BattleRecyclerViewAdapter extends RecyclerView.Adapter<BattleRecyclerViewAdapter.ViewHolder> {

    private List<BattleDto> battles;
      private OnItemClickListener listener;




    public BattleRecyclerViewAdapter(List<BattleDto> battles){
        this.battles=battles;
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
        holder.txtValueCompleteDate.setText(String.valueOf(battles.get(position).getCompleteDateToString()));
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
            //holder.imgBattle.setImageAlpha(R.drawable.ic_battle);

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

        private ImageView imgBattle;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValueNameRoute = (TextView) itemView.findViewById(R.id.txtValueNameRoute);
            txtValueCompleteDate = (TextView) itemView.findViewById(R.id.txtValueCompleteDate);
            txtValueCantParticipant = itemView.findViewById(R.id.txtValueCantParticipant);
            btn_Information = itemView.findViewById(R.id.btn_Information);
            status = (Chip) itemView.findViewById(R.id.chipStatus);
            imgBattle=itemView.findViewById(R.id.imgBattle);

            rootView = itemView;

            btn_Information.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putSerializable(Constants.BATTLE_ITEM, battleDto);
                    RankingFragment rankingFragment=new RankingFragment();
                    rankingFragment.setArguments(args);
                    replaceFragment(rankingFragment);
                }
                private void replaceFragment(Fragment fragment){
                    FragmentManager fragmentManager=((AppCompatActivity) rootView.getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//                    fragmentTransaction.addToBackStack(fragment.getTag());
                    fragmentTransaction.replace(R.id.frame_layout,fragment);
                    fragmentTransaction.commitNow();
                }
            });

        }
    }
}
