package com.example.desafioapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.desafioapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idInputID = binding.idProduto
        val nameInputID = binding.nameInput
        val descInputID = binding.descriptionInput
        val valueInputID = binding.valueInput


        val submitBtn = binding.submitBtn
        val listBtn = binding.listBtn

        val database = Database(this)
        val main2 = Intent(this, MainActivity2::class.java)

        submitBtn.setOnClickListener {
            val arrayListProduto = database.readProducts()
            if (nameInputID.text.toString().isNotEmpty() &&
                descInputID.text.toString().isNotEmpty() &&
                valueInputID.text.toString().isNotEmpty()
            ) {
                if (arrayListProduto.size == 0) {
                    database.insertProduto(
                        Produto(
                            idInputID.text.toString().trim().toInt(),
                            nameInputID.text.toString().trim(),
                            descInputID.text.toString().trim(),
                            valueInputID.text.toString().trim().toDouble()
                        )
                    )
                    database.readProducts()
                    clearFields(nameInputID, descInputID, valueInputID)

                    Snackbar.make(
                        findViewById(R.id.main),
                        "Produto cadastrado.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    loop@ for (produto1 in arrayListProduto) {
                        for (produto2 in arrayListProduto) {
                            if (produto2.name == nameInputID.text.toString().trim()) {
                                nameInputID.requestFocus()
                                Snackbar.make(
                                    findViewById(R.id.main),
                                    "Produto com mesmo nome já cadastrado.",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                                break@loop
                            }
                        }
                        database.insertProduto(
                            Produto(
                                idInputID.text.toString().trim().toInt(),
                                nameInputID.text.toString().trim(),
                                descInputID.text.toString().trim(),
                                valueInputID.text.toString().trim().toDouble()
                            )
                        )
                        database.readProducts()
                        clearFields(nameInputID, descInputID, valueInputID)

                        Snackbar.make(
                            findViewById(R.id.main),
                            "Produto cadastrado.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        break@loop
                    }
                }
                binding.nameL.error = ""
                binding.descriptionL.error = ""
                binding.valueL.error = ""
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
                    binding.valueL.error = "Preencha o valor!"
                } else {
                    binding.valueL.error = null
                }
            }
        }

        listBtn.setOnClickListener {
            startActivity(main2)
        }
    }
}

fun clearFields(
    nomeID: EditText,
    descID: EditText,
    valueID: EditText
) {
    nomeID.text.clear()
    descID.text.clear()
    valueID.text.clear()

    nomeID.requestFocus()
}