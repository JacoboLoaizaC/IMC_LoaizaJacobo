package com.jacoboloaiza.imc_loaizajacobo.model

data class ImcResult(
    val imc: Double = 0.0,
    val clasificacion: String = "",
    val color: Long = 0xFFFFFFFF // ARGB
)

