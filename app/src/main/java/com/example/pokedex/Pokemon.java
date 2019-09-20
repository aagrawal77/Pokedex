package com.example.pokedex;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcel;

import java.io.Serializable;
import java.util.ArrayList;

@Parcel
public class Pokemon implements Serializable {
    String name;
    String id;
    String attack;
    String defense;
    String hp;
    String spattack;
    String spdef;
    String species;
    String speed;
    String total;
    ArrayList<String>  type;

    // empty constructor needed for Parceler library
    public Pokemon() {

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAttack() {
        return attack;
    }

    public String getDefense() {
        return defense;
    }

    public String getHp() {
        return hp;
    }

    public String getSpattack() {
        return spattack;
    }

    public String getSpdef() {
        return spdef;
    }

    public String getSpecies() {
        return species;
    }

    public String getSpeed() {
        return speed;
    }

    public String getTotal() {
        return total;
    }

    public ArrayList getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public void setSpattack(String spattack) {
        this.spattack = spattack;
    }

    public void setSpdef(String spdef) {
        this.spdef = spdef;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setType(JSONArray type) throws JSONException {
        this.type = new ArrayList<>();
        for (int i = 0; i < type.length(); i++) {
            this.type.add(type.getString(i));
        }
    }
}
