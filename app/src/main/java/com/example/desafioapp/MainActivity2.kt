package com.example.desafioapp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.desafioapp.databinding.ActivityMain2Binding
import com.google.android.material.snackbar.Snackbar

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private var position = -1
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
            // enabling buttons
            binding.editBtn.isEnabled = true
            binding.editBtn.isClickable = true
            binding.excludeBtn.isEnabled = true
            binding.excludeBtn.isClickable = true
            val selectedItem = parent.getItemAtPosition(position) as Produto
            binding.nameL.visibility = View.VISIBLE
            binding.descriptionL.visibility = View.VISIBLE
            binding.valorL.visibility = View.VISIBLE

            binding.nameInput.setText(selectedItem.nome)
            binding.descriptionInput.setText(selectedItem.desc)
            binding.valorInput.setText(selectedItem.value.toString())

            binding.nameInput.requestFocus()

            this.position = position

            Snackbar.make(
                findViewById(R.id.main),
                "${position + 1}º item selecionado.",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        binding.editBtn.setOnClickListener {
            if (position >= 0) {
                listaProduto[position]?.nome = binding.nameInput.text.toString().trim()
                listaProduto[position]?.desc = binding.descriptionInput.text.toString().trim()
                listaProduto[position]?.value = binding.valorInput.text.toString().toDoubleOrNull()

                listAdapter.notifyDataSetChanged()

                binding.nameInput.text?.clear()
                binding.descriptionInput.text?.clear()
                binding.valorInput.text?.clear()

                Snackbar.make(
                    findViewById(R.id.main),
                    "${position + 1}º item modificado.",
                    Snackbar.LENGTH_SHORT
                ).show()

                position = -1
            }
        }

        binding.excludeBtn.setOnClickListener {
            if (position >= 0) {
                listaProduto.removeAt(position)

                binding.nameInput.text?.clear()
                binding.descriptionInput.text?.clear()
                binding.valorInput.text?.clear()

                Snackbar.make(
                    findViewById(R.id.main),
                    "${position + 1}º item excluído.",
                    Snackbar.LENGTH_SHORT
                ).show()

                position = -1
            }
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