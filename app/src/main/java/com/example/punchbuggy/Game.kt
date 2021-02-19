package com.example.punchbuggy

import android.util.Log

class Game {

    private var players: MutableList<Player> = mutableListOf()

    fun getPlayers(): MutableList<Player> {
        return players
    }

    fun updatePlayer(player: Player) {
        removePlayer(player)
        addPlayer(player)
    }

    fun getPlayer(name: String): Player {
        for (player in players) {
            if (name == player.username) {
                return player
            }
        }
        TODO()
    }

    fun hasPlayer(username: String): Boolean {
        var inGame = false
        for (name: String in getPlayerNames()) {
            if (name == username) {
                inGame = true
                break
            }
        }
        return inGame
    }

    fun sortPlayers() {
        players.sortByDescending { it.getTotalScore() }
    }

    fun getPlayerNames(): MutableList<String> {
        sortPlayers()
        val playerNames: MutableList<String> = mutableListOf()
        for (player in players) {
            playerNames.add(player.username)
        }
        return playerNames
    }

    fun addPlayer(player: Player) {
        players.add(player)
    }

    fun removePlayer(player: Player) {
        for (i in players.indices) {
            if (players[i].username == player.username) {
                Log.d("removePlayer", "Removing player at index $i")
                players.removeAt(i)
                break
            }
        }
    }

    fun removePlayer(playerName: String) {
        for (i in players.indices) {
            if (players[i].username == playerName) {
                players.removeAt(i)
                break
            }
        }
    }

    fun incrementScore(player: Player, color: String) {
        player.addPoint(color)
    }

    fun decrementScore(player: Player, color: String) {
        player.removePoint(color)
    }

    fun displayScore() {
        players.forEach {
            println("*-------${it.username}---------*")
            println("${it.displayTotalScore()}")
            println("*------------------------------------*")
        }
    }

}