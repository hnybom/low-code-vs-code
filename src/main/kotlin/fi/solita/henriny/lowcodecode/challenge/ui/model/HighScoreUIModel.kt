package fi.solita.henriny.lowcodecode.challenge.ui.model

import java.time.LocalDateTime

data class HighScoreUIModel(
    var gameId: Long,
    var gamerName: String? = "",
    var score: Int? = 0,
    var created: LocalDateTime? = LocalDateTime.now()
)