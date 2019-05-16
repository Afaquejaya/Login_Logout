package com.example.collegenoticeboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;




public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmainl;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_main);

        //initilizeing firebase auth object
        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null) {
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        buttonRegister=(Button) findViewById(R.id.buttonRegister);

        //initilizing View
        editTextEmainl=(EditText) findViewById(R.id.editTextEmail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        textViewSignin=(TextView) findViewById(R.id.textViewSignin);

        //attaching listener to button
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email & password from edit text
        String email = editTextEmainl.getText().toString().trim();
        String password =editTextPassword.getText().toString().trim();

        //checking email is empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            //Stoppping the function Execution further
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //checking Password is Empty
            Toast.makeText(this,"please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        //if validation are ok
        //Display a progressbar
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                                //profile activity here
                                finish();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                            //user is succesfully registered and logged in
                            //we will start the profile activity here
                            //right now lets display a toast only
                            Toast.makeText(MainActivity.this,"Registered Successfullt",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this,"Could not Registered ,Please Try again",Toast.LENGTH_SHORT).show();

                        }
                        progressDialog.dismiss();

                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view==buttonRegister){
            registerUser();
        }
        if(view==textViewSignin){
            //Will open login activity here
            startActivity(new Intent(this, loginActivity.class));
        }

    }
}
