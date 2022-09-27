package com.nopo.cardgame.utils

import com.nopo.cardgame.GameField
import com.nopo.cardgame.screens.Game


fun renderCards(game: Game) {
    for (i in 0..4) {
        val cardEnvironment = GameField.getCard(i, GameField.Type.ENVIRONMENTS)
        if (cardEnvironment != null) {
           // game.batch.draw(cardEnvironment.texture, 100f + i * 200f, 100f)
        }
        val cardTheir = GameField.getCard(i, GameField.Type.THEIR_CARDS)
        if (cardTheir != null) {
            game.batch.draw(cardTheir.texture, 40f + (200 * i+1), 720f)
        }
        val cardYour = GameField.getCard(i, GameField.Type.YOUR_CARDS)
        if (cardYour != null) {
            game.batch.draw(cardYour.texture, 40f + (200 * i+1), 260f)
        }
    }
}