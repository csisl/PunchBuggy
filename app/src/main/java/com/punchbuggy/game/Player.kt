package com.punchbuggy.game

/**
 * A `Player` in the game that has their own score for vehicles.
 *
 * @param    username: The name of the user
 * @property punchBuggies: A hash map mapping a color of a vehicle
 *           to the instance of that vehicle.
 */
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

    /**
     * @param color: the color of the punch buggy to get the score for
     * @return: the current count for the provided color of a vehicle
     */
    fun getScoreForColor(color: String): Int? = punchBuggies[color]?.getCount()

    /**
     * @param color: the color of the punch buggy to grab the instance for
     * @return: the punch buggy object for hte provided color
     */
    fun getCurrentPunchBuggy(color: String): PunchBuggy? = punchBuggies[color]

    /** @param color: the color of the punch buggy to increment the count for */
    fun addPoint(color: String) {
        totalScore++
        getCurrentPunchBuggy(color)?.incrementCount()
    }

    /** @param color: the color of the punch buggy to decrement the count for */
    fun removePoint(color: String) {
        if (getCurrentPunchBuggy(color)!!.getCount() > 0) {
            getCurrentPunchBuggy(color)?.decrementCount()
            totalScore--
        }
    }

    /** @return the total score for the current player */
    fun getTotalScore(): Int = totalScore

}