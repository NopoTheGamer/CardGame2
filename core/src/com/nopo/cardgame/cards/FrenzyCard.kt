package com.nopo.cardgame.cards

class FrenzyCard @JvmOverloads constructor(name: String, baseDamage: Int, baseHealth: Int, cost: Int, abilities: MutableList<Ability> = mutableListOf(Ability.FRENZY)) : Card(name, baseDamage, baseHealth, cost, abilities) {

}