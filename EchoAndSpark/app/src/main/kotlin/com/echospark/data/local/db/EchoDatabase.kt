package com.echospark.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.echospark.domain.model.Question
import com.echospark.domain.model.UserResponse
import com.echospark.domain.model.SparkPoints
import com.echospark.domain.model.SparkBalance
import com.echospark.domain.model.Milestone

@Database(
    entities = [
        Question::class,
        UserResponse::class,
        SparkPoints::class,
        SparkBalance::class,
        Milestone::class
    ],
    version = 1,
    exportSchema = false
)
abstract class EchoDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun responseDao(): ResponseDao
    abstract fun sparkPointsDao(): SparkPointsDao
    abstract fun sparkBalanceDao(): SparkBalanceDao
    abstract fun milestoneDao(): MilestoneDao
}
