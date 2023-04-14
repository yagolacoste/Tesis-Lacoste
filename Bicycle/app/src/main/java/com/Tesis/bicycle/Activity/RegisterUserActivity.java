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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Tesis.bicycle.Dto.ApiRest.auth.request.SignupRequest;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.StoredDocumentViewModel;
import com.Tesis.bicycle.ViewModel.AuthViewModel;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterUserActivity extends AppCompatActivity {
    private File f;
    private AuthViewModel authViewModel;
    private StoredDocumentViewModel storedDocumentViewModel;
    private Button btnUploadImage, btnStoredData;
    private CircleImageView imageUser;
    private AutoCompleteTextView dropdownTypeDoc, dropdownCity, dropdownState, dropdownCountry;
    private EditText edtNameUser, edtLastName, edtAge, edtNumDocU, edtTelephone,
            edtDirection, edtPasswordUser, edtEmailUser, edtBirthday;
    private TextInputLayout txtInputNameUser, txtInputLastName, txtInputAge,
            txtInputTypeDoc, txtInputNumberDoc, txtInputCity, txtInputState,
            txtInputCountry, txtInputTelephone, txtInputDirection, txtInputEmailUser, txtInputPasswordUser,txtInputBirthday;
    private final static int LOCATION_REQUEST_CODE = 23;
    private final static String USER_DEFAULT="user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        this.init();
        this.initViewModel();
        this.spinners();

    }

    private void spinners() {
        //LISTA DE TIPOS DE DOCUMENTOS
        String[] tipoDoc = getResources().getStringArray(R.array.typeDoc);
        ArrayAdapter arrayTipoDoc = new ArrayAdapter(this, R.layout.dropdown_item, tipoDoc);
        dropdownTypeDoc.setAdapter(arrayTipoDoc);
        //LISTA DE DEPARTAMENTOS
        String[] departamentos = getResources().getStringArray(R.array.Country);
        ArrayAdapter arrayDepartamentos = new ArrayAdapter(this, R.layout.dropdown_item, departamentos);
        dropdownCountry.setAdapter(arrayDepartamentos);
        //LISTA DE PROVINCIAS
        String[] provincias = getResources().getStringArray(R.array.State);
        ArrayAdapter arrayProvincias = new ArrayAdapter(this, R.layout.dropdown_item, provincias);
        dropdownState.setAdapter(arrayProvincias);
        //LISTA DE DISTRITOS
        String[] distritos = getResources().getStringArray(R.array.City);
        ArrayAdapter arrayDistritos = new ArrayAdapter(this, R.layout.dropdown_item, distritos);
        dropdownCity.setAdapter(arrayDistritos);

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
        edtAge = findViewById(R.id.editAge);
        edtNumDocU = findViewById(R.id.edtNumDocU);
        edtTelephone = findViewById(R.id.edtTelephone);
        edtDirection = findViewById(R.id.edtDirection);
        edtPasswordUser = findViewById(R.id.edtPasswordUser);
        edtEmailUser = findViewById(R.id.edtEmailUser);
        edtBirthday=findViewById(R.id.edtBirthday);
        //AutoCompleteTextView
        dropdownTypeDoc = findViewById(R.id.dropdownTypeDoc);
        dropdownCity = findViewById(R.id.dropdownCity);
        dropdownState = findViewById(R.id.dropdownState);
        dropdownCountry = findViewById(R.id.dropdownCountry);
        //TextInputLayout
        txtInputNameUser = findViewById(R.id.txtInputNameUser);
        txtInputLastName = findViewById(R.id.txtInputLastName);
        txtInputAge = findViewById(R.id.txtInputAge);
        txtInputTypeDoc = findViewById(R.id.txtInputTypeDoc);
        txtInputNumberDoc = findViewById(R.id.txtInputNumberDoc);
        txtInputCity = findViewById(R.id.txtInputCity);
        txtInputState = findViewById(R.id.txtInputState);
        txtInputCountry = findViewById(R.id.txtInputCountry);
        txtInputTelephone = findViewById(R.id.txtInputTelephone);
        txtInputDirection = findViewById(R.id.txtInputDirection);
        txtInputEmailUser = findViewById(R.id.txtInputEmailUser);
        txtInputPasswordUser = findViewById(R.id.txtInputPasswordUser);
        txtInputBirthday=findViewById(R.id.txtInputBirthday);
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
        edtNumDocU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputNumberDoc.setErrorEnabled(false);
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
        edtDirection.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputDirection.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtBirthday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputBirthday.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dropdownTypeDoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputTypeDoc.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dropdownCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputCity.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dropdownState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputState.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dropdownCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputCity.setErrorEnabled(false);
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
                user.setIdentityType(dropdownTypeDoc.getText().toString());
                user.setIdentity(edtNumDocU.getText().toString());
                user.setAddress(edtDirection.getText().toString());
                Set<String>userDefault=new HashSet<String>();
                userDefault.add(USER_DEFAULT);
                user.setRole(userDefault);
                user.setAge(Integer.parseInt(edtAge.getText().toString()));
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                user.setBirthday(formato.parse(edtBirthday.getText().toString()));
                user.setPhone(edtTelephone.getText().toString());
                RequestBody rb = RequestBody.create(f, MediaType.parse("multipart/form-data")),somedata;
                String filename = f.getName();
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", f.getName(), rb);
                somedata = RequestBody.create(filename, MediaType.parse("text/plain"));
                this.storedDocumentViewModel.save(part,somedata).observe(this,response->{
                    user.setPhoto(response);
                    user.setPassword(edtPasswordUser.getText().toString());
                    user.setEmail(edtEmailUser.getText().toString());
                    this.authViewModel.registerUser(user).observe(this, resp->{
                        successMessage("Estupendo! " + "Su información ha sido guardada con éxito en el sistema.");
                        this.finish();
                    });
                });
            }catch (Exception e){
                warningMessage("Se ha producido un error : " + e.getMessage());
            }
        }
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
        String nombres, apellidoPaterno, age,birthday, numDoc, telefono, direccion, correo, clave,
                dropTipoDoc, dropDepartamento, dropProvincia, dropDistrito;
        nombres = edtNameUser.getText().toString();
        apellidoPaterno = edtLastName.getText().toString();
        age = edtAge.getText().toString();
        numDoc = edtNumDocU.getText().toString();
        telefono = edtTelephone.getText().toString();
        direccion = edtDirection.getText().toString();
        birthday=edtBirthday.getText().toString();
        correo = edtEmailUser.getText().toString();
        clave = edtPasswordUser.getText().toString();
        dropTipoDoc = dropdownTypeDoc.getText().toString();
        dropDepartamento = dropdownCity.getText().toString();
        dropProvincia = dropdownState.getText().toString();
        dropDistrito = dropdownCountry.getText().toString();
        if (this.f == null) {
            errorMessage("debe selecionar una foto de perfil");
            retorno = false;
        }
        if (nombres.isEmpty()) {
            txtInputNameUser.setError("Ingresar nombres");
            retorno = false;
        } else {
            txtInputNameUser.setErrorEnabled(false);
        }
        if (apellidoPaterno.isEmpty()) {
            txtInputLastName.setError("Ingresar apellido paterno");
            retorno = false;
        } else {
            txtInputLastName.setErrorEnabled(false);
        }
        if (age.isEmpty()) {
            txtInputAge.setError("Ingresar age");
            retorno = false;
        } else {
            txtInputAge.setErrorEnabled(false);
        }
        if (numDoc.isEmpty()) {
            txtInputNumberDoc.setError("Ingresar número documento");
            retorno = false;
        } else {
            txtInputNumberDoc.setErrorEnabled(false);
        }
        if (telefono.isEmpty()) {
            txtInputTelephone.setError("Ingresar número telefónico");
            retorno = false;
        } else {
            txtInputTelephone.setErrorEnabled(false);
        }
        if (direccion.isEmpty()) {
            txtInputDirection.setError("Ingresar dirección de su casa");
            retorno = false;
        } else {
            txtInputDirection.setErrorEnabled(false);
        }
        if (birthday.isEmpty()) {
            txtInputBirthday.setError("Insert birthday");
            retorno = false;
        } else {
            txtInputBirthday.setErrorEnabled(false);
        }
        if (correo.isEmpty()) {
            txtInputEmailUser.setError("Ingresar correo electrónico");
            retorno = false;
        } else {
            txtInputEmailUser.setErrorEnabled(false);
        }
        if (clave.isEmpty()) {
            txtInputPasswordUser.setError("Ingresar clave para el inicio de sesión");
            retorno = false;
        } else {
            txtInputPasswordUser.setErrorEnabled(false);
        }
        if (dropTipoDoc.isEmpty()) {
            txtInputTypeDoc.setError("Seleccionar Tipo Doc");
            retorno = false;
        } else {
            txtInputTypeDoc.setErrorEnabled(false);
        }
        if (dropDepartamento.isEmpty()) {
            txtInputCity.setError("Seleccionar Departamento");
            retorno = false;
        } else {
            txtInputCity.setErrorEnabled(false);
        }
        if (dropProvincia.isEmpty()) {
            txtInputState.setError("Seleccionar Provincia");
            retorno = false;
        } else {
            txtInputState.setErrorEnabled(false);
        }
        if (dropDistrito.isEmpty()) {
            txtInputCity.setError("Seleccionar Distrito");
            retorno = false;
        } else {
            txtInputCity.setErrorEnabled(false);
        }
        return retorno;
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
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