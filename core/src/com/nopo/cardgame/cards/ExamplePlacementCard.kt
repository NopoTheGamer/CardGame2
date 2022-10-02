package com.nopo.cardgame.cards

import com.nopo.cardgame.GameField

class ExamplePlacementCard(name: String, baseDamage: Int, baseHealth: Int, cost: Int) : Card(name, baseDamage, baseHealth, cost) {

    override fun onPlacement() {
        GameField.moveCard(this.lane, 3, this.location!!)
    }

    override fun onTurnStart() {
        this.healthModifier += 1
    }

    override fun onCombatEnd() {
        println("combat did a end")
    }

    override fun onDeath() {
        println("i die")
    }

}