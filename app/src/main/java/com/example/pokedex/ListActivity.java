package com.example.pokedex;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<Pokemon> allPokemon;
    CharSequence query;
    ArrayList<Pokemon> filtered;
    RecyclerView recyclerView;
    PokemonGridAdapter gridAdapter;
    PokemonAdapter adapter;
    ImageButton btnGrid;
    ImageButton btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        btnGrid = findViewById(R.id.btnGrid);
        btnList = findViewById(R.id.btnList);

        filtered = new ArrayList<>();
        if (getIntent().getStringExtra("key").equals("main")) {
            Log.d("XYZ", "about to filter");
            query = getIntent().getCharSequenceExtra("query");
            Bundle bundle = getIntent().getExtras();
            allPokemon = (ArrayList) bundle.getSerializable("list");
            filter();
        } else {
            Bundle bundle = getIntent().getExtras();
            filtered = (ArrayList) bundle.getSerializable("list");
        }

        setUpRV();
        btnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GridLayoutManager layoutManager = new GridLayoutManager(ListActivity.this, 2);
                recyclerView.setLayoutManager(layoutManager);
                gridAdapter = new PokemonGridAdapter(ListActivity.this, filtered);
                recyclerView.setAdapter(gridAdapter);
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpRV();
            }
        });

    }

    public void filter() {
        if (query == null || query.length() == 0) {
            filtered.addAll(allPokemon);
        } else {
            String filterString = query.toString().toLowerCase().trim();
            for (Pokemon poke: allPokemon) {
                if (poke.getName().toLowerCase().contains(filterString)) {
                    filtered.add(poke);
                }
            }
        }
    }

    private void setUpRV() {
        recyclerView = findViewById(R.id.rvResults);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PokemonAdapter(this, filtered);
        recyclerView.setAdapter(adapter);
    }
}
