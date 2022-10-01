package com.nopo.cardgame.cards

import com.badlogic.gdx.graphics.Texture
import com.nopo.cardgame.GameField

class ExamplePlacementCard(name: String, damage: Int, health: Int, cost: Int, texture: Texture) : Card(name, damage, health, cost, texture) {

    override fun onPlacement() {
        GameField.moveCard(this.lane, 4, this.location!!)
    }

}