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
        val valueInputID = binding.valorInput


        val submitBtn = binding.submitBtn
        val listarBtn = binding.listBtn

        val database = Database(this)
        var listaProduto = database.readProducts()
        val i = Intent(this, MainActivity2::class.java)

        submitBtn.setOnClickListener {
            if (nameInputID.text.toString().isNotEmpty() &&
                descInputID.text.toString().isNotEmpty() &&
                valueInputID.text.toString().isNotEmpty()
            ) {
                if (listaProduto.size == 0) {
                    database.insertProduto(
                        Produto(
                            idInputID.text.toString().trim().toInt(),
                            nameInputID.text.toString().trim(),
                            descInputID.text.toString().trim(),
                            valueInputID.text.toString().trim().toDouble()
                        )
                    )
                    listaProduto = database.readProducts()
                    LimparCampos(nameInputID, descInputID, valueInputID)

                    Snackbar.make(
                        findViewById(R.id.main),
                        "Produto cadastrado.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    loop@ for (produto in listaProduto) {
                        for (produto in listaProduto) {
                            if (produto.nome == nameInputID.text.toString().trim()) {
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
                        listaProduto = database.readProducts()
                        LimparCampos(nameInputID, descInputID, valueInputID)

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
                binding.valorL.error = null
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
            }
        }

        listarBtn.setOnClickListener {
            //i -> val i = Intent(this, MainActivity2::class.java)
//            i.putExtra("lista", databaseList())
            startActivity(i)
        }
    }
}

fun LimparCampos(
    nomeID: EditText,
    descID: EditText,
    valueID: EditText
) {
    nomeID.text.clear()
    descID.text.clear()
    valueID.text.clear()

    nomeID.requestFocus()
}

//fun Cadastro(
//    lista: ArrayList<Produto>,
//    produtoID: TextView,
//    nomeID: EditText,
//    descID: EditText,
//    valueID: EditText
//) {
//    lista.add(
//        Produto(
//            produtoID.text.toString().toInt(),
//            nomeID.text.toString().trim(),
//            descID.text.toString().trim(),
//            valueID.text.toString().toDouble()
//        )
//    )
//    nomeID.text.clear()
//    descID.text.clear()
//    valueID.text.clear()
//
//    nomeID.requestFocus()
//}