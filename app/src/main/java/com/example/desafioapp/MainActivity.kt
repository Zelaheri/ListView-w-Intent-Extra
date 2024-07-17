package com.example.desafioapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.desafioapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nameInputID = binding.nameInput
        val descInputID = binding.descriptionInput
        val valueInputID = binding.valorInput


        val submitBtn = binding.submitBtn
        val listarBtn = binding.listBtn

        val listaProduto = ArrayList<Produto>()
        val i = Intent(this, MainActivity2::class.java)


        submitBtn.setOnClickListener {
            if (nameInputID.text.toString().isNotEmpty() &&
                descInputID.text.toString().isNotEmpty() &&
                valueInputID.text.toString().isNotEmpty()
            ) {
                if (listaProduto.size == 0) {
                    Cadastro(
                        listaProduto,
                        nameInputID,
                        descInputID,
                        valueInputID
                    )

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
                        Cadastro(
                            listaProduto,
                            nameInputID,
                            descInputID,
                            valueInputID
                        )

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
            i.putExtra("lista", listaProduto as Serializable)
            startActivity(i)
        }
    }
}

fun Cadastro(
    lista: ArrayList<Produto>,
    nomeID: EditText,
    descID: EditText,
    valueID: EditText
) {
    lista.add(
        Produto(
            nomeID.text.toString().trim(),
            descID.text.toString().trim(),
            valueID.text.toString().toDoubleOrNull()
        )
    )
    nomeID.text.clear()
    descID.text.clear()
    valueID.text.clear()

    nomeID.requestFocus()
}