package com.nopo.cardgame.cards

import com.nopo.cardgame.GameField

class FrenzyCard(name: String, baseDamage: Int, baseHealth: Int, cost: Int) : Card(name, baseDamage, baseHealth, cost) {

    override fun onKill() {
        GameField.attack(this.lane, this)
    }
}