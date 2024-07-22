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
            it.moveToFirst()
            while (it.moveToNext()) {
                val idProduto = it.getColumnIndex("idProduto")
                val nomeProduto = it.getColumnIndex("nome")
                val descProduto = it.getColumnIndex("desc")
                val valorProduto = it.getColumnIndex("valor")

                produto.add(
                    Produto(
                        it.getInt(idProduto),
                        it.getString(nomeProduto),
                        it.getString(descProduto),
                        it.getDouble(valorProduto)
                    )
                )
            }
            return produto
        }
    }

    fun insertProduto(produto: Produto): Long {
        val values = ContentValues().apply {
            put("nome", produto.nome)
            put("desc", produto.desc)
            put("valor", produto.value)
        }
        return writableDatabase.insert("produto", null, values)
    }

    fun updateProduto(produto: Produto): Int {
        val values = ContentValues().apply {
            put("nome", produto.nome)
            put("desc", produto.desc)
            put("valor", produto.value)
        }
        return writableDatabase.update(
            "produto",
            values,
            "id = ?",
            arrayOf(produto.idProduto.toString())
        )
    }
}