package com.example.cycles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cycles.ui.theme.CyclesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CyclesTheme {
                CyclesScreen()
                }
            }
        }
    }

@Composable
fun CyclesScreen() {

    var epsilon by remember { mutableStateOf("0.001") }
    var sum by remember { mutableDoubleStateOf(0.0) }
    var lastTerm by remember { mutableDoubleStateOf(0.0) }
    var iterations by remember { mutableIntStateOf(0) }
    var hasCalculated by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Cycles",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Вычисление суммы ряда",
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "S = 1/4 + 1/9 + 1/16 + 1/25 + ...",
            modifier = Modifier.padding(top = 24.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            value = epsilon,
            onValueChange = { epsilon = it },
            label = { Text("Точность ε") },
            placeholder = { Text("Например, 0.001") },
            modifier = Modifier.padding(top = 20.dp),
            singleLine = true
        )

        Button(
            onClick = {val accuracy = epsilon.replace(',', '.').toDoubleOrNull()

                if (accuracy != null && accuracy > 0.0 &&
                    accuracy.isFinite()
                ) {
                    var n = 2
                    var term = 1.0 / (n * n)
                    var total = 0.0
                    var count = 0

                    while (kotlin.math.abs(term) >= accuracy) {
                        total += term
                        count++

                        n++
                        term = 1.0 / (n * n)
                    }

                    sum = total
                    lastTerm = term
                    iterations = count
                    hasCalculated = true
                } },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Вычислить сумму")
        }

        if (hasCalculated) {
            Column(
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(
                    text = "Сумма ряда: %.6f".format(sum),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Последнее слагаемое: %.8f".format(lastTerm),
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Количество итераций: $iterations",
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}