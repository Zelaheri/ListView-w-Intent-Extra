package com.example.desafioapp

import android.content.Intent
import android.os.Bundle
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
                listaProduto.add(Produto(
                    nameInputID.text.toString().trim(),
                    descInputID.text.toString().trim(),
                    valueInputID.text.toString().toDoubleOrNull()
                ))

                nameInputID.text.clear()
                descInputID.text.clear()
                valueInputID.text.clear()

                binding.nameL.error = ""
                binding.descriptionL.error = ""
                binding.valorL.error = null

                Snackbar.make(
                    findViewById(R.id.main),
                    "Produto cadastrado.",
                    Snackbar.LENGTH_SHORT
                ).show()
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