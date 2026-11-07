package com.example.gemini.ui.echoo

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemini.ui.chat.ChatViewModel

@Composable
fun EchooLiveOrbOverlay(
    chatViewModel: ChatViewModel,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val isJarvisSpeaking by com.example.gemini.data.jarvis.JarvisVoice.speaking.collectAsState()
    val lastSpoken by com.example.gemini.data.jarvis.JarvisVoice.lastSpoken.collectAsState()
    var isMuted by remember { mutableStateOf(false) }
    var captionText by remember { mutableStateOf("Listening... Speak with Jarvis.") }

    val infiniteTransition = rememberInfiniteTransition(label = "orbPulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.94f))
            .clickable(onClick = {}),
        contentAlignment = Alignment.Center
    ) {
        // Top Live Bar
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(EchooColors.Red)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "LIVE AGENT",
                color = Color.White.copy(alpha = 0.8f),
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                letterSpacing = 1.5.sp
            )
        }

        // Orb in center
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .scale(if (isMuted) 0.95f else scale)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF6894FF),
                                EchooColors.Blue,
                                Color(0xFF1A3BA8),
                                Color.Transparent
                            )
                        )
                    )
            )

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = if (isMuted) "Microphone muted" else if (isJarvisSpeaking && lastSpoken.isNotBlank()) "Jarvis: $lastSpoken" else captionText,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 16.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }

        // Bottom Controls
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { isMuted = !isMuted },
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(if (isMuted) EchooColors.Red else Color.White.copy(alpha = 0.15f))
            ) {
                Icon(
                    imageVector = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                    contentDescription = "Mute",
                    tint = Color.White
                )
            }

            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.25f))
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "End Live",
                    tint = Color.White
                )
            }
        }
    }
}

object EchooLiveOrbManager {
    var isLiveOrbActive by mutableStateOf(false)
        private set

    fun startOrb() {
        isLiveOrbActive = true
    }

    fun stopOrb() {
        isLiveOrbActive = false
    }
}
