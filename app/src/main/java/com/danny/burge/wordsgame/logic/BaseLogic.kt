package com.danny.burge.wordsgame.logic

interface BaseLogic<T> {
    fun onEvent(gameEvent: GameEvent)
}