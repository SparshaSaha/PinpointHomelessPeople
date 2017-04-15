package com.fourthstatelab.pinpointhomelesspeople;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

import static com.fourthstatelab.pinpointhomelesspeople.Utility.fredoka;
import static com.fourthstatelab.pinpointhomelesspeople.Utility.nunito_bold;
import static com.fourthstatelab.pinpointhomelesspeople.Utility.nunito_reg;


public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText emailid_login,password_login;
    TextView forgot_password,sign_up,title,appName;
    private FirebaseAuth fire_auth;
    ImageButton toggleButton;
    boolean isPasswordShown =false;

    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fire_auth=FirebaseAuth.getInstance();
        Utility.setStatusBar(getWindow(),getApplicationContext());

        login=(Button)findViewById(R.id.login);
        emailid_login=(EditText)findViewById(R.id.email_login);
        password_login=(EditText)findViewById(R.id.password_login);
        forgot_password=(TextView)findViewById(R.id.forgot_password);
        sign_up=(TextView)findViewById(R.id.sign_up);
        sign_up.setTypeface(nunito_reg);
        forgot_password.setTypeface(nunito_reg);
        emailid_login.setTypeface(nunito_reg);
        password_login.setTypeface(nunito_reg);
        progress=new ProgressDialog(this);


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,Signup.class));
                overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailid_login.getText().toString().length()>0 && password_login.getText().toString().length()>0) {
                    progress.setMessage("Signing you in..");
                    progress.setIndeterminate(true);
                    progress.show();
                    sign_user_in();
                }
                else
                    Toast.makeText(LoginActivity.this,"Email-ID and password must not be empty", Toast.LENGTH_SHORT).show();
            }
        });
        login.setTypeface(nunito_bold);

        toggleButton = (ImageButton)findViewById(R.id.toggle_password);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toogleShowPassword();
            }
        });

        title=(TextView)findViewById(R.id.login_title);
        title.setTypeface(fredoka);
        appName= (TextView)findViewById(R.id.login_appName);
        appName.setTypeface(fredoka);

    }

    void toogleShowPassword(){
        if(isPasswordShown){
            //DONT SHOW PASSWORD
            password_login.setTransformationMethod(new PasswordTransformationMethod());
            Glide.with(getApplicationContext()).load(R.drawable.ic_show_password).into(toggleButton);
            isPasswordShown=false;
            password_login.setSelection(password_login.getText().length());
        }
        else{
            //SHOW PASSWORD
            password_login.setTransformationMethod(null);
            Glide.with(getApplicationContext()).load(R.drawable.ic_unshow_password).into(toggleButton);
            isPasswordShown=true;
            password_login.setSelection(password_login.getText().length());
        }
    } //SHOW AND INSHOW PASSWORD CONTROLLER

    public void sign_user_in()
    {
        fire_auth.signInWithEmailAndPassword(emailid_login.getText().toString(),password_login.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Login_credentials login=new Login_credentials();
                    login.email_id=emailid_login.getText().toString();
                    login.password=password_login.getText().toString();
                    Shared_Preferences_class.put_login_details(getApplicationContext(),login);
                    Shared_Preferences_class.set_keep_logged_in(true,getApplicationContext());
                    startActivity(new Intent(LoginActivity.this,NavigationCentre.class));
                    overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
                    finish();
                }
                else
                {
                    try
                    {
                        throw task.getException();
                    }
                    catch(FirebaseNetworkException e)
                    {
                        Toast.makeText(LoginActivity.this,"Unable to connect.Please check your Internet connection", Toast.LENGTH_LONG).show();
                    }
                    catch(FirebaseAuthInvalidCredentialsException e)
                    {
                        Toast.makeText(LoginActivity.this,"Invalid credentials", Toast.LENGTH_SHORT).show();
                        password_login.setText("");
                    }
                    catch(Exception e)
                    {
                        Log.e("tag",e.getMessage());
                    }
                }
                progress.cancel();
            }
        });
    }
}
