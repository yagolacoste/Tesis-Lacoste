package com.Tesis.bicycle.Presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Dto.Room.TrackedDto;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.TrackedRoomViewModel;

import java.util.List;

public class TrackedRecyclerViewAdapter extends RecyclerView.Adapter<TrackedRecyclerViewAdapter.ViewHolder> {


    private List<TrackedDto> resp;

    private TrackedRoomViewModel trackedRoomViewModel;

    private ViewModelStoreOwner viewModelStoreOwner;

    private OnItemClickListener listener;




    public TrackedRecyclerViewAdapter(List<TrackedDto> resp, Context context, ViewModelStoreOwner viewModelStoreOwner) {
        this.resp = resp;
        this.trackedRoomViewModel = new ViewModelProvider(viewModelStoreOwner).get(TrackedRoomViewModel.class);
        this.viewModelStoreOwner = viewModelStoreOwner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_tracked,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrackedRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.txtValueTitleTracked.setText(resp.get(position).getTitle());
        holder.txtValueDescriptionTracked.setText(resp.get(position).getDescription());
        holder.txtValueDistanceTracked.setText(String.valueOf(resp.get(position).getDistance()));
        holder.txtValueTimeTracked.setText(String.valueOf(resp.get(position).getTime()));

    }

    public void updateList(List<TrackedDto> newList) {
        resp.clear();
        resp.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return resp.size();
    }

    public OnItemClickListener getListener(OnItemClickListener onItemClickListener) {
        return listener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public List<TrackedDto> getResp() {
        return resp;
    }

    public void setResp(List<TrackedDto> resp) {
        this.resp = resp;
    }

    public interface OnItemClickListener {
        void onDiscardClick(int position);

        void onSaveCardClick(int position);
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtValueTitleTracked,txtValueDescriptionTracked,txtValueDistanceTracked,txtValueTimeTracked;

        private Button discardButtonTracked,saveButtonTracked;

        private View rootView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValueTitleTracked=itemView.findViewById(R.id.txtValueTitleTracked);
            txtValueDescriptionTracked=itemView.findViewById(R.id.txtValueDescriptionTracked);
            txtValueDistanceTracked=itemView.findViewById(R.id.txtValueDistanceTracked);
            txtValueTimeTracked=itemView.findViewById(R.id.txtValueTimeTracked);
            discardButtonTracked=itemView.findViewById(R.id.discardButtonTracked);
            saveButtonTracked=itemView.findViewById(R.id.saveButtonTracked);
            rootView=itemView;
            discardButtonTracked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    trackedRoomViewModel.deleteById(resp.get(getAdapterPosition()).getRouteId());
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDiscardClick(position);
                        }
                    }
                }
            });

            saveButtonTracked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onSaveCardClick(position);
                        }
                    }
                }
            });


        }
    }
}
