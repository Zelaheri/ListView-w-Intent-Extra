package com.example.desafioapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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

        val idInputID = binding.idProduto
        val nameInputID = binding.nameInput
        val descInputID = binding.descriptionInput
        val valueInputID = binding.valorInput
        val listView = binding.listProducts

        val database = Database(this)
        val listaProduto = database.readProducts()
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            listaProduto
        )
//        listUsers = database.readUsers()
//        adapter = setAdapter(listUsers)
//        binding.listaItens.adapter = adapter
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, _, position, _ ->
            // enable to view buttons
            binding.nameL.visibility = View.VISIBLE
            binding.descriptionL.visibility = View.VISIBLE
            binding.valorL.visibility = View.VISIBLE
            // enabling to click buttons
            binding.editBtn.isEnabled = true
            binding.editBtn.isClickable = true
            binding.excludeBtn.isEnabled = true
            binding.excludeBtn.isClickable = true

            val selectedItem = parent.getItemAtPosition(position) as Produto

//            nameInputID.setText(selectedItem.nome)
//            descInputID.setText(selectedItem.desc)
//            valueInputID.setText(selectedItem.value.toString())

            nameInputID.requestFocus()

            this.position = position

            Snackbar.make(
                findViewById(R.id.main),
                "${position + 1}º item selecionado.",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        binding.editBtn.setOnClickListener {
            // enabling buttons
            if (nameInputID.text.toString().isNotEmpty() &&
                descInputID.text.toString().isNotEmpty() &&
                valueInputID.text.toString().isNotEmpty())
            {
                changeButtonsVisibility(binding.editBtn, binding.excludeBtn)
                if (position >= 0) {
//                    listaProduto[position].nome = nameInputID.text.toString().trim()
//                    listaProduto[position].desc = descInputID.text.toString().trim()
//                    listaProduto[position].value = valueInputID.text.toString().toDouble()
//
//                    listAdapter.notifyDataSetChanged()

                    LimparCampos(nameInputID, descInputID, valueInputID)

                    Snackbar.make(
                        findViewById(R.id.main),
                        "${position + 1}º item modificado.",
                        Snackbar.LENGTH_SHORT
                    ).show()

                    position = -1
                }
            } else {
                if (nameInputID.text.toString().trim().isEmpty()) {
                    binding.nameL.error = "Preencha o nome!"
                } else {
                    binding.nameL.error = ""
                }
                // Descrição TextLayout
                if (descInputID.text.toString().trim().isEmpty()) {
                    binding.descriptionL.error = "Preencha a descrição!"
                } else {
                    binding.descriptionL.error = ""
                }
                // Valor TextLayout
                if (valueInputID.text.isNullOrEmpty()) {
                    binding.valorL.error = "Preencha o valor!"
                } else {
                    binding.valorL.error = null
                }
                Snackbar.make(
                    findViewById(R.id.main),
                    "Preencha todos os campos!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        binding.excludeBtn.setOnClickListener {
            changeButtonsVisibility(binding.editBtn, binding.excludeBtn)
            if (position >= 0) {
//                listaProduto.remove(listaProduto[position])
//
//                listAdapter.notifyDataSetChanged()

                LimparCampos(nameInputID, descInputID, valueInputID)

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

fun changeButtonsVisibility(editBtn: Button, excludeBtn: Button){
    if (editBtn.isEnabled && excludeBtn.isEnabled){
        editBtn.isEnabled = false
        editBtn.isClickable = false
        excludeBtn.isEnabled = false
        excludeBtn.isClickable = false
    } else {
        editBtn.isEnabled = true
        editBtn.isClickable = true
        excludeBtn.isEnabled = true
        excludeBtn.isClickable = true
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