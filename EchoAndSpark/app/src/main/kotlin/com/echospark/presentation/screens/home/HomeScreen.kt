package com.echospark.presentation.screens.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.echospark.presentation.components.PartyButton
import com.echospark.presentation.components.SparkPointDisplay
import com.echospark.ui.theme.GradientEnd
import com.echospark.ui.theme.GradientStart
import com.echospark.ui.theme.SparkGold
import com.echospark.ui.theme.TextLight

@Composable
fun HomeScreen(
    onGameStart: () -> Unit,
    onQuestClick: () -> Unit,
    onShopClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentPoints by remember { mutableStateOf(2850) }
    val recentEarnings by remember { mutableStateOf(50) }
    val dayStreak by remember { mutableStateOf(12) }
    val gamesPlayed by remember { mutableStateOf(47) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A0033),
                        Color(0xFF2D1B4E),
                        Color(0xFF1A0033)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            // Header with emoji celebration
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Echo & Spark",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = TextLight
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("💕", fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Spark Points Display Card
            SparkPointDisplay(
                currentPoints = currentPoints,
                recentEarnings = recentEarnings
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Start Game Button - LARGE & PROMINENT
            PartyButton(
                text = "🎮 Start a New Round",
                onClick = onGameStart,
                modifier = Modifier.height(64.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Stats Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                StatCard(
                    label = "Day Streak",
                    value = dayStreak.toString(),
                    icon = "🔥",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                StatCard(
                    label = "Games Played",
                    value = gamesPlayed.toString(),
                    icon = "🎯",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Recent Games Section
            Text(
                "Recent Games ◀▶",
                style = MaterialTheme.typography.labelLarge,
                color = TextLight,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Game Round Cards
            GameRoundCard(
                title = "Perfect Match! 🎉",
                points = "+50",
                mood = "Deep 🌙",
                time = "2 hours ago"
            )

            Spacer(modifier = Modifier.height(10.dp))

            GameRoundCard(
                title = "Close One!",
                points = "+10",
                mood = "Spicy 🔥",
                time = "5 hours ago"
            )

            Spacer(modifier = Modifier.height(10.dp))

            GameRoundCard(
                title = "Perfect Match! 🎉",
                points = "+50",
                mood = "Chill ☀️",
                time = "1 day ago"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Milestones Section
            Text(
                "Milestones 🏆",
                style = MaterialTheme.typography.labelLarge,
                color = TextLight,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            MilestoneCard(
                name = "First Connection",
                description = "Complete your first round",
                isUnlocked = true,
                unlockedDate = "April 1, 2026"
            )

            Spacer(modifier = Modifier.height(10.dp))

            MilestoneCard(
                name = "10 Perfect Matches",
                description = "Get 10 correct guesses",
                isUnlocked = false,
                progress = "7/10"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Quick Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                QuickActionButton(
                    icon = "🎪",
                    label = "Quests",
                    onClick = onQuestClick,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                QuickActionButton(
                    icon = "🛍️",
                    label = "Shop",
                    onClick = onShopClick,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    icon: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        ),
        border = androidx.compose.material3.CardDefaults.outlinedCardBorder().copy(
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFFD946EF), Color(0xFF6200EE))
            )
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                value,
                style = MaterialTheme.typography.headlineMedium,
                color = SparkGold,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = TextLight.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun GameRoundCard(
    title: String,
    points: String,
    mood: String,
    time: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.labelLarge,
                    color = TextLight,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "$mood • $time",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextLight.copy(alpha = 0.7f)
                )
            }
            Box(
                modifier = Modifier
                    .background(
                        color = SparkGold.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    points,
                    style = MaterialTheme.typography.labelMedium,
                    color = SparkGold,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MilestoneCard(
    name: String,
    description: String,
    isUnlocked: Boolean,
    progress: String? = null,
    unlockedDate: String? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked)
                Color(0xFFFFD700).copy(alpha = 0.15f)
            else
                Color.White.copy(alpha = 0.08f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    if (isUnlocked) "🏆" else "🔒",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        name,
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isUnlocked) SparkGold else TextLight,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        description,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextLight.copy(alpha = 0.7f)
                    )
                }
            }

            if (!isUnlocked && progress != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    progress,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextLight.copy(alpha = 0.8f)
                )
            }

            if (isUnlocked && unlockedDate != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Unlocked: $unlockedDate",
                    style = MaterialTheme.typography.labelSmall,
                    color = SparkGold
                )
            }
        }
    }
}

@Composable
fun QuickActionButton(
    icon: String,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .androidx.compose.foundation.clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(icon, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                label,
                style = MaterialTheme.typography.labelMedium,
                color = TextLight,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
