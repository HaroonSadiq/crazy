package com.echospark.presentation.screens.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInUp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.echospark.presentation.components.PartyButton
import com.echospark.presentation.components.QuestionCard
import com.echospark.ui.theme.GradientEnd
import com.echospark.ui.theme.GradientStart
import com.echospark.ui.theme.SparkGold
import com.echospark.ui.theme.TextLight

@Composable
fun GameScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var gameState by remember { mutableStateOf(GameState.PARTNER_A_ANSWERING) }
    var partnerAAnswer by remember { mutableStateOf("") }
    var partnerBGuess by remember { mutableStateOf("") }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }

    val question = "What's one travel destination that would make you feel closest to me?"

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
            // Header
            Text(
                when (gameState) {
                    GameState.PARTNER_A_ANSWERING -> "💭 Partner A, answer honestly"
                    GameState.PARTNER_B_GUESSING -> "🎯 Partner B, make your guess"
                    GameState.SHOWING_RESULTS -> "🎉 Let's see how well you know each other!"
                },
                style = MaterialTheme.typography.headlineSmall,
                color = TextLight,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Question Card
            QuestionCard(
                question = question,
                category = "Travel & Dreams",
                intensity = "Deep"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // State-based content
            when (gameState) {
                GameState.PARTNER_A_ANSWERING -> {
                    PartnerAAnswerSection(
                        answer = partnerAAnswer,
                        onAnswerChange = { partnerAAnswer = it },
                        onSubmit = { gameState = GameState.PARTNER_B_GUESSING }
                    )
                }

                GameState.PARTNER_B_GUESSING -> {
                    PartnerBGuessSection(
                        guess = partnerBGuess,
                        onGuessChange = { partnerBGuess = it },
                        onSubmit = {
                            isCorrect = partnerBGuess.lowercase().trim() == partnerAAnswer.lowercase().trim()
                            gameState = GameState.SHOWING_RESULTS
                        }
                    )
                }

                GameState.SHOWING_RESULTS -> {
                    ResultsSection(
                        isCorrect = isCorrect ?: false,
                        actualAnswer = partnerAAnswer,
                        guessedAnswer = partnerBGuess,
                        onContinue = {
                            gameState = GameState.PARTNER_A_ANSWERING
                            partnerAAnswer = ""
                            partnerBGuess = ""
                            isCorrect = null
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun PartnerAAnswerSection(
    answer: String,
    onAnswerChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            "Your honest answer:",
            style = MaterialTheme.typography.labelMedium,
            color = TextLight,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        TextField(
            value = answer,
            onValueChange = onAnswerChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(alpha = 0.95f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = GradientStart
            ),
            placeholder = {
                Text(
                    "Write your thoughtful answer here...",
                    color = Color.Gray
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(20.dp))

        PartyButton(
            text = "✌️ Hand Over to Partner B →",
            onClick = onSubmit,
            enabled = answer.isNotBlank()
        )
    }
}

@Composable
fun PartnerBGuessSection(
    guess: String,
    onGuessChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            "What do you think they said?",
            style = MaterialTheme.typography.labelMedium,
            color = TextLight,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        TextField(
            value = guess,
            onValueChange = onGuessChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(alpha = 0.95f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = GradientStart
            ),
            placeholder = {
                Text(
                    "Make your guess...",
                    color = Color.Gray
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(20.dp))

        PartyButton(
            text = "🎯 Submit Guess",
            onClick = onSubmit,
            enabled = guess.isNotBlank()
        )
    }
}

@Composable
fun ResultsSection(
    isCorrect: Boolean,
    actualAnswer: String,
    guessedAnswer: String,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(targetValue = 1f, label = "resultScale")

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Result Celebration/Learning
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = if (isCorrect)
                        Brush.linearGradient(
                            colors = listOf(Color(0xFFFFD700), Color(0xFFFFA500))
                        )
                    else
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF6200EE), Color(0xFFD946EF))
                        )
                )
                .padding(24.dp)
                .scale(scale),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    if (isCorrect) "🎉 Perfect Match!" else "💡 Learning Moment",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TextLight,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    if (isCorrect)
                        "You know each other so well!"
                    else
                        "No worries, you'll get the next one!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextLight.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .background(
                            color = TextLight.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        if (isCorrect) "+50 ✨" else "+10 ✨",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = TextLight
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Comparison
        Column {
            Text(
                "Comparison:",
                style = MaterialTheme.typography.labelMedium,
                color = TextLight,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.1f))
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        "Their answer:",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextLight.copy(alpha = 0.8f)
                    )
                    Text(
                        actualAnswer,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextLight,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Your guess:",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextLight.copy(alpha = 0.8f)
                    )
                    Text(
                        guessedAnswer,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isCorrect) SparkGold else Color(0xFFFF6B6B),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        PartyButton(
            text = "🔄 Play Again",
            onClick = onContinue
        )
    }
}

enum class GameState {
    PARTNER_A_ANSWERING,
    PARTNER_B_GUESSING,
    SHOWING_RESULTS
}
