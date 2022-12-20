package com.example.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {
private  EditText forEmail;
private Button forget;
private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        forEmail=findViewById(R.id.fmail);
        forget=findViewById(R.id.fbtnconfirm);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=forEmail.getText().toString();

                if(email.isEmpty())
                {
                    Toast.makeText(ForgetActivity.this, "Please provide Email!", Toast.LENGTH_SHORT).show();
                }else{
                    forgetpassword();
                }
            }
        });
    }

    private void forgetpassword() {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ForgetActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgetActivity.this,MainActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(ForgetActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}