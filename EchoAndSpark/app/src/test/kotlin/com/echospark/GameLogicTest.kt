package com.echospark

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GameLogicTest {

    @Test
    fun testCorrectGuessAwards50Points() {
        val points = calculateSparkPoints(isCorrect = true)
        assertEquals(50, points)
    }

    @Test
    fun testIncorrectGuessAwards10Points() {
        val points = calculateSparkPoints(isCorrect = false)
        assertEquals(10, points)
    }

    @Test
    fun testSparkPointsArePositive() {
        val pointsCorrect = calculateSparkPoints(isCorrect = true)
        val pointsIncorrect = calculateSparkPoints(isCorrect = false)
        assertTrue(pointsCorrect > 0)
        assertTrue(pointsIncorrect > 0)
    }

    @Test
    fun testGuessComparison() {
        val actual = "Paris"
        val guess = "paris"
        val isMatch = guess.lowercase().trim() == actual.lowercase().trim()
        assertTrue(isMatch)
    }

    private fun calculateSparkPoints(isCorrect: Boolean): Int {
        return if (isCorrect) 50 else 10
    }
}
