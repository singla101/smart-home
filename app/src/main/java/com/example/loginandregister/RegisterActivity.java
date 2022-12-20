package com.example.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
TextView alreadyhaveaccount;
EditText inputemail,inputpassword,inputconfirmpassword;
Button btnregister;
String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
ProgressDialog progressdialog;
FirebaseAuth mAuth;
FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        alreadyhaveaccount=findViewById(R.id.alreadyhaveaccount);
        inputemail=findViewById(R.id.fmail);
        inputpassword=findViewById(R.id.inputPassword);
        inputconfirmpassword=findViewById(R.id.inputPassword2);
        btnregister=findViewById(R.id.btnRegister);
        progressdialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();
        alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuth();
            }
        });
    }

    private void PerformAuth() {
        String email=inputemail.getText().toString();
        String password=inputpassword.getText().toString();
        String confirmpassword=inputconfirmpassword.getText().toString();
        if(!email.matches(emailpattern))
        {
            inputemail.setError("Please input correct email");
        }
        else if(password.isEmpty()||password.length()<6)
        {
            inputpassword.setError("Enter proper password(Password length should be greater than 6)");
        }
        else if(!password.matches(confirmpassword))
        {
            inputconfirmpassword.setError("Password Not Matched");
        }
        else
        {
            progressdialog.setMessage("Please wait for Registration");
            progressdialog.setTitle("Registration");
            progressdialog.setCanceledOnTouchOutside(false);
            progressdialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressdialog.dismiss();
                        sendusertonextactivity();
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressdialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Registration failed"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void sendusertonextactivity() {
        Intent intent=new Intent(RegisterActivity.this,Homeactivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}