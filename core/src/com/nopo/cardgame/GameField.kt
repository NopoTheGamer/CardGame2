package com.nopo.cardgame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.nopo.cardgame.cards.Card
import com.nopo.cardgame.cards.ExamplePlacementCard

object GameField {
    private val yourCards = ArrayList(listOf(*arrayOfNulls<Card>(5)))
    private val theirCards = ArrayList(listOf(*arrayOfNulls<Card>(5)))
    private val environments = ArrayList(listOf(*arrayOfNulls<Card>(5)))
    private val deck = ArrayList(listOf(*arrayOfNulls<Card>(10)))
    private val cardPile = ArrayList(listOf(*arrayOfNulls<Card>(40)))
    var energy = 1
    var turn = 1
    var yourHealth = 30
    var theirHealth = 30

    enum class Type {
        YOUR_CARDS, THEIR_CARDS, ENVIRONMENTS, DECK, CARD_PILE;

        fun getOtherSide(): Type {
            return when (this) {
                YOUR_CARDS -> THEIR_CARDS
                THEIR_CARDS -> YOUR_CARDS
                else -> throw IllegalArgumentException("Can't get other side of $this")
            }
        }
    }

    private fun getField(type: Type): ArrayList<Card?> {
        return when (type) {
            Type.YOUR_CARDS -> yourCards
            Type.THEIR_CARDS -> theirCards
            Type.ENVIRONMENTS -> environments
            Type.DECK -> deck
            Type.CARD_PILE -> cardPile
            //else -> throw IllegalArgumentException("Invalid type: $type")
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
            if (getField(type)[lane]?.location != null) throw IllegalStateException("Card is in wrong field")
            null
        }
    }

    @JvmStatic
    fun canPlaceCard(lane: Int, card: Card?, type: Type): Boolean {
        if (lane < 0 || lane > 4) throw IndexOutOfBoundsException("Lane must be between 0 and 4")
        if (card == null) return true // ?
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
            getField(type)[lane] = card
            card.onPlacement()
        }
    }

    @JvmStatic
    fun playCard(lane: Int, card: Card, type: Type): Boolean {
        if (card.location != Type.DECK) throw IllegalStateException("Card is not in deck, use placeCard instead")
        if (canPlaceCard(lane, card, type) && (card.cost <= energy)) {
            getField(Type.DECK).remove(card)
            getField(Type.DECK).add(null)
            energy -= card.cost
            placeCard(lane, card, type)
            return true
        }
        return false
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
    fun canAddToDeck(): Int {
        for (i in 0..9) {
            getField(Type.DECK)[i] ?: return i
        }
        return -1
    }


    @JvmStatic
    fun addToDeck(card: Card): Boolean {
        return if (canAddToDeck() != -1) {
            card.location = Type.DECK
            getField(Type.DECK)[canAddToDeck()] = card
            true
        } else false
    }

    @JvmStatic
    fun startCombat() {
        for (card in getField(Type.THEIR_CARDS)) {
            card?.onCombatStart()
        }
        for (card in getField(Type.YOUR_CARDS)) {
            card?.onCombatStart()
        }
    }

    @JvmStatic
    fun combat() {
        for (i in 0..4) {
            val theirCard = getCard(i, Type.THEIR_CARDS)
            val yourCard = getCard(i, Type.YOUR_CARDS)

            theirCard?.onTurnStart()
            yourCard?.onTurnStart()

            theirCard?.onAttack()
            attack(i, theirCard)

            yourCard?.onAttack()
            attack(i, yourCard)

            theirCard?.onTurnEnd()
            yourCard?.onTurnEnd()
        }
    }

    @JvmStatic
    fun attack(lane: Int, card: Card?) {
        card ?: return
        val otherSide = card.location?.getOtherSide() ?: throw IllegalStateException("Card is not in field")
        val otherCard = getCard(lane, otherSide)
        if (otherCard != null) {
            otherCard.health -= card.damage
            if (otherCard.health <= 0) killCard(lane, otherSide)
        } else {
            if (otherSide == Type.THEIR_CARDS) {
                theirHealth -= card.damage
                if (theirHealth < 0) theirHealth = 0
            } else {
                yourHealth -= card.damage
                if (yourHealth < 0) yourHealth = 0
            }
        }
    }

    @JvmStatic
    fun nextTurn() {
        for (card in getField(Type.THEIR_CARDS)) {
            card?.onCombatEnd()
        }
        for (card in getField(Type.YOUR_CARDS)) {
            card?.onCombatEnd()
        }
        turn++
        energy = turn
        drawCard()
    }

    @JvmStatic
    fun drawCard() {
        val pile = getField(Type.CARD_PILE)
        for (card in pile) {
            if (card != null) {
                if (addToDeck(card)) {
                    pile.remove(card)
                    pile.add(null)
                }
                return
            }
        }
        throw IndexOutOfBoundsException("pile is empty")
    }

    @JvmStatic
    fun createPile() {
        for (i in 0..39) {
            val card = Card("card $i", 3, 3, 3, Texture(Gdx.files.internal("player.png")))
            val cardCool = ExamplePlacementCard("card $i", 3, 3, 5, Texture(Gdx.files.internal("player.png")))
            if (i < 20) getField(Type.CARD_PILE)[i] = card
            else getField(Type.CARD_PILE)[i] = cardCool
        }
        getField(Type.CARD_PILE).shuffle()
    }
}