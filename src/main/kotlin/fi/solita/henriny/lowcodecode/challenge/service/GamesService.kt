package fi.solita.henriny.lowcodecode.challenge.service

import fi.solita.henriny.lowcodecode.challenge.repository.GameRepository
import fi.solita.henriny.lowcodecode.challenge.repository.model.Game
import fi.solita.henriny.lowcodecode.challenge.repository.model.HighScore
import fi.solita.henriny.lowcodecode.challenge.ui.model.HighScoreUIModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GamesService(val gameRepository: GameRepository) {

    @Transactional
    fun addHighScoreToGame(highScoreUIModel: HighScoreUIModel): Game {
        val gameOpt = gameRepository.findById(highScoreUIModel.gameId)
        if (gameOpt.isEmpty) throw IllegalStateException("Game not found with id: ${highScoreUIModel.gameId}")

        val game = gameOpt.get()
        val newGame = game.copy(
            highScores = game.highScores + HighScore(
                id = null,
                version = null,
                game = null,
                gamerName = highScoreUIModel.gamerName!!,
                score = highScoreUIModel.score!!.toLong(),
                created = highScoreUIModel.created!!
            )
        )

        return gameRepository.save(newGame)
    }


}