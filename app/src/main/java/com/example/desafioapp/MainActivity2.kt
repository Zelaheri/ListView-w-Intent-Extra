package com.example.desafioapp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.desafioapp.databinding.ActivityMain2Binding
import com.google.android.material.snackbar.Snackbar

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

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as Produto
            binding.nameL.visibility = View.VISIBLE
            binding.descriptionL.visibility = View.VISIBLE
            binding.valorL.visibility = View.VISIBLE

            binding.nameInput.setText(selectedItem.nome)
            binding.descriptionInput.setText(selectedItem.desc)
            binding.valorInput.setText(selectedItem.value.toString())

            binding.nameInput.requestFocus()

            Snackbar.make(
                findViewById(R.id.main),
                "${position+1}º item selecionado.",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}

//fun View.showKeyboard() {
//    this.requestFocus()
//    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
//}
//fun View.hideKeyboard() {
//    this.requestFocus()
//    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
//}