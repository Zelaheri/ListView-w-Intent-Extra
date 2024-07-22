package com.example.desafioapp

import java.io.Serializable

class Produto(
    var idProduto: Int?,
    var nome: String,
    var desc: String,
    var value: Double
) : Serializable {
    override fun toString(): String {
        return "Produto(idProduto=$idProduto, nome='$nome', desc='$desc', value=$value)"
    }
}