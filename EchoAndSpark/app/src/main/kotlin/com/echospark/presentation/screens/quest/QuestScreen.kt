package com.echospark.presentation.screens.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.echospark.ui.theme.SparkGold
import com.echospark.ui.theme.TextLight

@Composable
fun QuestScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeQuelst by remember {
        mutableStateOf(
            listOf(
                Quest(
                    "10-Day Streak",
                    "Play at least one round every day for 10 days",
                    7,
                    10,
                    300,
                    true
                ),
                Quest(
                    "Perfect Week",
                    "Get 7 perfect matches in one week",
                    3,
                    7,
                    500,
                    true
                ),
                Quest(
                    "Exploration Master",
                    "explore all 4 mood categories",
                    2,
                    4,
                    250,
                    true
                )
            )
        )
    }

    val completedQuests by remember {
        mutableStateOf(
            listOf(
                Quest("First Connection", "Complete your first round", 1, 1, 100, false),
                Quest("5 Perfect Matches", "Get 5 correct guesses in total", 5, 5, 200, false),
                Quest("The Beginning", "Your first spark earned", 50, 50, 75, false)
            )
        )
    }

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
            Text(
                "Couples Quests 🎪",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                color = TextLight,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Active Quests
            Text(
                "Active Challenges",
                style = MaterialTheme.typography.labelLarge,
                color = TextLight,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            activeQuelst.forEach { quest ->
                ActiveQuestCard(quest = quest)
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Completed Quests
            Text(
                "Completed (${completedQuests.size})",
                style = MaterialTheme.typography.labelLarge,
                color = SparkGold,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            completedQuests.forEach { quest ->
                CompletedQuestCard(quest = quest)
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

data class Quest(
    val title: String,
    val description: String,
    val progress: Int,
    val target: Int,
    val rewardSpark: Int,
    val isActive: Boolean
)

@Composable
fun ActiveQuestCard(
    quest: Quest,
    modifier: Modifier = Modifier
) {
    val progressPercent = (quest.progress.toFloat() / quest.target.toFloat() * 100).toInt()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        quest.title,
                        style = MaterialTheme.typography.labelLarge,
                        color = TextLight,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        quest.description,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextLight.copy(alpha = 0.7f)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            color = SparkGold.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        "+${quest.rewardSpark}✨",
                        style = MaterialTheme.typography.labelSmall,
                        color = SparkGold,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White.copy(alpha = 0.1f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressPercent / 100f)
                        .height(8.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFF6200EE), Color(0xFFD946EF))
                            )
                        )
                        .clip(RoundedCornerShape(4.dp))
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                "${quest.progress}/${quest.target}",
                style = MaterialTheme.typography.labelSmall,
                color = TextLight.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun CompletedQuestCard(
    quest: Quest,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = SparkGold.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "✅",
                fontSize = 24.sp,
                modifier = Modifier.padding(end = 12.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    quest.title,
                    style = MaterialTheme.typography.labelMedium,
                    color = SparkGold,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    quest.description,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextLight.copy(alpha = 0.6f)
                )
            }
            Text(
                "+${quest.rewardSpark}✨",
                style = MaterialTheme.typography.labelSmall,
                color = SparkGold,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
