package com.example.gspl.firebase_authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edittextemail,edittextpassword;
    TextView textView;
    ProgressBar progressBar;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edittextemail=findViewById(R.id.medittextemail);
        edittextpassword=findViewById(R.id.meditpassword);
        progressBar=findViewById(R.id.progressbar);
        textView=findViewById(R.id.textvwlogin);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.mbuttonsignup).setOnClickListener(this);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }


    private void registeruser(){
        String email= edittextemail.getText().toString().trim();
        String password=edittextpassword.getText().toString().trim();

        if(email.isEmpty()){
            edittextemail.setError("Email is required");
            edittextemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edittextemail.setError("Please enter a valid email");
            edittextemail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            edittextpassword.setError("password is required");
            edittextpassword.requestFocus();
            return;
        }

        if(password.length()<6){
            edittextpassword.setError("password length should be minimum of 6");
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(SignUpActivity.this,ProfileActivity.class));

                    Toast.makeText(SignUpActivity.this, "you registered successfully", Toast.LENGTH_SHORT).show();
                }
                   else {
                   if(task.getException() instanceof FirebaseAuthUserCollisionException){
                       Toast.makeText(SignUpActivity.this, "you are already registed", Toast.LENGTH_SHORT).show();
                   }
                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mbuttonsignup:
                registeruser();
                break;

           /* case R.id.textvwlogin:
                finish();
                startActivity(new Intent(this,MainActivity.class));
                break;*/
        }

    }
}
