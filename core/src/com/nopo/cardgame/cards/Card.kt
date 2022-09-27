package com.nopo.cardgame.cards

import com.badlogic.gdx.graphics.Texture

class Card(var name: String, var damage: Int, var health: Int, var cost: Int, var texture: Texture) {

    fun canBeReplaced(card : Card): Boolean {
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