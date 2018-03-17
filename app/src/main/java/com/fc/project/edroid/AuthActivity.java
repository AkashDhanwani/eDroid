package com.fc.project.edroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.bloder.magic.view.MagicButton;

public class AuthActivity extends AppCompatActivity {


    ActionProcessButton btnFirebaseSignUp,btnFirebaseLogin;
    EditText et_email,et_pass;
    private FirebaseAuth mAuth;
    FirebaseUser currentuser;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btnFirebaseSignUp = findViewById(R.id.btnFirebaseSignUp);
        btnFirebaseLogin=findViewById(R.id.btnFirebaseLogin);
        et_email=findViewById(R.id.ed_email);
        et_pass=findViewById(R.id.ed_password);
        mAuth = FirebaseAuth.getInstance();
        btnFirebaseSignUp.setMode(ActionProcessButton.Mode.ENDLESS);
//start with progress = 0
        btnFirebaseSignUp.setProgress(0);
        currentuser=FirebaseAuth.getInstance().getCurrentUser();
        if(currentuser!=null)
        {
            Intent userintent=new Intent(getApplicationContext(),MainActivity.class);
            userintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(userintent);
        }
        else{
           // Toast.makeText(this, "User is signed out!", Toast.LENGTH_SHORT).show();
        }
    }

    public void firebaseSignUp(View view) {

        email =et_email.getText().toString();
        password=et_pass.getText().toString();

        if(password.length()<8)
        {
            et_pass.setError("Please enter password of minimum length 8");
            et_pass.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            et_email.setError("Enter Valid Email id");
            et_email.requestFocus();
            return;
        }
        else
        {
            ActionProcessButton btn = (ActionProcessButton) view;
            // we add 25 in the button progress each click
            if(btn.getProgress() < 100){
                btn.setProgress(btn.getProgress() + 15);
                et_email.setEnabled(false);
                et_pass.setEnabled(false);
            }
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(currentuser!=null)
//                            {
//                                Toast.makeText(LoginActivity.this, "Account created successfully!  with email Id"+email, Toast.LENGTH_SHORT).show();
//                            }
                    if(task.isSuccessful())
                    {
                        currentuser=mAuth.getCurrentUser();
                        String userEmail=currentuser.getEmail();
                        Toast.makeText(getApplicationContext(), "Account created successfully!  with email Id"+ userEmail, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error While Creating Account with email id "+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void login(View view) {
        email = et_email.getText().toString();
        password = et_pass.getText().toString();
        if (password.length() < 8) {
            et_pass.setError("Enter correct password");
            et_pass.requestFocus();
            return;
        } else if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Enter Valid Email Address!");
            et_email.requestFocus();
            return;
        } else {

            ActionProcessButton btn = (ActionProcessButton) view;
            // we add 25 in the button progress each click
            if(btn.getProgress() < 100){
                btn.setProgress(btn.getProgress() + 15);
                et_email.setEnabled(false);
                et_pass.setEnabled(true);
            }
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        currentuser=mAuth.getCurrentUser();
                        String userEmail=currentuser.getEmail();
                        Toast.makeText(getApplicationContext(), "Login successful with email id "+userEmail, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please Enter Valid credentials "+task.getException(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
            }
        }

    public void forgot(View view) {

        Intent intent=new Intent(getApplicationContext(),ForgotPasswordActivity.class);
        startActivity(intent);
        //finish();
    }
}
