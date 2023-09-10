package com.nopo.cardgame.cards

import com.nopo.cardgame.GameField

class ExamplePlacementCard @JvmOverloads constructor(name: String, baseDamage: Int, baseHealth: Int, cost: Int, abilities: MutableList<Ability> = mutableListOf()) : Card(name, baseDamage, baseHealth, cost, abilities) {

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

    override fun showInfo(): String {
        return "This card gains 1hp at the end of every turn, costs 5 mana, has 1 attack and 3 health, also places in the 4th lane if its empty"
    }

}