package com.Tesis.bicycle.Activity.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.Tesis.bicycle.Activity.EditProfileActivity;
import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.StoredDocumentViewModel;
import com.Tesis.bicycle.ViewModel.UserViewModel;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TAB_NAME = "ARG_TAB_NAME";

    private TextView titleName,titleLastName,postsAge,postWeight,postHeight,profilePhone,profileEmail;

    private Button editProfile;
    private CircleImageView imageUser;

    private UserViewModel userViewModel;
    private AccessTokenRoomViewModel accessTokenRoomViewModel;

    private StoredDocumentViewModel storedDocumentViewModel;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(@StringRes int tabName) {
        UserFragment frg = new UserFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_NAME, tabName);
        frg.setArguments(args);
        return frg;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user, container, false);
        imageUser=view.findViewById(R.id.profileImg);
        titleName=view.findViewById(R.id.titleName);
        titleLastName=view.findViewById(R.id.titleLastName);
        postsAge=view.findViewById(R.id.postsAge);
        postWeight=view.findViewById(R.id.postWeight);
        postHeight=view.findViewById(R.id.postHeight);
        profilePhone=view.findViewById(R.id.profilePhone);
        profileEmail=view.findViewById(R.id.profileEmail);
        editProfile=view.findViewById(R.id.editButton);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        accessTokenRoomViewModel=new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);
        storedDocumentViewModel=new ViewModelProvider(this).get(StoredDocumentViewModel.class);
        loadData();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
        return view;
    }

    private void updateData() {
        accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(),response->{
            if(response.getAccessToken()!=null){
                userViewModel.getById(response.getId()).observe(getViewLifecycleOwner(),resp->{
                    if(resp!=null){
                        Intent i=new Intent(getContext(), EditProfileActivity.class);
                        i.putExtra(Constants.USER_ITEM,resp.getId());
                       startActivity(i);
                    }
                });
            }
        });
    }

    private void loadData() {
        accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(),response->{
            if(response.getAccessToken()!=null){
                userViewModel.getById(response.getId()).observe(getViewLifecycleOwner(),resp->{
                    if(resp!=null){
                        titleName.setText(resp.getFirstName());
                        titleLastName.setText(resp.getLastName());
                        postsAge.setText(String.valueOf(resp.getAge()));
                        postWeight.setText(String.valueOf(resp.getWeight()));
                        postHeight.setText(String.valueOf(resp.getHeight()));
                        profilePhone.setText(String.valueOf(resp.getPhone()));
                        profileEmail.setText(String.valueOf(resp.getEmail()));
                        String url = ApiRestConnection.URL_STORED_DOCUMENT + "download?fileName=" + resp.getFileName();
                        final Picasso picasso = new Picasso.Builder(getContext())
                                .downloader(new OkHttp3Downloader(ClientRetrofit.getHttp()))
                                .build();
                        picasso.load(url)
                                .error(R.drawable.image_not_found)
                                .into(imageUser);
                    }
                });
            }
        });
    }


}