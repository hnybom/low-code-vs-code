package fi.solita.henriny.lowcodecode.challenge.repository.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("games")
data class Game(
    @Id
    val id: Long?,
    @Version
    val version: Long?,
    val name: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val rating: String,
    @Column("release_year")
    val releaseYear: Int,
    @Column("review_score")
    val reviewScore: Int,

    val highScores: Set<HighScore>) {

    fun getSortedScores(): List<HighScore> {
        return highScores.toList().sortedBy { it.score }
    }

}