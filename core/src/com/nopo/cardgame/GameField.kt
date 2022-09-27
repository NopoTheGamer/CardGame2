package com.nopo.cardgame

import com.badlogic.gdx.Gdx
import com.nopo.cardgame.cards.Card
import java.util.*

object GameField {
    private val yourCards = ArrayList(listOf(*arrayOfNulls<Card>(5)))
    private val theirCards = ArrayList(listOf(*arrayOfNulls<Card>(5)))
    private val environments = ArrayList(listOf(*arrayOfNulls<Card>(5)))
    private val deck = ArrayList(listOf(*arrayOfNulls<Card>(10)))

    enum class Type {
        YOUR_CARDS, THEIR_CARDS, ENVIRONMENTS, DECK
    }

    private fun getField(type: Type): ArrayList<Card?> {
        return when (type) {
            Type.YOUR_CARDS -> yourCards
            Type.THEIR_CARDS -> theirCards
            Type.ENVIRONMENTS -> environments
            Type.DECK -> deck
            else -> throw IllegalArgumentException("Invalid type: $type")
        }
    }

    @JvmStatic
    fun getCard(lane: Int, type: Type): Card? {
        if (lane < 0) throw IndexOutOfBoundsException("Lane can't be under 0")
        if (lane > 4 && type != Type.DECK) throw IndexOutOfBoundsException("Lane can't be over 4")
        if (lane > 9 && type == Type.DECK) throw IndexOutOfBoundsException("Deck can't be over 9")
        return if (getField(type)[lane]?.location == type) {
            getField(type)[lane]
        } else {
            println("${getField(type)[lane]?.location} + $type + $lane") //TODO: fix this
            null
        }
    }

    @JvmStatic
    fun canPlaceCard(lane: Int, card: Card?, type: Type): Boolean {
        if (lane < 0 || lane > 4) throw IndexOutOfBoundsException("Lane must be between 0 and 4")
        if (card == null) return true
        return getField(type)[lane] == null || getField(type)[lane]?.canBeReplaced(card) ?: false
    }

    @JvmStatic
    fun setCard(lane: Int, card: Card, type: Type) {
        if (canPlaceCard(lane, card, type)) getField(type)[lane] = card
    }

    @JvmStatic
    fun placeCard(lane: Int, card: Card, type: Type) {
        if (canPlaceCard(lane, card, type)) {
            card.lane = lane
            card.location = type
            card.onPlacement()
            getField(type)[lane] = card
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
        getField(type)[lane]?.onDeath() ?: return
        getField(type)[lane] = null
    }


    @JvmStatic
    fun addToDeck(card : Card) {
        for (i in 0..9) {
            val cards = deck[i]
            if (cards == null) {
                card.location = Type.DECK
                deck[i] = card
                return
            }
        }
        throw IndexOutOfBoundsException("Deck is full")
    }
}