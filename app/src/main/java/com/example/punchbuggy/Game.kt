package com.example.punchbuggy

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

    fun getPlayerNames(): MutableList<String> {
        val playerNames: MutableList<String> = mutableListOf()
        for (player: Player in players) {
            playerNames.add(player.username)
        }
        return playerNames
    }

    fun addPlayer(player: Player) {
        players.add(player)
    }

    fun removePlayer(player: Player) {
        println("removing player: ${player.username}")
        for (i in players.indices) {
            if (players[i].username == player.username) {
                players.removeAt(i)
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