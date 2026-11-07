package com.example.gemini.ui.echoo

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

object EchooTaskManager {
    private val _tasks = MutableStateFlow<List<EchooTask>>(emptyList())
    val tasks = _tasks.asStateFlow()

    private val _automations = MutableStateFlow<List<EchooAutomation>>(emptyList())
    val automations = _automations.asStateFlow()

    fun createPlan(goal: String): EchooTask {
        val taskId = UUID.randomUUID().toString().take(8)
        val defaultSteps = mutableListOf(
            EchooTaskStep(UUID.randomUUID().toString(), "Analyze requirements & inspect environment", "find . -maxdepth 2"),
            EchooTaskStep(UUID.randomUUID().toString(), "Execute core implementation", "echo 'Running: $goal'"),
            EchooTaskStep(UUID.randomUUID().toString(), "Verify output and tests", "echo 'Verifying results'"),
            EchooTaskStep(UUID.randomUUID().toString(), "Generate completion summary report", "echo 'Done'")
        )
        val task = EchooTask(
            id = taskId,
            title = if (goal.length > 30) goal.take(30) + "..." else goal,
            goal = goal,
            status = "plan",
            steps = defaultSteps
        )
        _tasks.value = listOf(task) + _tasks.value
        return task
    }

    fun approveTask(task: EchooTask) {
        task.status = "running"
        task.logs.add("[${java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())}] Task approved. Starting execution...")
        _tasks.value = _tasks.value.toList()
    }

    fun saveAutomation(name: String, instruction: String, time: String) {
        val auto = EchooAutomation(
            id = UUID.randomUUID().toString().take(8),
            name = name,
            instruction = instruction,
            time = time
        )
        _automations.value = listOf(auto) + _automations.value
    }
}
