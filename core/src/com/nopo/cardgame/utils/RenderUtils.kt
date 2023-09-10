package com.nopo.cardgame.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
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
        if (card.shouldShowInfo) {
            if (Gdx.input.isTouched) card.shouldShowInfo = false
            var a = Texture(Gdx.files.internal("black.png"))
            game.batch.draw(a, 0f, 370f, 962f, 245f)
            game.batch.draw(card.texture, 0f, 450f, card.texture.width.toFloat(), card.texture.height.toFloat())
            game.font.draw(game.batch, card.showInfo(), 10f, 600f)
        }
        if (!cardHolding(card, i)) {
            resetCard(card, i)
        }
        drawCardInfo(card, game)
        card.workOutCost()
        game.font.draw(game.batch, card.currentCost.toString(), card.rectangle.x, card.rectangle.y + card.texture.height - 5)
    }
}

fun drawCardInfo(card: Card, game: Game) {
    card.workOutHealth()
    card.workOutDamage()
    game.batch.draw(card.texture, card.rectangle.x, card.rectangle.y)

    if (card.currentDamage > card.baseDamage) game.font.setColor(0f, 1f, 0f, 1f)
    else if (card.currentDamage < card.baseDamage) game.font.setColor(1f, 0f, 0f, 1f)
    game.font.draw(game.batch, card.currentDamage.toString(), card.rectangle.x, card.rectangle.y + 13)

    if (card.currentHealth > card.baseHealth) game.font.setColor(0f, 1f, 0f, 1f)
    else if (card.currentHealth < card.baseHealth) game.font.setColor(1f, 0f, 0f, 1f)
    else game.font.setColor(1f, 1f, 1f, 1f)
    game.font.draw(game.batch, card.currentHealth.toString(), card.rectangle.x + card.texture.width - 5, card.rectangle.y + 13)

    game.font.setColor(1f, 1f, 1f, 1f)
}