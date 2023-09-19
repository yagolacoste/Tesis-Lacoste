package com.Tesis.bicycle.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Tesis.bicycle.Dto.ApiRest.auth.request.SignupRequest;
import com.Tesis.bicycle.Presenter.Notifications;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.StoredDocumentViewModel;
import com.Tesis.bicycle.ViewModel.AuthViewModel;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterActivity extends AppCompatActivity {

    private static final String UPLOAD_DIR="file";
    private File f;

    private AuthViewModel authViewModel;
    private StoredDocumentViewModel storedDocumentViewModel;
    private Button btnUploadImage, btnStoredData;
    private CircleImageView imageUser;
    private EditText edtNameUser, edtLastName, edtAge, edtHeight, edtTelephone,
             edtPasswordUser, edtEmailUser, edtWeight;
    private TextInputLayout txtInputNameUser, txtInputLastName, txtInputAge,
            txtInputTypeHeight,
             txtInputTelephone, txtInputEmailUser, txtInputPasswordUser,txtInputTypeWeight;
    private Notifications notifications;
    private final static int LOCATION_REQUEST_CODE = 23;
    private final static String USER_DEFAULT="user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        this.init();
        this.initViewModel();

    }
    
    
    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.authViewModel = vmp.get(AuthViewModel.class);
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
        btnStoredData = findViewById(R.id.btnStoredData);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        imageUser = findViewById(R.id.imageUser);
        edtNameUser = findViewById(R.id.edtNameUser);
        edtLastName = findViewById(R.id.edtLastName);
        edtAge = findViewById(R.id.edtAge);
        edtHeight = findViewById(R.id.edtHeight);
        edtTelephone = findViewById(R.id.edtTelephone);
        edtPasswordUser = findViewById(R.id.edtPasswordUser);
        edtEmailUser = findViewById(R.id.edtEmailUser);
        edtWeight=findViewById(R.id.edtWeight);
        //TextInputLayout
        txtInputNameUser = findViewById(R.id.txtInputNameUser);
        txtInputLastName = findViewById(R.id.txtInputLastName);
        txtInputAge = findViewById(R.id.txtInputAge);
        txtInputTypeHeight = findViewById(R.id.txtInputHeight);
        txtInputTelephone = findViewById(R.id.txtInputTelephone);
        txtInputEmailUser = findViewById(R.id.txtInputEmailUser);
        txtInputPasswordUser = findViewById(R.id.txtInputPasswordUser);
        txtInputTypeWeight=findViewById(R.id.txtInputWeight);
        notifications=new Notifications(this);
        btnUploadImage.setOnClickListener(v -> {
            this.uploadImage();
        });
        btnStoredData.setOnClickListener(v -> {
            this.storedData();
        });
        ///ONCHANGE LISTENEER A LOS EDITEXT
        edtNameUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputNameUser.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputLastName.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputAge.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputTypeHeight.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtTelephone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputTelephone.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputTypeWeight.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void storedData() {
        SignupRequest user;
        if(validate()){
            user=new SignupRequest();
            try{
                user.setFirstName(edtNameUser.getText().toString());
                user.setLastName(edtLastName.getText().toString());
                user.setHeight(Integer.valueOf(edtHeight.getText().toString()));
                user.setWeight(Integer.valueOf(edtWeight.getText().toString()));
                Set<String>userDefault=new HashSet<String>();
                userDefault.add(USER_DEFAULT);
                user.setRole(userDefault);
                user.setAge(Integer.parseInt(edtAge.getText().toString()));
                user.setPhone(edtTelephone.getText().toString());
                Bitmap rotatedBitmap = rotateImageBitmap(f.getAbsolutePath());
                File rotatedFile = createFileFromBitmap(rotatedBitmap);
                RequestBody rb = RequestBody.create(rotatedFile, MediaType.parse("multipart/form-data")),somedata;
                String filename = f.getName();
                MultipartBody.Part part = MultipartBody.Part.createFormData(UPLOAD_DIR, f.getName(), rb);
                somedata = RequestBody.create(filename, MediaType.parse("text/plain"));
                this.storedDocumentViewModel.save(part,somedata).observe(this,response->{
                    user.setPhoto(response);
                    user.setPassword(edtPasswordUser.getText().toString());
                    user.setEmail(edtEmailUser.getText().toString());
                    this.authViewModel.registerUser(user).observe(this, resp->{
                        if(resp){
                            notifications.successMessage("New User!","Great! Your information has been successfully saved in the system.");
                            //this.finish();
                        }
                        else {
                           notifications.warningMessage("Se ha producido un error : ");
                        }
                    });

                });
            }catch (Exception e){
               notifications.warningMessage("Se ha producido un error : " + e.getMessage());
            }
        }
    }

    // Método para rotar una imagen Bitmap
    private Bitmap rotateImageBitmap(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int orientation = exif != null ? exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL) : ExifInterface.ORIENTATION_NORMAL;

        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            rotationAngle = 90;
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            rotationAngle = 180;
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            rotationAngle = 270;
        }

        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    // Método para crear un archivo a partir de un Bitmap
    private File createFileFromBitmap(Bitmap bitmap) {
        File file = new File(getCacheDir(), "rotated_image.jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    private void uploadImage() {
        Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/");
        startActivityForResult(Intent.createChooser(i,"Select the application"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri uri=data.getData();
            final String realPath=getRealPathFromURI(uri);
            this.f=new File(realPath);
            this.imageUser.setImageURI(uri);
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

    private boolean validate() {
        boolean retorno = true;
        String firstName, lastName, age, telephone, email, password,height,weight;
        firstName = edtNameUser.getText().toString();
        lastName = edtLastName.getText().toString();
        age = edtAge.getText().toString();
        height = edtHeight.getText().toString();
        telephone = edtTelephone.getText().toString();
        weight=edtWeight.getText().toString();
        email = edtEmailUser.getText().toString();
        password = edtPasswordUser.getText().toString();
        if (this.f == null) {
            errorMessage("you must select a profile picture");
            retorno = false;
        }
        if (firstName.isEmpty()) {
            txtInputNameUser.setError("enter your name");
            retorno = false;
        } else {
            txtInputNameUser.setErrorEnabled(false);
        }
        if (lastName.isEmpty()) {
            txtInputLastName.setError("enter your lastname");
            retorno = false;
        } else {
            txtInputLastName.setErrorEnabled(false);
        }
        if (age.isEmpty()) {
            txtInputAge.setError("enter your age");
            retorno = false;
        } else {
            txtInputAge.setErrorEnabled(false);
        }
        if (telephone.isEmpty()) {
            txtInputTelephone.setError("enter your telephone");
            retorno = false;
        } else {
            txtInputTelephone.setErrorEnabled(false);
        }
        if (height.isEmpty()) {
            txtInputTypeHeight.setError("enter your height");
            retorno = false;
        } else {
            txtInputTypeHeight.setErrorEnabled(false);
        }
        if (weight.isEmpty()) {
            txtInputTypeWeight.setError("enter your weight");
            retorno = false;
        } else {
            txtInputTypeWeight.setErrorEnabled(false);
        }
        if (email.isEmpty()) {
            txtInputEmailUser.setError("enter your email");
            retorno = false;
        } else {
            txtInputEmailUser.setErrorEnabled(false);
        }
        if (password.isEmpty()) {
            txtInputPasswordUser.setError("Enter password for login");
            retorno = false;
        } else {
            txtInputPasswordUser.setErrorEnabled(false);
        }

        return retorno;
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