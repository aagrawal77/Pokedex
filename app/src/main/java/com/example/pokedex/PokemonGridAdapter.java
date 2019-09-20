package com.example.pokedex;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.ArrayList;

public class PokemonGridAdapter extends RecyclerView.Adapter<PokemonGridAdapter.CustomViewHolder> {

    Context context;
    ArrayList<Pokemon> pokemon;

    public PokemonGridAdapter(Context context, ArrayList pokemon) {
        this.context = context;
        this.pokemon = pokemon;
    }

    @NonNull
    @Override
    public PokemonGridAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pokemon_grid, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonGridAdapter.CustomViewHolder holder, int position) {
        Pokemon pokemon = this.pokemon.get(position);
        holder.bind(pokemon);
    }

    @Override
    public int getItemCount() {
        return pokemon.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPokemon;
        private TextView tvName;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ivPokemon = itemView.findViewById(R.id.ivPokemon);
            tvName = itemView.findViewById(R.id.tvName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Pokemon poke = pokemon.get(position);
                        Intent intent = new Intent(context, ProfileActivity.class);
                        intent.putExtra("pokemon", Parcels.wrap(poke));
                        context.startActivity(intent);
                    }
                }
            });
        }

        public void bind(Pokemon pokemon) {
            tvName.setText(pokemon.getName());
            String url = "http://img.pokemondb.net/artwork/" + pokemon.getName().toLowerCase();
            url = url.concat(".jpg");
            if (Patterns.WEB_URL.matcher(url).matches()) {
                Glide.with(context)
                        .load(url)
                        .into(ivPokemon);
            } else {
                Glide.with(context)
                        .load(R.drawable.question)
                        .into(ivPokemon);
            }
            Log.d("XYZ", url);
        }
    }
}
