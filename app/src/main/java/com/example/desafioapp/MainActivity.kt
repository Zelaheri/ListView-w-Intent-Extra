package com.example.desafioapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
            if (nameInputID.text.toString().isNotEmpty() && descInputID.text.toString().isNotEmpty() && valueInputID.text.toString().isNotEmpty()){
                if (listaProduto.size == 0){
                    cadastroProduto(listaProduto,
                        nameInputID,
                        descInputID,
                        valueInputID
                    )

                    binding.nameL.error = ""
                    binding.descriptionL.error = ""
                    binding.valorL.error = null

                    Snackbar.make(
                        findViewById(R.id.main),
                        "Produto cadastrado.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    for (produto in listaProduto) {
                        if (produto.nome.contains(nameInputID.text.toString())) {
                            Snackbar.make(
                                findViewById(R.id.main),
                                "Produto com mesmo nome já cadastrado.",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            break
                        } else if (!produto.nome.contains(nameInputID.text.toString())) {
                            cadastroProduto(
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
                            break
                        } else {
                            continue
                        }
                    }
                }

                nameInputID.requestFocus()
            } else {
                // Nome error
                if (nameInputID.text.toString().trim().isEmpty()) {
                    binding.nameL.error = "Preencha o nome!"
                } else {
                    binding.nameL.error = ""
                }
                // Descrição error
                if (descInputID.text.toString().trim().isEmpty()) {
                    binding.descriptionL.error = "Preencha a descrição!"
                } else {
                    binding.descriptionL.error = ""
                }
                // Valor error
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
fun cadastroProduto(listaProduto:ArrayList<Produto>,
                           nameInputId:EditText,
                           descInputId:EditText,
                           valueInputId:EditText){
    listaProduto.add(Produto(
        nameInputId.text.toString().trim(),
        descInputId.text.toString().trim(),
        valueInputId.text.toString().toDoubleOrNull()
    ))
    nameInputId.text.clear()
    descInputId.text.clear()
    valueInputId.text.clear()
}