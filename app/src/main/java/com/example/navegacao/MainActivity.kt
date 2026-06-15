package com.example.navegacao

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.navegacao.data.AppDatabase
import com.example.navegacao.navigation.SetupNavigation
import com.example.navegacao.telas.ProdutoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val viewModel = ProdutoViewModel(database.produtoDao())

        setContent {
            SetupNavigation(viewModel = viewModel)
        }
    }
}

