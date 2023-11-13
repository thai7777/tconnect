package vn.tconnect.appbanhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import vn.tconnect.appbanhang.models.UserModels;


public class SignUp extends AppCompatActivity {
    EditText editTextFullname,editTextPassword,editTextEmail;
    Button buttonSignUp;
    TextView textViewLogin;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth= FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        editTextFullname = findViewById(R.id.fullname);
        editTextPassword =findViewById(R.id.password);
        editTextEmail = findViewById(R.id.email);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.Login);
        progressBar= findViewById(R.id.SU_progessbar);
        progressBar.setVisibility(View.GONE);


        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void createUser(){
        String userName = editTextFullname.getText().toString();
        String userEmail = editTextEmail.getText().toString();
        String userPassword = editTextPassword.getText().toString();

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this,"Tên không được bỏ trống !!!" ,Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"Mật khẩu không được bỏ trống !!!" ,Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Email không được bỏ trống !!!" ,Toast.LENGTH_SHORT).show();
            return;
        }

        if(userPassword.length()<8){
            Toast.makeText(this,"Mật khẩu không được ít hơn 8 ký tự !!!" ,Toast.LENGTH_SHORT).show();
            return;
        }

        //
        auth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    UserModels userModels= new UserModels(userName,userEmail,userPassword);
                    String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(userModels);
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(SignUp.this,"Đăng ký tài khoản thành công !!!" ,Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUp.this,"Lỗi "+ task.getException() ,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}