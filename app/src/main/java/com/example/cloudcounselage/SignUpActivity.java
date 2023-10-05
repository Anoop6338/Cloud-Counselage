package com.example.cloudcounselage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {
    Button SignIn_button, Register_button;
    TextInputEditText Re_password, password, Name, email,User_Name;
    TextView textView;
    ImageView imageView;
    LinearLayout linear_layout;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_sign_up);

        SignIn_button = (Button)findViewById(R.id.SignIn_button);
        Register_button = (Button)findViewById(R.id.Register_button);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        linear_layout = findViewById(R.id.linear_layout);
        Re_password = findViewById(R.id.Re_password);
        password = findViewById(R.id.password);
        Name = findViewById(R.id.Name);
        email = findViewById(R.id.email);
        User_Name=findViewById(R.id.User_Name);


        SignIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(getApplicationContext(), LoginActivity.class);
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair(imageView,"logo_image");
                pairs[1] = new Pair(textView,"title");
                pairs[2] = new Pair(SignIn_button,"SignUp");
                pairs[3] = new Pair(linear_layout,"layout");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this,pairs);
                startActivity(signIn, options.toBundle());
            }
        });

        Register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateName() | !validateEmail() | !validateUserName()| !validatePassword() | !validateRePassword()){
                    return;
                }
                else {

                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("users");
                    String val_Name = Name.getText().toString();
                    String val_User_Name = User_Name.getText().toString();
                    String val_email = email.getText().toString();
                    String val_password = password.getText().toString();



                    mAuth.createUserWithEmailAndPassword(val_email, val_password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    sendVerificationEmail(user);
                                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(login);
                                }else {
                                    Toast.makeText(SignUpActivity.this, "user is null", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(SignUpActivity.this, "Registration failed "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    UserHelperClass helperClass = new UserHelperClass(val_Name, val_email, val_User_Name, val_password);
                    reference.child(val_User_Name).setValue(helperClass);
                }
            }
        });
    }

    private void sendVerificationEmail(FirebaseUser user) {
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Email sent successfully
                    Toast.makeText(SignUpActivity.this, "Verification email sent. Please check your inbox.", Toast.LENGTH_SHORT).show();
                } else {
                    // Email sending failed
                    Toast.makeText(SignUpActivity.this, "Failed to send verification email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean validateName(){
        String val_Name = Name.getText().toString();
        if (val_Name.isEmpty()){
            Name.setError("Field can't be empty");
            return false;
        }
        else {
            Name.setError(null);
            return true;
        }
    }

    private Boolean validateUserName(){
        String val_User_Name = User_Name.getText().toString();
        String whitespace = "\\w*";
        if (val_User_Name.isEmpty()){
            User_Name.setError("Field can't be empty");
            return false;
        } else if (! val_User_Name.matches(whitespace)) {
            User_Name.setError("Can't have white spaces");
            return false;
        } else if (val_User_Name.length()>=20) {
            User_Name.setError("User name should be under 20 characters");
            return false;
        } else {
            User_Name.setError(null);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val_email = email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val_email.matches(emailPattern) && val_email.length() > 0)
        {
            return true;
        }
        else
        {
            email.setError("invalid email");
            return false;
        }
    }

    private Boolean validatePassword(){
        String val_password = password.getText().toString();
        String password_val = "^" +
                "(?=.*[@#$%^&+=])" +     // at least 1 special character
                "(?=\\S+$)" +            // no white spaces
                ".{4,}" +                // at least 4 characters
                "$";
        if (val_password.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        } else if (!val_password.matches(password_val)) {
            password.setError("Weak password");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private Boolean validateRePassword(){
        String val_Re_password = Re_password.getText().toString();
        String val_password = password.getText().toString();

        if (val_Re_password.isEmpty()){
            Re_password.setError("Field can't be empty");
            return false;
        } else if (!val_Re_password.equals(val_password)) {
            Re_password.setError("Passwords do not match");
            return false;
        }
        else {
            Re_password.setError(null);
            return true;
        }
    }
}