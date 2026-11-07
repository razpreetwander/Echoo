package com.example.gemini.ui.echoo

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EchooMessageActionRow(
    messageText: String,
    onCopy: () -> Unit,
    onMakeTask: (String) -> Unit,
    onMakeAutomation: (String) -> Unit,
    onSpeak: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isLiked by remember { mutableStateOf<Boolean?>(null) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Copy
        IconButton(onClick = onCopy, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(16.dp))
        }

        // Like
        IconButton(onClick = { isLiked = if (isLiked == true) null else true }, modifier = Modifier.size(32.dp)) {
            Icon(
                Icons.Default.ThumbUp,
                contentDescription = "Like",
                modifier = Modifier.size(16.dp),
                tint = if (isLiked == true) EchooColors.Blue else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Dislike
        IconButton(onClick = { isLiked = if (isLiked == false) null else false }, modifier = Modifier.size(32.dp)) {
            Icon(
                Icons.Default.ThumbDown,
                contentDescription = "Dislike",
                modifier = Modifier.size(16.dp),
                tint = if (isLiked == false) EchooColors.Red else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Speak (TTS via Jarvis Voice engine)
        val coroutineScope = rememberCoroutineScope()
        IconButton(
            onClick = {
                coroutineScope.launch {
                    com.example.gemini.data.jarvis.JarvisVoice.speak(messageText, wait = false)
                }
            },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(Icons.Default.VolumeUp, contentDescription = "Speak", modifier = Modifier.size(16.dp))
        }

        // Make Task (Echoo advanced feature)
        IconButton(onClick = { onMakeTask(messageText) }, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Default.CheckCircleOutline, contentDescription = "Make Task", modifier = Modifier.size(16.dp), tint = EchooColors.Blue)
        }

        // Make Automation (Echoo advanced feature)
        IconButton(onClick = { onMakeAutomation(messageText) }, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Default.Schedule, contentDescription = "Make Automation", modifier = Modifier.size(16.dp), tint = EchooColors.Blue)
        }

        // Share
        IconButton(onClick = onShare, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Default.Share, contentDescription = "Share", modifier = Modifier.size(16.dp))
        }
    }
}
