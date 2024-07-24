package com.example.desafioapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, "Database.db", null, 1) {
    private val sql = "CREATE TABLE produto(" +
            "idProduto INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nome TEXT NOT NULL," +
            "descrição TEXT NOT NULL," +
            "valor DOUBLE NOT NULL)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {
        db.execSQL("DROP TABLE produto")
        onCreate(db)
    }

    fun readProducts(): ArrayList<Produto> {
        val produto = arrayListOf<Produto>()
        val cursor = readableDatabase.query(
            true,
            "produto",
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

        cursor.use {
            while (it.moveToNext()) {
                val idProduto = it.getColumnIndex("idProduto")
                val nameProduct = it.getColumnIndex("nome")
                val descProduct = it.getColumnIndex("descrição")
                val valueProduct = it.getColumnIndex("valor")

                produto.add(
                    Produto(
                        it.getInt(idProduto),
                        it.getString(nameProduct),
                        it.getString(descProduct),
                        it.getDouble(valueProduct)
                    )
                )
            }
            return produto
        }
    }

    fun insertProduto(produto: Produto): Long {
        val values = ContentValues().apply {
            put("nome", produto.name)
            put("descrição", produto.desc)
            put("valor", produto.value)
        }
        return writableDatabase.insert(
            "produto",
            null,
            values
        )
    }

    fun updateProduto(produto: Produto): Int {
        val values = ContentValues().apply {
            put("nome", produto.name)
            put("descrição", produto.desc)
            put("valor", produto.value)
        }
        return writableDatabase.update(
            "produto",
            values,
            "idProduto = ?",
            arrayOf(produto.idProduct.toString())
        )
    }

    fun deleteProduto(produto: Produto): Int {
        return writableDatabase.delete(
            "produto",
            "idProduto = ?",
            arrayOf(produto.idProduct.toString())
        )
    }
}