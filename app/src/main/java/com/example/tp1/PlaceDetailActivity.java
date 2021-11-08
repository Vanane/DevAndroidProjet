package com.example.tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlaceDetailActivity extends AppCompatActivity {

    @OnClick(R.id.placeAdresse) void OnAdresseClick(View view)
    {
        finish();
    }

    @OnClick(R.id.butGoogle) void OnGoogleClick(View view)
    {
        String browse = "https://www.google.com/search?q=" + ((TextView)findViewById(R.id.placeAdresse)).getText();
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(browse));
        startActivity((i));
    }

    @OnClick(R.id.butPartage) void OnPartageClick(View view)
    {
        String text = "https://www.google.com/search?q=" + ((TextView)findViewById(R.id.placeAdresse)).getText();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT, Uri.parse(text).toString());
        i.setType("text/plain");
        startActivity((i));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        ((TextView)findViewById(R.id.placeAdresse)).setText(getIntent().getStringExtra(("street")));
        ButterKnife.bind(this);
    }
}