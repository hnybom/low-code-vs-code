package fi.solita.henriny.lowcodecode.challenge.ui.event

import fi.solita.henriny.lowcodecode.challenge.repository.model.Game

data class HighScoreAdded (
    val game: Game
) : Event