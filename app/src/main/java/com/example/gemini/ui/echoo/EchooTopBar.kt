package com.example.gemini.ui.echoo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemini.ui.chat.ChatViewModel

@Composable
fun EchooTopBar(
    viewModel: ChatViewModel,
    onOpenDrawer: () -> Unit,
    onNavigateToTerminal: () -> Unit,
    onNavigateToIde: () -> Unit,
    onStartLiveOrb: () -> Unit,
    onToggleGhostMode: () -> Unit,
    isGhostMode: Boolean,
    modifier: Modifier = Modifier
) {
    var showModelMenu by remember { mutableStateOf(false) }
    val selectedAgent by viewModel.newChatAgent.collectAsState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Drawer toggle
        IconButton(
            onClick = onOpenDrawer,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        // Model / Agent Selector Dropdown (Echoo Pill Style)
        Box {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.clickable { showModelMenu = true }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "ECHOO",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp,
                        color = EchooColors.Blue
                    )
                    Text(
                        text = if (selectedAgent == com.example.gemini.domain.model.AgentKind.CLAUDE) "Claude" else "Agy",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            DropdownMenu(
                expanded = showModelMenu,
                onDismissRequest = { showModelMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("ECHOO Antigravity (agy)") },
                    onClick = {
                        viewModel.setNewChatAgent(com.example.gemini.domain.model.AgentKind.AGY)
                        showModelMenu = false
                    },
                    leadingIcon = {
                        RadioButton(selected = selectedAgent == com.example.gemini.domain.model.AgentKind.AGY, onClick = null)
                    }
                )
                DropdownMenuItem(
                    text = { Text("ECHOO Claude Code (CLI)") },
                    onClick = {
                        viewModel.setNewChatAgent(com.example.gemini.domain.model.AgentKind.CLAUDE)
                        showModelMenu = false
                    },
                    leadingIcon = {
                        RadioButton(selected = selectedAgent == com.example.gemini.domain.model.AgentKind.CLAUDE, onClick = null)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // AntiGem Core Buttons: Terminal
        IconButton(
            onClick = onNavigateToTerminal,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Code,
                contentDescription = "Terminal",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }

        // AntiGem Core Buttons: IDE Code Editor
        IconButton(
            onClick = onNavigateToIde,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Folder,
                contentDescription = "IDE Editor",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }

        // Echoo Incognito / Ghost Mode Button
        IconButton(
            onClick = onToggleGhostMode,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = if (isGhostMode) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "Ghost Mode",
                tint = if (isGhostMode) EchooColors.Blue else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }

        // Jarvis / Live Orb Button
        IconButton(
            onClick = onStartLiveOrb,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.GraphicEq,
                contentDescription = "Live Voice Orb",
                tint = EchooColors.Blue
            )
        }
    }
}
