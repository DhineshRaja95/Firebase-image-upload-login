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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edittextemail,edittextpassword;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittextemail=findViewById(R.id.medittextemail);
        edittextpassword=findViewById(R.id.meditpassword);
        progressBar=findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.buttonlogin).setOnClickListener(this);
        findViewById(R.id.textvwsignup).setOnClickListener(this);




 }

    private void userlogin(){

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

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                if(task.isSuccessful()){
                    finish();
                    Intent intents=new Intent(MainActivity.this,ProfileActivity.class);
                    intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intents);

                }else
                {
                    //Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "create account first or check your connection", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(this,ProfileActivity.class));
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.textvwsignup:
                finish();
                startActivity(new Intent(this,SignUpActivity.class));
                break;

            case R.id.buttonlogin:
                userlogin();
                break;

        }

    }
}
