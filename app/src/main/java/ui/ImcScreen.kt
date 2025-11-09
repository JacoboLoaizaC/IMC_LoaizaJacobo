package com.jacoboloaiza.imc_loaizajacobo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.input.KeyboardType
import com.jacoboloaiza.imc_loaizajacobo.viewmodel.ImcViewModel

@Composable
fun ImcScreen(
    viewModel: ImcViewModel = viewModel()
) {
    val peso by viewModel.peso
    val altura by viewModel.altura
    val resultado by viewModel.resultado
    val mostrarError by viewModel.mostrarError
    val mensajeError by viewModel.mensajeError

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "CALCULADORA DE IMC",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = peso,
            onValueChange = { viewModel.onPesoChange(it) },
            label = { Text("Peso (kg)") },
            placeholder = { Text("Ingrese su peso") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = altura,
            onValueChange = { viewModel.onAlturaChange(it) },
            label = { Text("Altura (cm)") },
            placeholder = { Text("Ingrese su altura en cm") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        if (mostrarError) {
            Text(
                text = mensajeError,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.calcularImc()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Calcular")
            }

            OutlinedButton(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.limpiarCampos()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Limpiar")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (resultado.imc > 0.0) {
            // Card con color de fondo según la clasificación
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(resultado.color)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Tu IMC es: ${"%.2f".format(resultado.imc)}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Clasificación: ${resultado.clasificacion}",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

