package com.example.gemini.ui.echoo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EchooComposer(
    promptText: String,
    onPromptChange: (String) -> Unit,
    onSend: () -> Unit,
    onVoiceClick: () -> Unit,
    isGenerating: Boolean,
    onStopGeneration: () -> Unit,
    onAttachClick: () -> Unit,
    isDeepThinking: Boolean,
    onToggleDeepThinking: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(26.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 6.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(6.dp)) {
            // Chips row if Deep Thinking is enabled
            if (isDeepThinking) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = EchooColors.Blue.copy(alpha = 0.12f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Deep Thinking", color = EchooColors.Blue, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "×",
                                modifier = Modifier.clickable { onToggleDeepThinking() },
                                color = EchooColors.Blue,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Input Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Attach button (+)
                IconButton(
                    onClick = onAttachClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Attach",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Text field
                TextField(
                    value = promptText,
                    onValueChange = onPromptChange,
                    placeholder = { Text("Ask Echoo...") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    maxLines = 4,
                    modifier = Modifier.weight(1f)
                )

                // Mic / Dictate
                IconButton(
                    onClick = onVoiceClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Dictate",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.width(2.dp))

                // Send / Live / Stop button
                IconButton(
                    onClick = {
                        if (isGenerating) {
                            onStopGeneration()
                        } else if (promptText.isNotBlank()) {
                            onSend()
                        } else {
                            onVoiceClick()
                        }
                    },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(EchooColors.Blue)
                ) {
                    Icon(
                        imageVector = if (isGenerating) {
                            Icons.Default.Stop
                        } else if (promptText.isNotBlank()) {
                            Icons.Default.ArrowUpward
                        } else {
                            Icons.Default.GraphicEq
                        },
                        contentDescription = "Action",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
