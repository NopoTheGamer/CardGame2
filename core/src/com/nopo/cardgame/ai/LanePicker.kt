package com.nopo.cardgame.ai

import com.nopo.cardgame.GameField
import com.nopo.cardgame.cards.Card
import com.nopo.cardgame.utils.round

class LanePicker @JvmOverloads constructor(var deck: ArrayList<Card?>, var chances: ArrayList<Float> = ArrayList(listOf(0.2f, 0.2f, 0.2f, 0.2f, 0.2f))) {

    fun setChance(lane: Int, newChance: Float) {
        if (newChance > 1) throw IndexOutOfBoundsException("chance is above 1: $newChance")
        val oldChance = chances[lane]
        chances[lane] = newChance
        //val restOfChances = (1 - newChance) / 4
        //setOthersTheSame(lane, restOfChances)

        //println(chances)
        //println(chances.sum())
        normalize()
    }

    fun zeroCount(): Int {
        var count = 0
        for (i in 0..4) {
            if (chances[i] == 0f) count++
        }
        return count
    }

    private var decimals = 6

    fun normalize() {
        println()
        val sum = chances.sum()
        if (sum == 1f) return
        println("old: $chances: ${chances.sum()}")
        val zeroCount = zeroCount()
        val oldChance = chances
        if (sum > 1f) {
            val overflow = sum - 1
            val remove = overflow / (5 - zeroCount)
            for (i in 0..4) {
                if (chances[i] == 0f) continue

                chances[i] -= remove
                chances[i] = chances[i].round(decimals)
                chances[i] = chances[i].coerceAtLeast(0.1f)
            }
        } else {
            val underflow = 1 - sum
            val add = underflow / (5 - zeroCount)
            for (i in 0..4) {
                if (chances[i] == 0f) continue
                val oldChance = chances[i]
                chances[i] += add
                chances[i] = chances[i].round(decimals)
                chances[i] = chances[i].coerceAtMost(1f)
            }
        }
        println("new: $chances: ${chances.sum()}")
        if (chances.sum() != 1f && decimals > 1) {
            decimals--.coerceAtLeast(1)
            normalize()
        }
        decimals = 6
        if (chances.sum() < .9f) {
            chances = oldChance
        }
    }

    fun setChanceZero(lane: Int) {
        setChance(lane, 0f)
        normalize()
    }

    /*private fun removeChanceFromOthers(besides: Int, chance: Float) {
        for (i in 0..4) {
            if (i == besides) continue
            if (chances[i] == 0f) continue
            chances[i] -= chance
        }
    }

    private fun addChanceFromOthers(besides: Int, chance: Float) {
        for (i in 0..4) {
            if (i == besides) continue
            if (chances[i] == 0f) continue
            chances[i] += chance
        }
    }*/


    private fun setOthersTheSame(besides: Int, newChance: Float) {
        //This is the shitter way
        for (i in 0..4) {
            if (i == besides) continue
            chances[i] = newChance
        }
        println(chances)
    }

    fun pickLane() {
        for (i in 0..4) {
            if (deck[0] == null) return
            if (!GameField.canPlaceOnLaneType(deck[0]!!.whereCanBePlaced, i)) {
                setChanceZero(i)
            }
            if (!GameField.canPlaceCard(i, deck[0]!!, GameField.Type.THEIR_CARDS)) {
                setChanceZero(i)
            }
        }

        if (zeroCount() == 5) return

        val lane = getLane()
        GameField.playCard(lane, deck[0]!!, GameField.Type.THEIR_CARDS)
    }

    private fun getLane(): Int {
        val random = Math.random()
        var chance = 0f
        println(random)
        for (i in 0..4) {
            chance += chances[i]
            if (random < chance) {
                return i
            }
        }
        throw IllegalStateException("This shouldn't happen")
        return -1
    }
}