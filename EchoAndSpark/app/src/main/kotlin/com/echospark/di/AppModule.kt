package com.echospark.di

import android.content.Context
import androidx.room.Room
import com.echospark.data.local.db.EchoDatabase
import com.echospark.data.local.db.QuestionDao
import com.echospark.data.local.db.ResponseDao
import com.echospark.data.local.db.SparkPointsDao
import com.echospark.data.local.db.SparkBalanceDao
import com.echospark.data.local.db.MilestoneDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideEchoDatabase(
        @ApplicationContext context: Context
    ): EchoDatabase {
        return Room.databaseBuilder(
            context,
            EchoDatabase::class.java,
            "echo_spark_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideQuestionDao(database: EchoDatabase): QuestionDao {
        return database.questionDao()
    }

    @Singleton
    @Provides
    fun provideResponseDao(database: EchoDatabase): ResponseDao {
        return database.responseDao()
    }

    @Singleton
    @Provides
    fun provideSparkPointsDao(database: EchoDatabase): SparkPointsDao {
        return database.sparkPointsDao()
    }

    @Singleton
    @Provides
    fun provideSparkBalanceDao(database: EchoDatabase): SparkBalanceDao {
        return database.sparkBalanceDao()
    }

    @Singleton
    @Provides
    fun provideMilestoneDao(database: EchoDatabase): MilestoneDao {
        return database.milestoneDao()
    }
}
