package com.nopo.cardgame.cards

import com.badlogic.gdx.graphics.Texture
import com.nopo.cardgame.GameField

class ExamplePlacementCard(name: String, damage: Int, health: Int, cost: Int) : Card(name, damage, health, cost) {

    override fun onPlacement() {
        GameField.moveCard(this.lane, 3, this.location!!)
    }

    override fun onCombatEnd() {
        println("combat did a end")
    }

    override fun onDeath() {
        println("i die")
    }

}