package com.example.cloudcounselage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.airbnb.lottie.LottieAnimationView;


public class MainActivity extends AppCompatActivity {

    Button Userbutton,brochure,official_website;
    ImageButton facebook,linkdin,twitter,instagram,youtube;
    ImageView imageView;
    LinearLayout product_layout;
    LottieAnimationView chatbot;

    @SuppressLint({"MissingInflatedId", "SetTextI18n", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Userbutton = findViewById(R.id.Userbutton);
        imageView = findViewById(R.id.imageView3);
        product_layout = findViewById(R.id.product_layout);
        chatbot= findViewById(R.id.chatbot);
        brochure = findViewById(R.id.brochure);
        official_website = findViewById(R.id.official_website);
        facebook = findViewById(R.id.facebook);
        linkdin = findViewById(R.id.linkdin);
        twitter = findViewById(R.id.twitter);
        instagram = findViewById(R.id.instagram);
        youtube = findViewById(R.id.youtube);


        Intent intent = getIntent();
        String nameFromDB = intent.getStringExtra("name");
        String user_NameFromDB = intent.getStringExtra("user_Name");
        String emailFromDB = intent.getStringExtra("email");
        String passwordFromDB = intent.getStringExtra("password");
        Userbutton.setText("Hi, "+nameFromDB);

        Userbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent UserProfile = new Intent(getApplicationContext(), userProfile.class);
                UserProfile.putExtra("name",nameFromDB);
                UserProfile.putExtra("user_Name",user_NameFromDB);
                UserProfile.putExtra("email",emailFromDB);
                UserProfile.putExtra("password",passwordFromDB);
                startActivity(UserProfile);
            }
        });



        brochure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://cloudcounselage0-my.sharepoint.com/:b:/g/personal/welcome_cloudcounselage_com/EUG2zVL-QsxBjF7os0oCZ-8BQfBXFVMSMiniVNtI-LzOJA?e=Ya8rhR";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.facebook.com/CloudCounselage/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        linkdin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.linkedin.com/company/cloud-counselage/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://twitter.com/CloudCounselage";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.instagram.com/cloudcounselage/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.youtube.com/channel/UCAZcXy22Fhj3ARp10Bem-BA";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        official_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.cloudcounselage.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

    }
}