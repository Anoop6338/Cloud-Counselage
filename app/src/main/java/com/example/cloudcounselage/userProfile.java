package com.example.cloudcounselage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userProfile extends AppCompatActivity {
    TextInputEditText Name, User_Name,email,password;
    Button LogOut, update;
    DatabaseReference reference;
    TextView textView3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Name = findViewById(R.id.Name);
        User_Name = findViewById(R.id.User_Name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        LogOut = findViewById(R.id.LogOut);
        textView3 = findViewById(R.id.textView3);
        update = findViewById(R.id.update);
        reference = FirebaseDatabase.getInstance().getReference("users");

        Intent intent = getIntent();
        String nameFromDB = intent.getStringExtra("name");
        String user_NameFromDB = intent.getStringExtra("user_Name");
        String emailFromDB = intent.getStringExtra("email");
        String passwordFromDB = intent.getStringExtra("password");
        Name.setText(nameFromDB);
        User_Name.setText(user_NameFromDB);
        email.setText(emailFromDB);
        password.setText(passwordFromDB);
        textView3.setText("Hi, "+nameFromDB);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent LoginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(LoginActivity);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNameChanged()|| isPasswordChanged()){
                    Toast.makeText(userProfile.this, "Data Updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(userProfile.this, "Same data thus not changed", Toast.LENGTH_SHORT).show();
                }
            }

            private boolean isPasswordChanged() {
                if(!passwordFromDB.equals(password.getText().toString())){
                    reference.child(user_NameFromDB).child("password").setValue(password.getText().toString());
                    return true;

                }else{
                    return false;
                }
            }

            private boolean isNameChanged() {
                if(!nameFromDB.equals(Name.getText().toString())){
                    reference.child(user_NameFromDB).child("name").setValue(Name.getText().toString());
                    textView3.setText("Hi, "+Name.getText().toString());
                    return true;
                }else{
                    return false;
                }
            }
        });

    }
}