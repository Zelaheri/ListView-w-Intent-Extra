package com.example.desafioapp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.desafioapp.databinding.ActivityMain2Binding
import com.google.android.material.snackbar.Snackbar
import kotlin.collections.ArrayList

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val listView: ListView = findViewById(R.id.listProducts)
        val listaProduto = i.getSerializableExtra("lista") as ArrayList<Produto?>


        val listAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaProduto
        )
        listView.adapter = listAdapter
        listAdapter.notifyDataSetChanged()

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as Produto
            binding.nameL.visibility = View.VISIBLE
            binding.descriptionL.visibility = View.VISIBLE
            binding.valorL.visibility = View.VISIBLE
            binding.nameInput.setText(selectedItem.nome)
            binding.descriptionInput.setText(selectedItem.desc)
            binding.valorInput.setText(selectedItem.value.toString())
            Snackbar.make(
                findViewById(R.id.main),
                "${position+1}º item selecionado.",
                Snackbar.LENGTH_SHORT
            ).show()
        }

    }
}