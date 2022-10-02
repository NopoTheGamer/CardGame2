package com.nopo.cardgame.utils

import com.nopo.cardgame.GameField
import com.nopo.cardgame.cardHolding
import com.nopo.cardgame.cards.Card
import com.nopo.cardgame.resetCard
import com.nopo.cardgame.screens.Game


fun renderCards(game: Game) { //TODO: refactor this code later
    for (i in 0..4) {
        val x = 40f + (200 * i + 1)
        val cardEnvironment = GameField.getCard(i, GameField.Type.ENVIRONMENTS)
        if (cardEnvironment != null) {
            // game.batch.draw(cardEnvironment.texture, 100f + i * 200f, 100f)
        }
        val cardTheir = GameField.getCard(i, GameField.Type.THEIR_CARDS)
        if (cardTheir != null) {
            cardTheir.rectangle.x = x
            cardTheir.rectangle.y = 720f
            drawCardInfo(cardTheir, game)
        }
        val cardYour = GameField.getCard(i, GameField.Type.YOUR_CARDS)
        if (cardYour != null) {
            cardYour.rectangle.x = x
            cardYour.rectangle.y = 260f
            drawCardInfo(cardYour, game)
        }
    }

    for (i in 0..9) {
        val card = GameField.getCard(i, GameField.Type.DECK) ?: continue
        if (!cardHolding(card, i)) {
            resetCard(card, i)
        }
        drawCardInfo(card, game)
        game.font.draw(game.batch, card.cost.toString(), card.rectangle.x, card.rectangle.y + card.texture.height - 5)
    }
}

fun drawCardInfo(card: Card, game: Game) {
    card.workOutHealth()
    card.workOutDamage()
    game.batch.draw(card.texture, card.rectangle.x, card.rectangle.y)
    game.font.draw(game.batch, card.currentDamage.toString(), card.rectangle.x, card.rectangle.y + 13)
    game.font.draw(game.batch, card.currentHealth.toString(), card.rectangle.x + card.texture.width - 5, card.rectangle.y + 13)
}