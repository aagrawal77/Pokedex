package com.example.pokedex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ProfileActivity extends AppCompatActivity {

    Pokemon pokemon;
    ImageView ivImage;
    TextView tvName;
    TextView tvID;
    TextView tvSpecies;
    TextView tvHP;
    TextView tvAtk;
    TextView tvDef;
    TextView tvSpeed;
    TextView tvSpatck;
    TextView tvSpdef;
    TextView tvTotal;
    TextView tvType1;
    TextView tvType2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ivImage = findViewById(R.id.ivImage);
        tvName = findViewById(R.id.tvName);
        tvID = findViewById(R.id.tvID);
        tvSpecies = findViewById(R.id.textView2);
        tvHP = findViewById(R.id.tvHP);
        tvAtk = findViewById(R.id.tvAtk);
        tvDef = findViewById(R.id.tvDef);
        tvSpeed = findViewById(R.id.tvSpeed);
        tvSpatck = findViewById(R.id.tvSpatk);
        tvSpdef = findViewById(R.id.tvSpdef);
        tvTotal = findViewById(R.id.tvTotal);
        tvType1 = findViewById(R.id.tvType1);
        tvType2 = findViewById(R.id.tvType2);

        Button btnSearch = findViewById(R.id.btnSearch);

        pokemon = (Pokemon) Parcels.unwrap(getIntent().getParcelableExtra("pokemon"));

        tvName.setText(pokemon.getName());
        String url = "http://img.pokemondb.net/artwork/" + pokemon.getName().toLowerCase();
        url = url.concat(".jpg");
        if (Patterns.WEB_URL.matcher(url).matches()) {
            Glide.with(this)
                    .load(url)
                    .into(ivImage);
        } else {
            Glide.with(this)
                    .load(R.drawable.question)
                    .into(ivImage);
        }

        bindTextViews();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String escapedQuery = null;
                try {
                    escapedQuery = URLEncoder.encode(pokemon.getName(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.parse("http://www.google.com/#q=" + escapedQuery);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    private void bindTextViews() {
        tvID.setText(pokemon.getId());
        tvSpecies.setText(pokemon.getSpecies());
        tvHP.setText("HP: " + pokemon.getHp());
        tvAtk.setText("Attack: " + pokemon.getAttack());
        tvDef.setText("Defense: " + pokemon.getDefense());
        tvSpatck.setText("Sp. Attack: " + pokemon.getSpattack());
        tvSpdef.setText("Sp. Defense: " + pokemon.getSpdef());
        tvSpeed.setText("Speed: " + pokemon.getSpeed());
        tvTotal.setText("Total: " + pokemon.getTotal());
        tvType1.setText(pokemon.getType().get(0).toString());
        if (pokemon.getType().size() > 1) {
            tvType2.setText(pokemon.getType().get(1).toString());
        }

    }
}
