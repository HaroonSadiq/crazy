package com.echospark.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.echospark.ui.theme.GradientEnd
import com.echospark.ui.theme.GradientStart
import com.echospark.ui.theme.MoodChillColor
import com.echospark.ui.theme.MoodDeepColor
import com.echospark.ui.theme.MoodSpicyColor
import com.echospark.ui.theme.TextLight

@Composable
fun MoodSelector(
    onMoodSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMood by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            "Choose your vibe",
            style = MaterialTheme.typography.headlineSmall,
            color = TextLight,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Chill Mood
        MoodCard(
            title = "Chill ☀️",
            description = "Light & playful questions",
            color = MoodChillColor,
            isSelected = selectedMood == "Chill",
            onClick = {
                selectedMood = "Chill"
                onMoodSelected("Chill")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Deep Mood
        MoodCard(
            title = "Deep 🌙",
            description = "Meaningful & thoughtful",
            color = MoodDeepColor,
            isSelected = selectedMood == "Deep",
            onClick = {
                selectedMood = "Deep"
                onMoodSelected("Deep")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Spicy Mood
        MoodCard(
            title = "Spicy 🔥",
            description = "Bold & intimate moments",
            color = MoodSpicyColor,
            isSelected = selectedMood == "Spicy",
            onClick = {
                selectedMood = "Spicy"
                onMoodSelected("Spicy")
            }
        )
    }
}

@Composable
fun MoodCard(
    title: String,
    description: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateColorAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "moodCardScale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) color.copy(alpha = 0.9f) else color.copy(alpha = 0.7f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 12.dp else 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                title,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = TextLight
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = TextLight.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun SparkPointDisplay(
    currentPoints: Int,
    recentEarnings: Int = 0,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "✨ Spark Points ✨",
                style = MaterialTheme.typography.labelMedium,
                color = TextLight.copy(alpha = 0.9f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                currentPoints.toString(),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                color = Color(0xFFFFD700)
            )

            if (recentEarnings > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "+$recentEarnings ✨",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFFFFD700)
                )
            }
        }
    }
}

@Composable
fun QuestionCard(
    question: String,
    category: String,
    intensity: String,
    modifier: Modifier = Modifier
) {
    val intensityColor = when (intensity) {
        "Chill" -> MoodChillColor
        "Deep" -> MoodDeepColor
        "Spicy" -> MoodSpicyColor
        else -> MoodChillColor
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Category Badge
            Box(
                modifier = Modifier
                    .background(
                        color = intensityColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    category,
                    style = MaterialTheme.typography.labelSmall,
                    color = intensityColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Question Text
            Text(
                question,
                style = MaterialTheme.typography.headlineSmall,
                color = TextDark,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Intensity Indicator
            Text(
                "Intensity: " + when (intensity) {
                    "Chill" -> "☀️ Light"
                    "Deep" -> "🌙 Meaningful"
                    "Spicy" -> "🔥 Bold"
                    else -> intensity
                },
                style = MaterialTheme.typography.labelSmall,
                color = intensityColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun PartyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF6200EE),
                        Color(0xFFD946EF),
                        Color(0xFFFF006E)
                    )
                )
            )
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            color = TextLight,
            textAlign = TextAlign.Center
        )
    }
}

val TextDark = Color(0xFF0D0D0D)
