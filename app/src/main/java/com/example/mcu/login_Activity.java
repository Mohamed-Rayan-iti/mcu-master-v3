package com.example.mcu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mcu.LocationOwner.retailer_dashboard_Activity;
import com.google.firebase.auth.FirebaseAuth;

public class login_Activity extends AppCompatActivity {

    Button callsignup, login_btn, forgetpasswod;
    ImageView loginlogo;
    TextView welcomeback, sign_in_to_continue;
    EditText email_login, passwordlogin;

    private CheckBox rememberme;
    private ProgressBar progressBar;


    //firebsae
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );


        // for full screen
        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView ( R.layout.activity_login );


        //HOOKS
        callsignup = findViewById ( R.id.sign_up_log );
        login_btn = findViewById ( R.id.login_btn );
        forgetpasswod = findViewById ( R.id.forget_password );
        loginlogo = findViewById ( R.id.login_logo );
        welcomeback = findViewById ( R.id.welcome_back );
        sign_in_to_continue = findViewById ( R.id.sign_in_to_continue );
        email_login = findViewById ( R.id.email_log );
        passwordlogin = findViewById ( R.id.password_log );
        rememberme = findViewById ( R.id.remember_melogin );
        progressBar = findViewById ( R.id.progressbar_login );


        //firebase
        firebaseAuth = FirebaseAuth.getInstance ( );


        // link from login to sign up
        callsignup.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Intent intent = new Intent ( login_Activity.this, sign_up_Activity.class );

                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair < View, String > ( loginlogo, "logo_image" );
                pairs[1] = new Pair < View, String > ( welcomeback, "logo_text" );
                pairs[2] = new Pair < View, String > ( sign_in_to_continue, "logo_desc" );
                pairs[3] = new Pair < View, String > ( email_login, "username_tran" );
                pairs[4] = new Pair < View, String > ( passwordlogin, "password_tran" );
                pairs[5] = new Pair < View, String > ( forgetpasswod, "forget_tran" );
                pairs[6] = new Pair < View, String > ( login_btn, "button_tran" );

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation ( login_Activity.this, pairs );
                login_Activity.this.startActivity ( intent, options.toBundle ( ) );

            }
        } );
        {


            // link from login to forget password

            forgetpasswod.setOnClickListener ( v -> {
                Intent intent = new Intent ( login_Activity.this, forgetpassword_Activity.class );
                login_Activity.this.startActivity ( intent );
            } );
        }


        //onclick to login
        findViewById ( R.id.login_btn ).setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                login_Activity.this.validationData ( );
            }
        } );

    }

    private void validationData () {
        String emaillogin = email_login.getText ( ).toString ( ).trim ( );
        String passlogin = passwordlogin.getText ( ).toString ( ).trim ( );

        //trust data
        // user name
        if (emaillogin.isEmpty ( )) {
            email_login.requestFocus ( );
            // first option
            // Toast.makeText(this, "User name is required", Toast.LENGTH_SHORT).show();


            // change to Alert
            showAlert ( "User name is required" );
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emaillogin).matches()) {
            email_login.requestFocus();

            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }
        // password
        if (passlogin.isEmpty ( )) {
            passwordlogin.requestFocus ( );
            // first option
            //Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();


            // change to Alert
            showAlert ( "Password is required" );
            return;

        }

        if (passlogin.length ( ) < 8) {
            passwordlogin.requestFocus ( );
            //Toast.makeText(this, "Password must be 8 digits", Toast.LENGTH_SHORT).show();


            // change to Alert
            showAlert ( "Password must be 8 digits" );
            return;


        }


        signInWithfirebase ( emaillogin, passlogin );
    }

    private void signInWithfirebase ( String emaillogin, String passlogin ) {

        progressBar.setVisibility ( View.VISIBLE );

        firebaseAuth.signInWithEmailAndPassword ( emaillogin, passlogin )
                .addOnCompleteListener ( task -> {

                    if (task.isSuccessful ( )) {
                        progressBar.setVisibility ( View.GONE );

                        if (rememberme.isChecked ( )) {
                            getSharedPreferences ( "Login", MODE_PRIVATE )
                                    .edit ( )
                                    .putBoolean ( "isLogin", true )
                                    .apply ( );
                        }

                        goToMain();

                    } else {
                        progressBar.setVisibility ( View.GONE );
                        showAlert ( "Error \n " + task.getException ( ).getMessage ( ) );

                    }
                } );
    }


    void showAlert ( String msg ) {
        new AlertDialog.Builder ( this )
                .setTitle ( "attention!" )
                .setMessage ( msg )
                .setIcon ( R.drawable.ic_attention )
                .setPositiveButton ( "Okay!", null )
                .create ( ).show ( );
    }


    protected void onStart () {
        super.onStart ( );

        boolean isLogin = getSharedPreferences ( "Login", MODE_PRIVATE ).getBoolean ( "isLogin", false );
        if (isLogin) {
            goToMain();
        }


    }


    void goToMain(){
        startActivity ( new Intent ( login_Activity.this, retailer_dashboard_Activity.class ) );
        finish ();

    }
}

