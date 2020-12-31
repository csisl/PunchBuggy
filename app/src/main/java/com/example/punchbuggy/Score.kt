package com.example.punchbuggy

class Score {

    private var totalScore: Int = 0

    fun addPoint(color: String) {
        incrementTotalScore()
    }

    fun incrementTotalScore() {
        totalScore++
    }

    fun decrementTotalScore() {
        if (totalScore > 0) {
            totalScore--
        }
    }

    fun getTotalScore(): Int = totalScore

}