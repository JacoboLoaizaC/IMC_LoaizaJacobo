package com.jacoboloaiza.imc_loaizajacobo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jacoboloaiza.imc_loaizajacobo.ui.ImcScreen
import com.jacoboloaiza.imc_loaizajacobo.ui.theme.IMC_LoaizaJacoboTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMC_LoaizaJacoboTheme {
                ImcScreen()
            }
        }
    }
}
