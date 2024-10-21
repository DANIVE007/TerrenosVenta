package com.example.terrenosventa.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.terrenosventa.model.RealEstate
import com.example.terrenosventa.viewmodel.RealEstateViewModel
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun RealEstateListScreen(navController: NavController, viewModel: RealEstateViewModel = viewModel()) {
    val realEstateList by viewModel.realEstateList.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }


    if (showSnackbar) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar("Nos Contactaremos a la Brevedad con usted!!")
            showSnackbar = false
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Contacto"
                )
            }
        }
    ) { innerPadding ->
        if (realEstateList.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // LazyVerticalGrid para mostrar las imágenes en cuadrícula
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(realEstateList) { item ->
                    RealEstateItem(item) {
                        val itemJson = URLEncoder.encode(Gson().toJson(item), StandardCharsets.UTF_8.toString())
                        navController.navigate("detail_screen/$itemJson")
                    }
                }
            }
        }
    }


    if (showDialog) {
        ContactDialog(
            onDismiss = { showDialog = false },
            onConfirm = {
                showDialog = false
                showSnackbar = true
            }
        )
    }
}

@Composable
fun RealEstateItem(item: RealEstate, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onItemClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(item.img_src),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ContactDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    var email by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Contacto") },
        text = {
            Column {
                Text(text = "Escriba su e-mail:")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()

                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
