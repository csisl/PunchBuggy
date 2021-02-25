package com.example.punchbuggy

/**
 * An abstract class to manage vehicles
 *
 * @param color: the color of the vehicle to instantiate
 */
abstract class Vehicle constructor(color: String) {
    abstract val color: String
    private var count: Int = 0

    /** An array of strings holding all valid colors for a vehicle */
    companion object Colors {
        val colors: Array<String> = arrayOf("red", "orange", "yellow", "green", "blue",
            "purple", "pink", "black", "white", "silver")
    }

    open fun incrementCount() {
        count++
    }

    open fun decrementCount() {
        if (count > 0) {
            count--
        }
    }

    /** @return the count for the current vehicle */
    fun getCount(): Int = count
}