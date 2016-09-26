package com.eaarcenal.bn.activities;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eaarcenal.bn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText password;
    EditText confirmPassword;
    EditText email;
    Button registerButton;
    Button cancelButton;

    private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        mAuth = FirebaseAuth.getInstance();

        instantiateViews();
        setClick();
    }

    private void setClick() {

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!checkNull()){
                    if(password.getText().toString().equals(confirmPassword.getText().toString())){
                        RegisterUser u = new RegisterUser();
                        u.execute(password.getText().toString(),email.getText().toString());
                    }else{
                        confirmPassword.setError("Password does not match");
                    }
                }
            }


        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private boolean checkNull() {
        boolean hasError = false;
        if(email.getText().toString().equals("")){
            email.setError("Email is required");
            hasError = true;
        }
        if(password.getText().toString().equals("")){
            password.setError("Password is required");
            hasError = true;
        }
        if(confirmPassword.getText().toString().equals("")){
            confirmPassword.setError("Confirm Password is required");
            hasError = true;
        }
        return hasError;
    }

    private void instantiateViews() {

        password = (EditText)findViewById(R.id.register_password);
        confirmPassword = (EditText)findViewById(R.id.register_confirmpassword);
        email = (EditText)findViewById(R.id.register_email);
        registerButton = (Button)findViewById(R.id.register_register);
        cancelButton = (Button) findViewById(R.id.register_cancel);

    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    class RegisterUser extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            String password = strings[0];
            String email = strings[1];

            getmAuth().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("AUTH REGISTER","DONE");
                    if(task.isSuccessful()){
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Error on registering",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return null;
        }
    }
}
