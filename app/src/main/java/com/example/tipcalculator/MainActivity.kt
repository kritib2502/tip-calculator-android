package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}
@Composable
fun TipCalculatorScreen() {
    var serviceCostAmountInput by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf(15) }

    val amount = serviceCostAmountInput.toDoubleOrNull() ?: 0.00
    val tip = calculateTip(amount, tipPercent)
    val total = amount + tip

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.tip_calculator_heading),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Button(
            onClick = { serviceCostAmountInput = (1..1000).random().toString() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Generate Bill")
        }

        Spacer(Modifier.height(16.dp))
        EditServiceCostField(
            value = serviceCostAmountInput,
            onValueChange = { serviceCostAmountInput = it }
        )
        Spacer(Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            TipButton(percent = 10, tipPercent = tipPercent, onClick = { tipPercent = 10 })
            TipButton(percent = 15, tipPercent = tipPercent, onClick = { tipPercent = 15 })
            TipButton(percent = 18, tipPercent = tipPercent, onClick = { tipPercent = 18 })
            TipButton(percent = 20, tipPercent = tipPercent, onClick = { tipPercent = 20 })
        }
        Spacer(Modifier.height(16.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(bottom = 200.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(R.string.bill_amount, amount),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.tip_amount, formatCurrency(tip)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Divider(thickness = 1.dp, color = MaterialTheme.colors.onBackground)
                Text(
                    text = stringResource(R.string.total_amount, formatCurrency(total)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}

@Composable
fun TipButton(percent: Int, tipPercent: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (tipPercent == percent) MaterialTheme.colors.primary else Color.Black,
            contentColor = if (tipPercent == percent) MaterialTheme.colors.onPrimary else Color.White
        ),
        modifier = Modifier.size(64.dp)
    ) {
        Text(
            text = "$percent%",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EditServiceCostField(
    value: String,
    onValueChange: (String) -> Unit
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.service_cost)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

private fun calculateTip(
    amount: Double,
    tipPercent: Int = 15
): Double {
    return amount * tipPercent / 100
}

private fun formatCurrency(amount: Double): String {
    return NumberFormat.getCurrencyInstance().format(amount)
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}