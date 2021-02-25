package com.example.punchbuggy

import android.util.Log

class Player constructor(val username: String){

    private val punchBuggies: HashMap<String, PunchBuggy?> = HashMap(10)
    private var totalScore = 0

    init {
        initializePunchBuggies()
    }

    private fun initializePunchBuggies() {
        Vehicle.colors.forEach {
            val buggy = PunchBuggy(it)
            punchBuggies[it] = buggy
        }
    }

    fun getScoreForColor(color: String): Int? {
        return punchBuggies[color]?.getCount()
    }

    fun getCurrentPunchBuggy(color: String): PunchBuggy? {
        return punchBuggies[color]
    }

    fun addPoint(color: String) {
        totalScore++
        getCurrentPunchBuggy(color)?.incrementCount()
    }

    fun removePoint(color: String) {
        if (getCurrentPunchBuggy(color)!!.getCount() > 0) {
            getCurrentPunchBuggy(color)?.decrementCount()
            totalScore--
        } else {
            Log.e("ScoreError", "Error: score is already at 0!")
        }
    }

    fun getTotalScore(): Int {
        return totalScore
    }

    fun displayTotalScore() {
        println("TOTAL SCORE: $totalScore")
        for ((color, vehicle) in punchBuggies) {
            Log.d("Player","${color}: ${vehicle?.getCount()}")
        }
    }

}