package com.example.terrenosventa.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.terrenosventa.model.RealEstate
import java.text.NumberFormat
import java.util.Locale
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealEstateDetailScreen(item: RealEstate, navController: NavController) {
    val numberFormat = NumberFormat.getNumberInstance(Locale("es", "CL"))
    val formattedPrice = numberFormat.format(item.price)

    var showDialog by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }


    if (showSnackbar) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar("Gracias, Nos Contactaremos a la Brevedad con usted!!")
            showSnackbar = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles de la Propiedad") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver atrás"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Contacto"
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.img_src),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tipo: ${item.type}",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Precio: \$${formattedPrice}",
                style = MaterialTheme.typography.headlineSmall
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
