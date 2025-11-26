package com.example.saudeemdia  // deixe igual ao package do MainActivity

data class HealthTask(
    val id: Long,
    var titulo: String,
    var tipo: String,      // "Consulta" ou "Exame"
    var data: String,      // Ex.: "10/12/2025"
    var realizado: Boolean // true = realizado, false = pendente
)
