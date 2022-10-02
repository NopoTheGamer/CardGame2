package com.nopo.cardgame.cards

import com.badlogic.gdx.graphics.Texture
import com.nopo.cardgame.GameField

class WaterCard(name: String, baseDamage: Int, baseHealth: Int, cost: Int) : Card(name, baseDamage, baseHealth, cost) {
    override var texture: Texture = Texture("cards/water_card.png")
    override var whereCanBePlaced = GameField.LaneTypes.WATER

    override fun onTurnStart() {
        this.damageModifier += 1
    }
}