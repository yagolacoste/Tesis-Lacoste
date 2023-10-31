package com.Tesis.bicycle.Activity.ui.Fragment;

import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Tesis.bicycle.Activity.AddFriendActivity;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TAB_NAME = "ARG_TAB_NAME";

    private EditText edtEmailFriendScore;

    private Button btnSearchFriend;

    private TextInputLayout txtEmailFriendScore;

    private AccessTokenRoomViewModel accessTokenRoomViewModel;

    private UserViewModel userViewModel;

    private LinearLayout scoreLayout;

    public ScoreFragment() {
        // Required empty public constructor
    }
    public static ScoreFragment newInstance(@StringRes int tabName) {
        ScoreFragment frg = new ScoreFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_NAME, tabName);
        frg.setArguments(args);;
        return frg;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_score, container, false);
        // Inflate the layout for this fragment
        edtEmailFriendScore=view.findViewById(R.id.edtEmailFriendScore);
        btnSearchFriend=view.findViewById(R.id.btnSearchFriend);
        txtEmailFriendScore=view.findViewById(R.id.txtEmailFriendScore);
        accessTokenRoomViewModel = new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        scoreLayout=view.findViewById(R.id.scoreLayout);
        edtEmailFriendScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtEmailFriendScore.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnSearchFriend.setOnClickListener(v ->{
            searchData();
        });
        return view;
    }

    private void searchData() {
        if (validate()) {
            try {
                String email = edtEmailFriendScore.getText().toString();
                accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(), resp -> {
                    if (resp != null) {
                        Long id = resp.getId();
                        userViewModel.getScore(id, email).observe(getViewLifecycleOwner(), response -> {
                            if (response!=null) {
                                View customView = getLayoutInflater().inflate(R.layout.card_score_item, null);
                                TextView titleWinner = customView.findViewById(R.id.txtWinner);
                                if(response.getPlayerOneScore()>response.getPlayerTwoScore()){
                                    titleWinner.setText("Winner is "+response.getNamePlayerOneComplete());
                                }else if  (response.getPlayerOneScore()==response.getPlayerTwoScore()){
                                    titleWinner.setText("There is a tie");
                                } else titleWinner.setText("Winner is "+response.getNamePlayerTwoComplete());
                                TextView playerOne=customView.findViewById(R.id.txtPlayerOne);
                                TextView playerTwo=customView.findViewById(R.id.txtPlayerTwo);
                                TextView score=customView.findViewById(R.id.txtScore);
                                ImageView imgPLayerOne=customView.findViewById(R.id.imgPlayerOne);
                                ImageView imgPLayerTwo=customView.findViewById(R.id.imgPlayerTwo);
                                playerOne.setText(response.getNamePlayerOneComplete());
                                playerTwo.setText(response.getNamePlayerTwoComplete());
                                String url = ApiRestConnection.URL_STORED_DOCUMENT + "download?fileName=" + response.getFileNamePlayerOne();
                                Picasso picasso = new Picasso.Builder(getContext())
                                        .downloader(new OkHttp3Downloader(ClientRetrofit.getHttp()))
                                        .build();
                                picasso.load(url)
                                        .rotate(270)
                                        .error(R.drawable.image_not_found)
                                        .into(imgPLayerOne);
                                url = ApiRestConnection.URL_STORED_DOCUMENT + "download?fileName=" + response.getFileNamePlayerOne();
                                picasso = new Picasso.Builder(getContext())
                                        .downloader(new OkHttp3Downloader(ClientRetrofit.getHttp()))
                                        .build();
                                picasso.load(url)
                                        .rotate(270)
                                        .error(R.drawable.image_not_found)
                                        .into(imgPLayerTwo);
                                score.setText(response.getPlayerOneScore()+" - "+response.getPlayerTwoScore());
                                scoreLayout.addView(customView);
                            } else {
                                warningMessage("Not exist battle with this user");
                            }
                        });
                    }
                });
            } catch (Exception e) {
                warningMessage("Se ha producido un error : " + e.getMessage());
            }
        }
    }

    private boolean validate() {
        boolean retorno = true;
        String email;
        email = edtEmailFriendScore.getText().toString();

        if (email.isEmpty()) {
            txtEmailFriendScore.setError("enter your friend's email");
            retorno = false;
        } else {
            txtEmailFriendScore.setErrorEnabled(false);
        }
        return retorno;
    }


    public void warningMessage(String message) {
        new SweetAlertDialog(getContext(),
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }
}