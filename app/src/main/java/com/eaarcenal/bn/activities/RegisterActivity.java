package com.eaarcenal.bn.activities;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eaarcenal.bn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText username;
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
                RegisterUser u = new RegisterUser();
                u.execute(username.getText().toString(),password.getText().toString(),email.getText().toString());
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity(0);
            }
        });

    }

    private void instantiateViews() {

        username = (EditText)findViewById(R.id.register_username);
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
            String username = strings[0];
            String password = strings[1];
            String email = strings[2];

            getmAuth().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("AUTH REGISTER","DONE");
                }
            });

            return null;
        }
    }
}
