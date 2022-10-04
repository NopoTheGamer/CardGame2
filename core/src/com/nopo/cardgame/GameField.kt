package com.nopo.cardgame

import com.nopo.cardgame.cards.Card
import com.nopo.cardgame.cards.ExamplePlacementCard

object GameField {
    private val yourCards = ArrayList(listOf(*arrayOfNulls<Card>(5)))
    private val theirCards = ArrayList(listOf(*arrayOfNulls<Card>(5)))
    private val environments = ArrayList(listOf(*arrayOfNulls<Card>(5)))
    private val deck = ArrayList(listOf(*arrayOfNulls<Card>(10)))
    private val cardPile = ArrayList(listOf(*arrayOfNulls<Card>(40)))
    private var laneTypes: ArrayList<LaneTypes> = ArrayList()
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

    enum class LaneTypes {
        NORMAL, WATER, WATER_ONLY, HEIGHTS
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
    fun canPlaceOnLaneType(cardLane: LaneTypes, lane: Int): Boolean {
        if (lane < 0 || lane > 4) throw IndexOutOfBoundsException("Lane must be between 0 and 4")
        val laneType = laneTypes[lane]
        if (cardLane == laneType) return true
        if (cardLane == LaneTypes.WATER) return true
        if (cardLane == LaneTypes.WATER_ONLY && laneType == LaneTypes.WATER) return true
        if (laneType == LaneTypes.HEIGHTS && cardLane == LaneTypes.NORMAL) return true
        if (laneType == LaneTypes.NORMAL && cardLane == LaneTypes.HEIGHTS) return true
        return false
    }

    @JvmStatic
    fun canPlaceCard(lane: Int, card: Card?, type: Type): Boolean {
        if (lane < 0 || lane > 4) throw IndexOutOfBoundsException("Lane must be between 0 and 4")
        if (card == null) return true // ?
        if (canPlaceOnLaneType(card.whereCanBePlaced, lane)) {
            return getField(type)[lane] == null || getField(type)[lane]?.canBeReplaced(card) ?: false
        } else return false
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
        card.workOutCost()
        if (canPlaceCard(lane, card, type) && (card.currentCost <= energy)) {
            getField(Type.DECK).remove(card)
            getField(Type.DECK).add(null)
            energy -= card.currentCost
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
        val doubleStrike = if (card.abilities.contains(Card.Ability.DOUBLE_STRIKE)) 2 else 1
        for (x in 1..doubleStrike) {
            val otherSide = card.location?.getOtherSide() ?: throw IllegalStateException("Card is not in field")
            val otherCard = getCard(lane, otherSide)

            card.workOutDamage()
            otherCard?.workOutHealth()

            if (otherCard != null && otherCard.currentHealth > 0) {
                card.attack(otherCard)
                otherCard.workOutHealth()
                if (otherCard.currentHealth <= 0) {
                    killCard(lane, otherSide)
                    card.onKill()
                }
            } else {
                attackOther(card, otherSide)
            }

        }
    }

    @JvmStatic
    fun attackOther/*person? idk*/(card: Card, location: Type) {
        if (location == Type.THEIR_CARDS) {
            theirHealth -= card.attack()
            if (theirHealth < 0) theirHealth = 0
        } else {
            yourHealth -= card.attack()
            if (yourHealth < 0) yourHealth = 0
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
        for (card in getField(Type.DECK)) {
            card?.onNewTurnDeck()
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
            val card = Card("card $i", 3, 3, 3)
            val cardCool = ExamplePlacementCard("card $i", 1, 3, 5, mutableListOf(Card.Ability.DOUBLE_STRIKE, Card.Ability.DEADLY))
            if (i < 20) getField(Type.CARD_PILE)[i] = card
            else getField(Type.CARD_PILE)[i] = cardCool
        }
        getField(Type.CARD_PILE).shuffle()
    }

    @JvmStatic
    fun setLanes(laneType: ArrayList<LaneTypes>) {
        if (laneType.size != 5) throw IndexOutOfBoundsException("LaneType must be 5 long")
        laneTypes = laneType
    }
}