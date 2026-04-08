package com.echospark.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey val id: Int,
    val text: String,
    val category: String,
    val intensity: String, // "Chill", "Deep", "Spicy"
    val isPremium: Boolean = false
)

@Entity(tableName = "user_responses")
data class UserResponse(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionId: Int,
    val partnerAResponse: String,
    val partnerBGuess: String,
    val isCorrect: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "spark_points")
data class SparkPoints(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val points: Int,
    val type: String, // "correct_guess", "attempt", "purchase", "milestone"
    val description: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "spark_balance")
data class SparkBalance(
    @PrimaryKey val id: Int = 1,
    val totalPoints: Int = 0
)

@Entity(tableName = "milestones")
data class Milestone(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val requiredGames: Int,
    val unlockedAt: Long? = null,
    val isUnlocked: Boolean = false
)
