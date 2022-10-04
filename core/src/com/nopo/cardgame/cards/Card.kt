package com.nopo.cardgame.cards

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.nopo.cardgame.GameField

open class Card @JvmOverloads constructor(
    var name: String,
    val baseDamage: Int,
    val baseHealth: Int,
    var baseCost: Int,
    var abilities: MutableList<Ability> = mutableListOf(),
) {

    enum class Ability {
        DOUBLE_STRIKE, POISON, FRENZY, STRIKETHROUGH, DEADLY, VAMP;

        private var value: Float = 1f
        fun setValue(value: Float): Ability {
            this.value = value
            return this
        }

        fun getValue(): Float {
            return this.value
        }
    }

    open var texture: Texture = Texture(Gdx.files.internal("cards/default_card.png"))
    open var whereCanBePlaced = GameField.LaneTypes.NORMAL
    var rectangle: Rectangle = Rectangle(-1f, -1f, 64f, 64f)
    var lane = -1
    var location: GameField.Type? = null
    var isHeld = false;
    var damageModifier = 0
    var currentDamage = 0
    var healthModifier = 0
    var currentHealth = 0
    var costModifier = 0
    var currentCost = 0

    /**
     * Always call before using the cards health values
     */
    fun workOutHealth() {
        currentHealth = baseHealth + healthModifier
    }

    /**
     * Always call before using the cards damage values
     */
    fun workOutDamage() {
        currentDamage = baseDamage + damageModifier
    }

    /**
     * Always call before using the cards cost values
     */
    fun workOutCost() {
        currentCost = baseCost + costModifier
    }

    open fun canBeReplaced(card: Card): Boolean {
        card.isHeld
        return false //TODO: implement
    }

    /**
     * Called when damaging another card
     */
    open fun attack(otherCard: Card) {
        otherCard.healthModifier -= if (this.abilities.contains(Ability.DEADLY)) otherCard.currentHealth else this.currentDamage
        if (this.abilities.contains(Ability.VAMP)) {
            this.healthModifier += (this.currentDamage * this.abilities.find { it == Ability.VAMP }!!
                .getValue()).toInt()
        }
        if (this.abilities.contains(Ability.STRIKETHROUGH)) {
            GameField.attackOther(this, this.location!!.getOtherSide())
        }
    }

    /**
     * Called when damaging a player
     */
    open fun attack(): Int {
        return this.currentDamage
    }

    open fun onPlacement() {

    }

    open fun onAttack() {

    }

    open fun onKill() {
        if (abilities.contains(Ability.FRENZY)) {
            GameField.attack(this.lane, this)
        }

    }

    open fun onDeath() {
        // Put code that runs on card hp = 0 here
    }

    open fun onCombatStart() {

    }

    open fun onCombatEnd() {

    }

    open fun onTurnStart() {

    }

    open fun onTurnEnd() {

    }

    open fun onNewTurnDeck() {

    }
}