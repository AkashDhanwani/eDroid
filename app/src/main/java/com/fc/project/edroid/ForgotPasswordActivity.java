package com.fc.project.edroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordActivity extends AppCompatActivity {

    FirebaseUser currentuser;
    FirebaseAuth mAuth;
    EditText email, pass;
    String etmail, etpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.forgot_ed_email);
        pass = findViewById(R.id.forgot_ed_password);
        mAuth = FirebaseAuth.getInstance();
    }

    public void forgotlogin(View view) {
        etmail = email.getText().toString();
        etpass = pass.getText().toString();
        if (etpass.length() < 8) {
            pass.setError("Enter correct password");
            pass.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etmail) || !Patterns.EMAIL_ADDRESS.matcher(etmail).matches()) {
            email.setError("Enter Valid Email Address!");
            email.requestFocus();
            return;
        } else {

            ActionProcessButton btn = (ActionProcessButton) view;
            // we add 25 in the button progress each click
            if(btn.getProgress() < 100){
                btn.setProgress(btn.getProgress() + 15);
                email.setEnabled(false);
                pass.setEnabled(false);

                currentuser=FirebaseAuth.getInstance().getCurrentUser();
                if(currentuser!=null)
                {
                    currentuser.updatePassword(pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Password Changed Successfully!", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                                Intent intent=new Intent(getApplicationContext(),AuthActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Password Cannot be CHanged", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }
    }
}