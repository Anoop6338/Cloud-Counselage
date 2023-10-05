
package com.example.cloudcounselage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import android.util.Pair;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button LogIn, SignUp;
    ImageView imageView;
    TextView textView;
    LinearLayout linear_layout;
    TextInputEditText User_Name, password;

    private FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        SignUp = findViewById(R.id.SignUp);
        LogIn = findViewById(R.id.LogIn);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        linear_layout = findViewById(R.id.linear_layout);
        User_Name = findViewById(R.id.User_Name);
        password= findViewById(R.id.password);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(getApplicationContext(), SignUpActivity.class);

                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair(imageView,"logo_image");
                pairs[1] = new Pair(textView,"title");
                pairs[2] = new Pair(SignUp,"SignUp");
                pairs[3] = new Pair(linear_layout,"layout");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                startActivity(register, options.toBundle());
            }
        });

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !validateUserName()| !validatePassword()){
                    //this will return if the fields are empty
                }
                else{
                    isUser();
                }
            }
        });

    }

    private void isUser() {
        String UserEnteredUsername = User_Name.getText().toString().trim();
        String UserEnteredPassword = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUsername = reference.orderByChild("user_Name").equalTo(UserEnteredUsername);
        checkUsername.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User_Name.setError(null);
                    String passwordFromDB = snapshot.child(UserEnteredUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(UserEnteredPassword)) {
                        String nameFromDB = snapshot.child(UserEnteredUsername).child("name").getValue(String.class);
                        String user_NameFromDB = snapshot.child(UserEnteredUsername).child("user_Name").getValue(String.class);
                        String emailFromDB = snapshot.child(UserEnteredUsername).child("email").getValue(String.class);

                        Intent Homepage = new Intent(getApplicationContext(), MainActivity.class);

                        Homepage.putExtra("name", nameFromDB);
                        Homepage.putExtra("user_Name", user_NameFromDB);
                        Homepage.putExtra("email", emailFromDB);
                        Homepage.putExtra("password", passwordFromDB);

                        Pair[] pairs = new Pair[2];
                        pairs[0] = new Pair(imageView, "logo_image");
                        pairs[1] = new Pair(linear_layout, "layout");
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                        startActivity(Homepage, options.toBundle());
                    }else {
                        password.setError("Wrong password");
                        password.requestFocus();
                    }
                } else {
                    User_Name.setError("no such user exist");
                    User_Name.requestFocus();
                }
            }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

}

    private Boolean validateUserName(){
        String val_User_Name = User_Name.getText().toString();
        if (val_User_Name.isEmpty()){
            User_Name.setError("Field can't be empty");
            return false;
        } else {
            User_Name.setError(null);
            return true;
        }
    }
    private Boolean validatePassword(){
        String val_password = password.getText().toString();
        if (val_password.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
}