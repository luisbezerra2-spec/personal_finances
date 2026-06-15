package com.example.navegacao.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Arquivo responsável por criar e gerenciar o arquivo físico do banco de dados dentro do celular do usuário.

@Database(entities = [Produto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun produtoDao(): ProdutoDao

    companion object{
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "loja_database"
                ).build().also { Instance = it }
            }
        }

    }
}

