package com.jacoboloaiza.imc_loaizajacobo.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jacoboloaiza.imc_loaizajacobo.model.ImcResult
import kotlin.math.round

class ImcViewModel : ViewModel() {

    var peso = mutableStateOf("")
        private set

    var altura = mutableStateOf("")
        private set

    var resultado = mutableStateOf(ImcResult())
        private set

    var mostrarError = mutableStateOf(false)
        private set

    var mensajeError = mutableStateOf("")
        private set

    // Setters (si prefieres modificar desde la UI)
    fun onPesoChange(value: String) {
        peso.value = value
    }

    fun onAlturaChange(value: String) {
        altura.value = value
    }

    fun limpiarCampos() {
        peso.value = ""
        altura.value = ""
        resultado.value = ImcResult()
        mostrarError.value = false
        mensajeError.value = ""
    }

    fun calcularImc() {
        // Reset error
        mostrarError.value = false
        mensajeError.value = ""

        val pesoStr = peso.value.trim().replace(',', '.')
        val alturaStr = altura.value.trim().replace(',', '.')

        // Validaciones
        if (pesoStr.isEmpty()) {
            mostrarError.value = true
            mensajeError.value = "Por favor ingresa tu peso en kg."
            resultado.value = ImcResult()
            return
        }
        if (alturaStr.isEmpty()) {
            mostrarError.value = true
            mensajeError.value = "Por favor ingresa tu altura en cm."
            resultado.value = ImcResult()
            return
        }

        val pesoDouble = pesoStr.toDoubleOrNull()
        val alturaCmDouble = alturaStr.toDoubleOrNull()

        if (pesoDouble == null || pesoDouble <= 0.0) {
            mostrarError.value = true
            mensajeError.value = "Peso inválido. Debe ser un número mayor que 0."
            resultado.value = ImcResult()
            return
        }
        if (alturaCmDouble == null || alturaCmDouble <= 0.0) {
            mostrarError.value = true
            mensajeError.value = "Altura inválida. Debe ser un número mayor que 0."
            resultado.value = ImcResult()
            return
        }

        // Conversión cm -> m
        val alturaM = alturaCmDouble / 100.0

        // Cálculo IMC
        val rawImc = pesoDouble / (alturaM * alturaM)
        // Redondear a 2 decimales
        val imcRounded = (round(rawImc * 100.0) / 100.0)

        // Clasificación según OMS y color sugerido (ARGB hex)
        val (clasificacion, color) = when {
            imcRounded < 18.5 -> Pair("Bajo peso", 0xFFBBDEFB)    // Azul claro
            imcRounded < 25.0 -> Pair("Peso normal", 0xFFC8E6C9) // Verde claro
            imcRounded < 30.0 -> Pair("Sobrepeso", 0xFFFFF9C4)   // Amarillo claro
            else -> Pair("Obesidad", 0xFFFFCDD2)                 // Rojo claro
        }

        resultado.value = ImcResult(imc = imcRounded, clasificacion = clasificacion, color = color)
    }
}


