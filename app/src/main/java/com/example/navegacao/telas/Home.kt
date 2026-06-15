package com.example.navegacao.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.navegacao.R
import com.example.navegacao.navigation.Telas
import com.example.navegacao.ui.theme.DarkBeige

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaHome(
    backStack: SnapshotStateList<Telas>
){
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Minha Loja", color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBeige)


            )
        },
        bottomBar = {
            BottomAppBar(containerColor = DarkBeige,
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Siga-nos em nossas redes sociais",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Abrir Instagram",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                                .clickable{ uriHandler.openUri("https://instagram.com")}
                        )

                        Icon(
                            imageVector = Icons.Default.Facebook,
                            contentDescription = "Abrir Facebook",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                                .clickable{ uriHandler.openUri("https://www.facebook.com")}
                        )

                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Acessar Site",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                                .clickable{ uriHandler.openUri("https://www.google.com")}
                        )
                    }
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.banner),
                contentDescription = "Banner da Loja",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        backStack.add(Telas.TelaProduto)
                    }
                ) {
                    Text(text = "Avançar para Produtos")
                }
            }
        }

    }

}
