package com.example.gemini.ui.echoo

data class EchooTaskStep(
    val id: String,
    var title: String,
    var command: String,
    var isSkipped: Boolean = false,
    var state: String = "todo" // todo, running, done, error
)

data class EchooTask(
    val id: String,
    var title: String,
    val goal: String,
    var status: String = "plan", // plan, running, paused, completed, failed, cancelled
    val steps: MutableList<EchooTaskStep> = mutableListOf(),
    val logs: MutableList<String> = mutableListOf(),
    var summary: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

data class EchooAutomation(
    val id: String,
    var name: String,
    var instruction: String,
    var time: String = "09:00",
    var isEnabled: Boolean = true,
    val reports: MutableList<String> = mutableListOf(),
    val createdAt: Long = System.currentTimeMillis()
)
