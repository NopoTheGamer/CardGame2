package com.nopo.cardgame

import com.badlogic.gdx.Gdx
import com.nopo.cardgame.cards.Card
import com.nopo.cardgame.screens.Game
import com.nopo.cardgame.screens.GameScreen

var holding = false;
fun cardHolding(card : Card, i : Int) : Boolean {
    if (GameScreen.INSTANCE.gameState == GameScreen.GAME_STATE.PLACING) {
        if (!Gdx.input.isTouched) holding = false
        if (card.rectangle.overlaps(Game.pointer) && Gdx.input.isTouched && !card.isHeld && !holding) {
            card.isHeld = true
            holding = true
        } else if (Gdx.input.isTouched && card.isHeld) {
            card.rectangle.setX(Game.pointer.x)
            card.rectangle.setY(Game.pointer.y)
        } else if (card.rectangle.y > 280) {
            val x = card.rectangle.x
            when {
                x > 0 && x < 160 ->
                    if (!GameField.playCard(0, card, GameField.Type.YOUR_CARDS)) {
                        resetCard(card, i)
                    }
                x > 170 && x < 320 ->
                    if (!GameField.playCard(1, card, GameField.Type.YOUR_CARDS)) {
                        resetCard(card, i)
                    }
                x > 330 && x < 512 ->
                    if (!GameField.playCard(2, card, GameField.Type.YOUR_CARDS)) {
                        resetCard(card, i)
                    }
                x > 522 && x < 700 ->
                    if (!GameField.playCard(3, card, GameField.Type.YOUR_CARDS)) {
                        resetCard(card, i)
                    }
                x > 710 && x < 880 ->
                    if (!GameField.playCard(4, card, GameField.Type.YOUR_CARDS)) {
                        resetCard(card, i)
                    }
                else -> {
                    resetCard(card, i)
                }
            }
        } else {
            resetCard(card, i)
        }
        return true
    }
    return false
}

fun resetCard(card : Card, i : Int) {
    card.rectangle.setX(40f + (200 * i + 1))
    card.rectangle.setY(0f)
    card.isHeld = false;
}