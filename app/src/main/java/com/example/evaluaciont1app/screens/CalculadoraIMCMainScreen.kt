package com.example.evaluaciont1app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculadoraIMCMainScreen(modifier: Modifier){
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var imc by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }

    var mostrarDialogo by remember { mutableStateOf(false) }
    var calculoRealizado by remember { mutableStateOf(false) }

    val colorBajoPeso = Color.Blue
    val colorNormal = Color.Green
    val colorSobrepeso = Color.Yellow
    val colorObesidad = Color.Red
    val colorError = Color.Gray

    var categoriaColor by remember { mutableStateOf(colorError) }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Datos incompletos") },
            text = { Text("Por favor, ingresa tu peso y altura.") },
            confirmButton = {
                Button(onClick = { mostrarDialogo = false }) {
                    Text("Aceptar")
                }
            }
        )
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Card (
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Column (
                Modifier
                    .padding(20.dp), Arrangement.Center, Alignment.CenterHorizontally
            ) {
                Text("Calculadora de IMC",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black)
                Spacer(modifier = Modifier.height(5.dp))
                Text("Índice de Masa Corporal",
                    fontSize = 14.sp,
                    color = Color.Gray)

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = peso,
                    onValueChange = { peso = it },
                    label = { Text("Peso (kg)") },
                    placeholder = { Text("Ej: 70", color = Color.Gray) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = altura,
                    onValueChange = { altura = it },
                    label = { Text("Altura (cm o m)") },
                    placeholder = { Text("Ej: 170 o 1.70", color = Color.Gray) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth()){
                    Button(
                        onClick = {
                            if (peso.isBlank() || altura.isBlank()){
                                mostrarDialogo = true
                                imc = ""
                                categoria = "Faltan datos"
                                categoriaColor = colorError
                                calculoRealizado = false
                            } else {
                                val pesoValue = peso.toDouble()
                                var alturaValue = altura.toDouble()

                                if (pesoValue != null && pesoValue > 0 && alturaValue != null && alturaValue > 0){
                                    if (alturaValue > 3) alturaValue /= 100

                                    if (alturaValue > 0) {
                                        val imcValue = pesoValue / (alturaValue * alturaValue)
                                        imc = String.format("%.1f", imcValue)
                                        when {
                                            imcValue < 18.5 -> {
                                                categoria = "Bajo peso"
                                                categoriaColor = colorBajoPeso
                                            }
                                            imcValue < 25 -> {
                                                categoria = "Peso normal"
                                                categoriaColor = colorNormal
                                            }
                                            imcValue < 30 -> {
                                                categoria = "Sobrepeso"
                                                categoriaColor = colorSobrepeso
                                            }
                                            else -> {
                                                categoria = "Obesidad"
                                                categoriaColor = colorObesidad
                                            }
                                        }
                                        calculoRealizado = true
                                    } else {
                                        imc = ""
                                        categoria = "Altura inválida"
                                        categoriaColor = colorError
                                        calculoRealizado = false
                                    }
                                } else {
                                    imc = ""
                                    categoria = "Valores inválidos"
                                    categoriaColor = colorError
                                    calculoRealizado = false
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2196F3),
                            contentColor = Color.White
                    ),

                    ) {
                        Text("Calcular IMC")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedButton(
                        onClick = {
                            peso = ""
                            altura = ""
                            imc = ""
                            categoria = ""
                            categoriaColor = colorError
                            calculoRealizado = false
                        },
                        modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    ){
                        Text("Limpiar")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {

                    },
                    enabled = calculoRealizado,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = Color.LightGray,
                    )
                ){
                    Text("MOSTRAR DETALLES")
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (imc.isNotEmpty()) {
                    Text(text = "Tu IMC es:", fontSize = 20.sp)
                    Text(text = "$imc", fontSize = 44.sp, fontWeight = FontWeight.Bold)
                    Text(text = categoria, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = categoriaColor)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF1F8FF)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 1.dp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Categorías del IMC:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black)

                        Spacer(modifier = Modifier.height(8.dp))

                        CategoriaItem("• Bajo peso:","< 18.5")
                        CategoriaItem("• Peso normal:","18.5 - 24.9")
                        CategoriaItem("• Sobrepeso:","25.0 - 29.9")
                        CategoriaItem("• Obesidad:","≥ 30.0")
                    }
                }
            }
        }
    }
}

@Composable
fun CategoriaItem(titulo: String, valor: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(titulo, fontSize = 16.sp, color = Color.Black)
        Text(valor, fontSize = 16.sp, color = Color.Black)
    }
}