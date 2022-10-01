package com.nopo.cardgame.cards

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.nopo.cardgame.GameField

class Card(var name: String, var damage: Int, var health: Int, var cost: Int, var texture: Texture) {
    var rectangle: Rectangle = Rectangle(-1f,-1f, texture.width.toFloat(), texture.height.toFloat())
    var lane = -1
    var location : GameField.Type? = null
    var isHeld = false;

    fun canBeReplaced(card : Card): Boolean {
        card.isHeld
        return false //TODO: implement
    }

    fun onPlacement() {

    }

    fun onAttack() {

    }

    fun onDeath() {
        // Put code that runs on card hp = 0 here
    }

    fun onCombatStart() {

    }

    fun onCombatEnd() {

    }

    fun onTurnStart() {

    }

    fun onTurnEnd() {

    }
}