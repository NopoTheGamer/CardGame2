package com.nopo.cardgame.cards

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.nopo.cardgame.GameField

open class Card(var name: String, var damage: Int, var health: Int, var cost: Int) {

    open var texture: Texture = Texture(Gdx.files.internal("cards/default_card.png"))
    var rectangle: Rectangle = Rectangle(-1f, -1f, 64f, 64f)
    var lane = -1
    var location: GameField.Type? = null
    var isHeld = false;
    open var whereCanBePlaced = GameField.LaneTypes.NORMAL

    open fun canBeReplaced(card: Card): Boolean {
        card.isHeld
        return false //TODO: implement
    }

    open fun onPlacement() {

    }

    open fun onAttack() {

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
}