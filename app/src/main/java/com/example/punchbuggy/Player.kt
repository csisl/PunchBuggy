package com.example.punchbuggy

class Player constructor(val username: String){

    // TODO: change to a hash map of some sort
    private var punchBuggies: MutableList<PunchBuggy> = mutableListOf()
    private var totalScore = 0

    init {
        initializePunchBuggies()
    }

    private fun initializePunchBuggies() {
        Vehicle.colors.forEach {
            val buggy = PunchBuggy(it)
            punchBuggies.add(buggy)
        }
    }

    fun getScoreForColor(color: String): Int? {
        punchBuggies.forEach {
            if (it.color == color) {
                return it.getCount()
            }
        }

        return null
    }

    fun getCurrentPunchBuggy(color: String): PunchBuggy {
        punchBuggies.forEach {
            if (it.color == color) {
                return it
            }
        }
        TODO("Change to PunchBuggy? but also change the calls to this func")
    }

    fun addPoint(color: String) {
        totalScore++
        getCurrentPunchBuggy(color).incrementCount()
    }

    fun removePoint(color: String) {
        if (getCurrentPunchBuggy(color).getCount() > 0) {
            getCurrentPunchBuggy(color).decrementCount()
            totalScore--
        } else {
            println("Error: score is already at 0!")
        }
    }

    fun getTotalScore(): Int {
        return totalScore
    }

    fun displayTotalScore() {
        println("TOTAL SCORE: $totalScore")
        punchBuggies.forEach {
            println("${it.color}: ${it.getCount()}")
        }
    }

}