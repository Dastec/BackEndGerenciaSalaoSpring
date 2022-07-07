package br.com.dastec.gerenciasalao

import java.text.NumberFormat
import java.util.*


fun realCurrency(value: Number): String{
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    numberFormat.setMaximumFractionDigits(2)
    val valueConvert = numberFormat.format(value)
    return valueConvert
}





