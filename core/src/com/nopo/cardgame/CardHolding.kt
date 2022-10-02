package com.nopo.cardgame

import com.badlogic.gdx.Gdx
import com.nopo.cardgame.cards.Card
import com.nopo.cardgame.screens.Game
import com.nopo.cardgame.screens.GameScreen

fun cardHolding(card : Card, i : Int) : Boolean {
    if (GameScreen.INSTANCE.gameState == GameScreen.GAME_STATE.PLACING) {
        if (card.rectangle.overlaps(Game.pointer) && Gdx.input.isTouched && !card.isHeld) {
            card.isHeld = true
        } else if (Gdx.input.isTouched && card.isHeld) {
            card.rectangle.setX(Game.pointer.x)
            card.rectangle.setY(Game.pointer.y)
        } else if (card.rectangle.y > 280) {
            val x = card.rectangle.x
            when {
                x > 0 && x < 160 ->
                    GameField.playCard(0, card, GameField.Type.YOUR_CARDS)

                x > 170 && x < 320 ->
                    GameField.playCard(1, card, GameField.Type.YOUR_CARDS)

                x > 330 && x < 512 ->
                    GameField.playCard(2, card, GameField.Type.YOUR_CARDS)

                x > 522 && x < 700 ->
                    GameField.playCard(3, card, GameField.Type.YOUR_CARDS)

                x > 710 && x < 880 ->
                    GameField.playCard(4, card, GameField.Type.YOUR_CARDS)

                else -> {
                    card.rectangle.setX(40f + (200 * i + 1))
                    card.rectangle.setY(0f)
                    card.isHeld = false;
                }
            }
        } else {
            card.rectangle.setX(40f + (200 * i + 1))
            card.rectangle.setY(0f)
            card.isHeld = false;
        }
        return true
    }
    return false
}