package com.dam.recyclerviewjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView tvSA_Auteur;
    private TextView tvSA_NbLikes;
    private ImageView ivSA_Image;

    private void initUI() {
        tvSA_Auteur = findViewById(R.id.tvSA_Auteur);
        tvSA_NbLikes = findViewById(R.id.tvSA_NbLikes);
        ivSA_Image = findViewById(R.id.ivSA_Image);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}