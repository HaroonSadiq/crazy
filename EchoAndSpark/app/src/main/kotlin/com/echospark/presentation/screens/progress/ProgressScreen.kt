package com.echospark.presentation.screens.progress

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
fun ProgressScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val stats by remember {
        mutableStateOf(
            mapOf(
                "Questions Answered" to "178",
                "Games Completed" to "47",
                "Perfect Matches" to "23",
                "Learning Moments" to "24",
                "Day Streak" to "12 days",
                "Milestones Unlocked" to "8/15"
            )
        )
    }

    val milestoneHistory by remember {
        mutableStateOf(
            listOf(
                MilestoneItem("🏆 First Connection", "April 1, 2026"),
                MilestoneItem("🎯 5 Perfect Matches", "April 3, 2026"),
                MilestoneItem("🔥 7-Day Streak", "April 7, 2026"),
                MilestoneItem("⭐ 50 Questions Answered", "April 5, 2026"),
                MilestoneItem("💎 50 Spark Earned", "April 2, 2026")
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
                "Your Progress 📊",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                color = TextLight,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Stats Grid
            Text(
                "Statistics",
                style = MaterialTheme.typography.labelLarge,
                color = TextLight,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            stats.forEach { (label, value) ->
                StatRow(label = label, value = value)
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Milestones Timeline
            Text(
                "Milestones Timeline 🏆",
                style = MaterialTheme.typography.labelLarge,
                color = SparkGold,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            milestoneHistory.forEachIndexed { index, milestone ->
                MilestoneTimelineItem(
                    milestone = milestone,
                    isLast = index == milestoneHistory.size - 1
                )
                if (index < milestoneHistory.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

data class MilestoneItem(
    val title: String,
    val date: String
)

@Composable
fun StatRow(
    label: String,
    value: String,
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            Text(
                label,
                style = MaterialTheme.typography.labelMedium,
                color = TextLight,
                fontWeight = FontWeight.SemiBold
            )

            Box(
                modifier = Modifier
                    .background(
                        color = SparkGold.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    value,
                    style = MaterialTheme.typography.labelMedium,
                    color = SparkGold,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MilestoneTimelineItem(
    milestone: MilestoneItem,
    isLast: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        // Timeline dot and line
        Column(
            modifier = Modifier
                .width(32.dp)
                .height(if (isLast) 32.dp else 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        color = SparkGold,
                        shape = RoundedCornerShape(50)
                    )
            )

            if (!isLast) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(64.dp)
                        .background(color = SparkGold.copy(alpha = 0.3f))
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Milestone content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 2.dp)
        ) {
            Text(
                milestone.title,
                style = MaterialTheme.typography.labelMedium,
                color = TextLight,
                fontWeight = FontWeight.Bold
            )
            Text(
                milestone.date,
                style = MaterialTheme.typography.labelSmall,
                color = SparkGold.copy(alpha = 0.8f)
            )
        }
    }
}
