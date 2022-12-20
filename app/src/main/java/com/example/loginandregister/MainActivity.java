package com.example.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView createNewAccount,forgetpassword;

    EditText inputemail, inputpassword;
    Button btnlogin;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressdialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNewAccount = findViewById(R.id.createnewaccount);
        forgetpassword=findViewById(R.id.Forgotpassword);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inputemail = findViewById(R.id.fmail);
        inputpassword = findViewById(R.id.inputPassword2);
        progressdialog = new ProgressDialog(this);
        btnlogin = findViewById(R.id.fbtnconfirm);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performlogin();
            }
        });
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ForgetActivity.class));
                finish();
            }
        });

    }

    private void performlogin() {
        String email = inputemail.getText().toString();
        String password = inputpassword.getText().toString();
        if (!email.matches(emailpattern)) {
            inputemail.setError("Please input correct email");
        } else if (password.isEmpty() || password.length() < 6) {
            inputpassword.setError("Enter proper password(Password length should be greater than 6)");
        } else {
            progressdialog.setMessage("Please wait for Login");
            progressdialog.setTitle("Login");
            progressdialog.setCanceledOnTouchOutside(false);
            progressdialog.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressdialog.dismiss();
                        sendusertonextactivity();
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        progressdialog.dismiss();
                        Toast.makeText(MainActivity.this, "Login failed"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendusertonextactivity() {
        Intent intent=new Intent(MainActivity.this,Homeactivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}