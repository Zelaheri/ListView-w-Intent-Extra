package com.example.desafioapp;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Product implements Serializable {
    String nome;
    String desc;
    Double value;

    Product(String nome, String desc, Double value) {
        nome = this.nome;
        desc = this.desc;
        value = this.value;
    }

    //getters
    public String getNome() {
        return nome;
    }

    public String getDesc() {
        return desc;
    }

    public Double getValue() {
        return value;
    }

    //setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return "Product{" +
                "nome='" + nome + '\'' +
                ", desc='" + desc + '\'' +
                ", value=" + value +
                '}';
    }
}
