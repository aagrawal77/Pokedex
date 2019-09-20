package com.example.pokedex

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class CategorySearchActivity : AppCompatActivity() {

    lateinit var type1: String
    lateinit var type2: String
    internal var query: CharSequence? = null
    lateinit var filtered: ArrayList<Pokemon>
    lateinit var pokemon: ArrayList<Pokemon>
    internal var atk: Int = 0
    internal var def: Int = 0
    internal var health: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_search)


        query = intent.getCharSequenceExtra("query")
        Log.d("XYZ", query!!.toString())
        val bundle = intent.extras
        pokemon = bundle!!.getSerializable("list") as ArrayList<Pokemon>
        filtered = ArrayList()

        val types = arrayOf("", "Water", "Fire", "Grass", "Fairy", "Ice", "Psychic", "Dark", "Bug", "Steel", "Ghost", "Rock", "Flying", "Dragon", "Normal", "Ground")

        val spType1 = findViewById<Spinner>(R.id.spType1)
        val spType2 = findViewById<Spinner>(R.id.spType2)
        val etAtk = findViewById<EditText>(R.id.editText4)
        val etDef = findViewById<EditText>(R.id.editText5)
        val etHealth = findViewById<EditText>(R.id.editText6)
        val goBtn = findViewById<Button>(R.id.btnGo)
        val typeAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, types)
        spType1.adapter = typeAdapter
        spType2.adapter = typeAdapter

        spType1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                type1 = adapterView.getItemAtPosition(i) as String
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }

        spType2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                type2 = adapterView.getItemAtPosition(i) as String
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }

        if (etAtk.text.toString() != null && etAtk.text.toString() != "") {
            atk = Integer.parseInt(etAtk.text.toString())
        } else {
            atk = 0
        }

        if (etDef.text.toString() != null && etDef.text.toString() != "") {
            def = Integer.parseInt(etDef.text.toString())
        } else {
            def = 0
        }

        if (etHealth.text.toString() != null && etHealth.text.toString() != "") {
            health = Integer.parseInt(etHealth.text.toString())
        } else {
            health = 0
        }

        goBtn.setOnClickListener {
            filter()
            val intent = Intent(this@CategorySearchActivity, MainActivity::class.java)
            intent.putExtra("key", "category")
            val bundle = Bundle()
            bundle.putSerializable("list", filtered)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    fun filter() {
        if (query == null || query!!.length == 0) {
            filtered.addAll(pokemon)
        } else {
            val filterString = query!!.toString().toLowerCase().trim { it <= ' ' }
            for (poke in pokemon) {
                if (poke.getName().toLowerCase().contains(filterString)) {
                    filtered.add(poke)
                }
            }
        }

        val oldFiltered = ArrayList(filtered)

        for (poke in oldFiltered) {
            if (type1 != "" && !poke.getType().contains(type1)) {
                filtered.remove(poke)
                continue
            }
            if (type2 != "" && !poke.getType().contains(type2)) {
                filtered.remove(poke)
                continue
            }
            if (atk != 0 && Integer.parseInt(poke.getAttack()) < atk) {
                filtered.remove(poke)
                continue
            }
            if (def != 0 && Integer.parseInt(poke.getDefense()) < def) {
                filtered.remove(poke)
                continue
            }
            if (health != 0 && Integer.parseInt(poke.getHp()) < health) {
                filtered.remove(poke)
                continue
            }
        }
    }
}
