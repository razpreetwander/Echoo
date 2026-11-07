package com.example.gemini.ui.echoo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EchooAutomationsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val automations by EchooTaskManager.automations.collectAsState()
    var showNewDialog by remember { mutableStateOf(false) }
    var autoName by remember { mutableStateOf("") }
    var autoInstruction by remember { mutableStateOf("") }
    var autoTime by remember { mutableStateOf("09:00") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Automations (Daily Routines)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { showNewDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "New")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (automations.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No automations scheduled yet. Tap + to set one up.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(automations) { item ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(item.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.weight(1f))
                                Switch(checked = item.isEnabled, onCheckedChange = { item.isEnabled = it })
                            }
                            Text("Runs daily at ${item.time}", fontSize = 12.sp, color = EchooColors.Blue)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(item.instruction, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }

    if (showNewDialog) {
        AlertDialog(
            onDismissRequest = { showNewDialog = false },
            title = { Text("New Daily Automation", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = autoName,
                        onValueChange = { autoName = it },
                        label = { Text("Name") },
                        placeholder = { Text("e.g. Daily Git pull & build") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = autoInstruction,
                        onValueChange = { autoInstruction = it },
                        label = { Text("Task to run") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = autoTime,
                        onValueChange = { autoTime = it },
                        label = { Text("Time (HH:MM)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (autoName.isNotBlank() && autoInstruction.isNotBlank()) {
                            EchooTaskManager.saveAutomation(autoName, autoInstruction, autoTime)
                            showNewDialog = false
                            autoName = ""
                            autoInstruction = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EchooColors.Blue)
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewDialog = false }) { Text("Cancel") }
            }
        )
    }
}
