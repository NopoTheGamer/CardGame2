package com.nopo.cardgame

import com.nopo.cardgame.cards.Card
import java.util.*

object GameField {
    private val yourCards = ArrayList(listOf(*arrayOfNulls<Card>(5)))
    private val theirCards = ArrayList(listOf(*arrayOfNulls<Card>(5)))
    private val environments = ArrayList(listOf(*arrayOfNulls<Card>(5)))

    private fun getField(type: Type): ArrayList<Card?> {
        return when (type) {
            Type.YOUR_CARDS -> yourCards
            Type.THEIR_CARDS -> theirCards
            Type.ENVIRONMENTS -> environments
        }
    }

    @JvmStatic
    fun getCard(lane: Int, type: Type): Card? {
        if (lane < 0 || lane > 4) throw IndexOutOfBoundsException("Lane must be between 0 and 4")
        return getField(type)[lane]
    }

    @JvmStatic
    fun canPlaceCard(lane: Int, card: Card?, type: Type): Boolean {
        if (lane < 0 || lane > 4) throw IndexOutOfBoundsException("Lane must be between 0 and 4")
        if (card == null) return true
        return getField(type)[lane] == null || getField(type)[lane]?.canBeReplaced(card) ?: false
    }

    @JvmStatic
    fun placeCard(lane: Int, card: Card, type: Type) {
        if (canPlaceCard(lane, card, type)) {
            getField(type)[lane] = card
            card.onPlacement()
        }
    }

    @JvmStatic
    fun moveCard(lane: Int, newLane: Int, type: Type) {
        val card = getCard(lane, type) ?: return
        if (canPlaceCard(newLane, card, type)) {
            placeCard(newLane, card, type)
            getField(type)[lane] = null
        }
    }

    @JvmStatic
    fun killCard(lane: Int, type: Type) {
        if (lane < 0 || lane > 4) throw IndexOutOfBoundsException("Lane must be between 0 and 4")
        getField(type)[lane]?.onDeath()
        getField(type)[lane] = null
    }

    enum class Type {
        YOUR_CARDS, THEIR_CARDS, ENVIRONMENTS
    }
}