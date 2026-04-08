package com.echospark.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.echospark.domain.model.Question
import com.echospark.domain.model.UserResponse
import com.echospark.domain.model.SparkPoints
import com.echospark.domain.model.SparkBalance
import com.echospark.domain.model.Milestone
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Insert
    suspend fun insertQuestion(question: Question)

    @Insert
    suspend fun insertQuestions(questions: List<Question>)

    @Query("SELECT * FROM questions WHERE category = :category ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuestionByCategory(category: String): Question?

    @Query("SELECT * FROM questions WHERE intensity = :intensity ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuestionByIntensity(intensity: String): Question?

    @Query("SELECT * FROM questions ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuestion(): Question?

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): Flow<List<Question>>

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getTotalQuestionCount(): Int
}

@Dao
interface ResponseDao {
    @Insert
    suspend fun insertResponse(response: UserResponse)

    @Query("SELECT * FROM user_responses ORDER BY timestamp DESC")
    fun getAllResponses(): Flow<List<UserResponse>>

    @Query("SELECT * FROM user_responses ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentResponses(limit: Int): List<UserResponse>

    @Query("SELECT COUNT(*) FROM user_responses WHERE isCorrect = 1")
    suspend fun getCorrectGuessCount(): Int

    @Query("SELECT COUNT(*) FROM user_responses")
    suspend fun getTotalGameRounds(): Int
}

@Dao
interface SparkPointsDao {
    @Insert
    suspend fun insertSparkTransaction(transaction: SparkPoints)

    @Query("SELECT SUM(points) FROM spark_points")
    fun getTotalSparkPoints(): Flow<Int>

    @Query("SELECT * FROM spark_points ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentTransactions(limit: Int): List<SparkPoints>

    @Query("SELECT * FROM spark_points ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<SparkPoints>>
}

@Dao
interface SparkBalanceDao {
    @Insert
    suspend fun initializeBalance()

    @Query("SELECT totalPoints FROM spark_balance WHERE id = 1")
    fun getBalance(): Flow<Int>

    @Query("UPDATE spark_balance SET totalPoints = totalPoints + :points WHERE id = 1")
    suspend fun addPoints(points: Int)

    @Query("UPDATE spark_balance SET totalPoints = totalPoints - :points WHERE id = 1")
    suspend fun removePoints(points: Int)

    @Query("SELECT totalPoints FROM spark_balance WHERE id = 1")
    suspend fun getCurrentBalance(): Int?
}

@Dao
interface MilestoneDao {
    @Insert
    suspend fun insertMilestone(milestone: Milestone)

    @Insert
    suspend fun insertMilestones(milestones: List<Milestone>)

    @Query("SELECT * FROM milestones WHERE isUnlocked = 0 ORDER BY requiredGames ASC")
    fun getUnlockedMilestones(): Flow<List<Milestone>>

    @Query("SELECT * FROM milestones WHERE isUnlocked = 1 ORDER BY unlockedAt DESC")
    fun getCompletedMilestones(): Flow<List<Milestone>>

    @Update
    suspend fun updateMilestone(milestone: Milestone)
}
