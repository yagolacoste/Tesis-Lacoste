package com.Tesis.bicycle.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.UserAppDto;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.SignupRequest;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AuthViewModel;
import com.Tesis.bicycle.ViewModel.StoredDocumentViewModel;
import com.Tesis.bicycle.ViewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileActivity extends AppCompatActivity {

    private File f;
    private UserViewModel userViewModel;
    private StoredDocumentViewModel storedDocumentViewModel;
    private Button btnUploadImageUpdate, btnStoredDataUpdate;
    private CircleImageView imageUserUpdate;
    private EditText edtNameUserUpdate, edtLastNameUpdate, edtAgeUpdate, edtHeightUpdate, edtTelephoneUpdate,
            edtWeightUpdate;

    private TextInputLayout txtInputNameUser, txtInputLastName, txtInputAge,
            txtInputTypeHeight,
            txtInputTelephone, txtInputTypeWeight;
    private final static int LOCATION_REQUEST_CODE = 23;

    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        id = getIntent().getExtras().getLong(Constants.USER_ITEM);
        this.init();
        this.initViewModel();
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.userViewModel = vmp.get(UserViewModel.class);
        this.storedDocumentViewModel = vmp.get(StoredDocumentViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Gracias por conceder los permisos para " +
                            "leer el almacenamiento, estos permisos son necesarios para poder " +
                            "escoger tu foto de perfil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No podemos realizar el registro si no nos concedes los permisos para leer el almacenamiento.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void init() {
        btnStoredDataUpdate = findViewById(R.id.btnStoredDataUpdate);
        btnUploadImageUpdate = findViewById(R.id.btnUploadImageUpdate);
        imageUserUpdate = findViewById(R.id.imageUserUpdate);
        edtNameUserUpdate = findViewById(R.id.edtNameUserUpdate);
        edtLastNameUpdate = findViewById(R.id.edtLastNameUpdate);
        edtAgeUpdate = findViewById(R.id.edtAgeUpdate);
        edtHeightUpdate = findViewById(R.id.edtHeightUpdate);
        edtTelephoneUpdate = findViewById(R.id.edtTelephoneUpdate);
        edtWeightUpdate = findViewById(R.id.edtWeightUpdate);
        //TextInputLayout
        txtInputNameUser = findViewById(R.id.txtInputNameUser);
        txtInputLastName = findViewById(R.id.txtInputLastName);
        txtInputAge = findViewById(R.id.txtInputAge);
        txtInputTypeHeight = findViewById(R.id.txtInputHeight);
        txtInputTelephone = findViewById(R.id.txtInputTelephone);
        txtInputTypeWeight = findViewById(R.id.txtInputWeight);
        btnUploadImageUpdate.setOnClickListener(v -> {
            this.uploadImage();
        });
        btnStoredDataUpdate.setOnClickListener(v -> {
            this.storedData();
        });

    }

    private void storedData() {
        UserAppDto user;
        user = new UserAppDto();
        try {
            if (!edtNameUserUpdate.getText().toString().equals(""))
                user.setFirstName(edtNameUserUpdate.getText().toString());
            if (!edtLastNameUpdate.getText().toString().equals(""))
                user.setLastName(edtLastNameUpdate.getText().toString());
            if (!edtHeightUpdate.getText().toString().equals(""))
                user.setHeight(Integer.valueOf(edtHeightUpdate.getText().toString()));
            if (!edtWeightUpdate.getText().toString().equals(""))
                user.setWeight(Integer.valueOf(edtWeightUpdate.getText().toString()));
            if (!edtAgeUpdate.getText().toString().equals(""))
                user.setAge(Integer.parseInt(edtAgeUpdate.getText().toString()));
            if (!edtTelephoneUpdate.getText().toString().equals(""))
                user.setPhone(edtTelephoneUpdate.getText().toString());
            if(f!=null){
                RequestBody rb = RequestBody.create(f, MediaType.parse("multipart/form-data")), somedata;
                String filename = f.getName();
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", f.getName(), rb);
                somedata = RequestBody.create(filename, MediaType.parse("text/plain"));
                this.storedDocumentViewModel.save(part, somedata).observe(this, response -> {
                    user.setPhoto(response);
                    this.userViewModel.update(user, id).observe(this, resp -> {
                        if (resp) {
                            successMessage("Estupendo! " + "Su información ha sido guardada con éxito en el sistema.");
                            this.finish();
                        } else {
                            warningMessage("Se ha producido un error : ");
                        }
                    });

                });
            }else {
                this.userViewModel.update(user, id).observe(this, resp -> {
                    if (resp) {
                        successMessage("Estupendo! " + "Su información ha sido guardada con éxito en el sistema.");
                        this.finish();
                    } else {
                        warningMessage("Se ha producido un error : ");
                    }
                });
            }
        } catch (Exception e) {
            warningMessage("Se ha producido un error : " + e.getMessage());
        }
    }

    private void uploadImage() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/");
        startActivityForResult(Intent.createChooser(i, "Select the application"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            final String realPath = getRealPathFromURI(uri);
            this.f = new File(realPath);
            this.imageUserUpdate.setImageURI(uri);
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String result;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            result = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Good Job!")
                .setContentText(message).show();
    }

    public void errorMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }
}