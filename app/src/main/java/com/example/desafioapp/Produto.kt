package com.example.desafioapp

import java.io.Serializable

class Produto(val nome: String, val desc: String, val value: Double?):Serializable {
    override fun toString(): String {
        return "Produto(nome='$nome', desc='$desc', value=$value)"
    }
}