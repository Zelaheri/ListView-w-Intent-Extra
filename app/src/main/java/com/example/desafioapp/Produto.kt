package com.example.desafioapp

import java.io.Serializable

class Produto(var nome: String, var desc: String, var value: Double?):Serializable {
    override fun toString(): String {
        return "Produto(nome='$nome', desc='$desc', value=$value)"
    }
}