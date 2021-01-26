package com.example.punchbuggy

class Player constructor(val username: String){

    // TODO: change to a hash map of some sort
    private var punchBuggies: MutableList<PunchBuggy> = mutableListOf()
    private val score: Score = Score()

    init {
        initializePunchBuggies()
    }

    private fun initializePunchBuggies() {
        Vehicle.colors.forEach {
            val buggy = PunchBuggy(it)
            punchBuggies.add(buggy)
        }
    }

    fun getCurrentPunchBuggy(color: String): PunchBuggy {
        punchBuggies.forEach {
            if (it.color == color) {
                return it
            }
        }
        TODO("What do we return when the color doesn't match?")
    }

    fun addPoint(color: String) {
        score.addPoint(color)
        getCurrentPunchBuggy(color).incrementCount()
    }

    fun removePoint(color: String) {
        if (getCurrentPunchBuggy(color).getCount() > 0) {
            getCurrentPunchBuggy(color).decrementCount()
            score.decrementTotalScore()
        } else {
            println("Error: score is already at 0!")
        }
    }

    fun displayTotalScore() {
        val totalScore = score.getTotalScore()
        println("TOTAL SCORE: $totalScore")
        punchBuggies.forEach {
            println("${it.color}: ${it.getCount()}")
        }
    }

}