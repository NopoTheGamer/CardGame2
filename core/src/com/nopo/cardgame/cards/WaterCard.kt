package com.nopo.cardgame.cards

import com.badlogic.gdx.graphics.Texture
import com.nopo.cardgame.GameField

class WaterCard(name: String, damage: Int, health: Int, cost: Int) : Card(name, damage, health, cost) {
    override var texture: Texture = Texture("cards/water_card.png")
    override var whereCanBePlaced = GameField.LaneTypes.WATER
}