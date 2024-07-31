package com.example.desafioapp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
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
        var arrayListProduto = database.readProducts()
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            arrayListProduto
        )

        listView.adapter = adapter

        listView.setOnItemClickListener { parent, _, position, _ ->
            // enable to view buttons
            binding.nameL.visibility = View.VISIBLE
            binding.descriptionL.visibility = View.VISIBLE
            binding.valorL.visibility = View.VISIBLE
            // enabling deactivated text inputs
            nameInputID.isEnabled = true
            descInputID.isEnabled = true
            valueInputID.isEnabled = true
            // enabling to click buttons
            binding.editBtn.isEnabled = true
            binding.editBtn.isClickable = true
            binding.excludeBtn.isEnabled = true
            binding.excludeBtn.isClickable = true

            val selectedItem = parent.getItemAtPosition(position) as Produto

            idInputID.setText(selectedItem.idProduct.toString())
            nameInputID.setText(selectedItem.name)
            descInputID.setText(selectedItem.desc)
            valueInputID.setText(selectedItem.value.toString())

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
                    database.updateProduto(
                        Produto(
                            idInputID.text.toString().toInt(),
                            nameInputID.text.toString().trim(),
                            descInputID.text.toString().trim(),
                            valueInputID.text.toString().toDouble()
                        )
                    )

                    arrayListProduto = database.readProducts()
                    listView.adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        arrayListProduto
                    )
                    adapter.notifyDataSetChanged()

                    clearFields(nameInputID, descInputID, valueInputID)
                    disablingTextInputs(nameInputID, descInputID, valueInputID)

                    Snackbar.make(
                        findViewById(R.id.main),
                        "${position + 1}º item modificado.",
                        Snackbar.LENGTH_SHORT
                    ).show()

                    position = -1
                }
            } else {
                // Nome TextLayout
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
                database.deleteProduto(
                    Produto(
                        idInputID.text.toString().toInt(),
                        nameInputID.text.toString().trim(),
                        descInputID.text.toString().trim(),
                        valueInputID.text.toString().toDouble()
                    )
                )

                arrayListProduto = database.readProducts()
                listView.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    arrayListProduto
                )
                adapter.notifyDataSetChanged()

                clearFields(nameInputID, descInputID, valueInputID)
                disablingTextInputs(nameInputID, descInputID, valueInputID)

                if (arrayListProduto.size > 0) {
                    Snackbar.make(
                        findViewById(R.id.main),
                        "${position + 1}º item excluído.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    finish()
                }

                position = -1
            }
        }
        if (arrayListProduto.size == 0){
            TODO("stopwatch delay" +
                    "see: https://github.com/Baeldung/kotlin-tutorials/blob/master/core-kotlin-modules/core-kotlin-lang-3/src/main/kotlin/com/baeldung/callFunction/afterDelay/CallFunctionAfterDelay.kt" +
                    "see: https://www.baeldung.com/kotlin/call-function-after-delay")
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

fun disablingTextInputs(nameField: EditText, descField: EditText, valueField: EditText) {
    if (nameField.isEnabled && descField.isEnabled && valueField.isEnabled) {
        nameField.isEnabled = false
        descField.isEnabled = false
        valueField.isEnabled = false
    } else {
        nameField.isEnabled = true
        descField.isEnabled = true
        valueField.isEnabled = true
    }
}