package com.example.desafioapp

import java.io.Serializable

class Produto(
    var idProduct: Int?,
    var name: String,
    var desc: String,
    var value: Double
) : Serializable {
    override fun toString(): String {
        return "Produto(idProduto=$idProduct, name='$name', desc='$desc', value=$value)"
    }
}