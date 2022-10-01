package com.nopo.cardgame.utils

import com.badlogic.gdx.Gdx
import com.nopo.cardgame.GameField
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
            game.batch.draw(cardTheir.texture, cardTheir.rectangle.x, cardTheir.rectangle.y)
        }
        val cardYour = GameField.getCard(i, GameField.Type.YOUR_CARDS)
        if (cardYour != null) {
            cardYour.rectangle.x = x
            cardYour.rectangle.y = 260f
            game.batch.draw(cardYour.texture, cardYour.rectangle.x, cardYour.rectangle.y)
        }
    }

    for (i in 0..9) {
        val card = GameField.getCard(i, GameField.Type.DECK) ?: continue
        if (card.rectangle.overlaps(Game.pointer) && Gdx.input.isTouched && !card.isHeld) {
            card.isHeld = true
        } else if (Gdx.input.isTouched && card.isHeld) {
            card.rectangle.setX(Game.pointer.x)
            card.rectangle.setY(Game.pointer.y)
        } else {
            card.rectangle.setX(40f + (200 * i + 1))
            card.rectangle.setY(0f)
            card.isHeld = false;
        }
        game.batch.draw(card.texture, card.rectangle.x, card.rectangle.y)
    }
}