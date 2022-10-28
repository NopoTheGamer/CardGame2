package com.nopo.cardgame.ai


var chances = ArrayList(listOf(0.2f, 0.2f, 0.2f, 0.2f, 0.2f))

    fun modifyChance(lane: Int, newChance: Float) {
        if (newChance > 1) throw IndexOutOfBoundsException("chance is above 1: $newChance")
        chances[lane] = newChance
        val stuff = (1-newChance)/4
        setOthers(lane, stuff)
    }

    fun setOthers(besides: Int, newChance: Float) {
        println(chances)
        for (i in 0..4) {
            if (i == besides) continue
            chances[i] = newChance
        }
        println(chances)
    }
