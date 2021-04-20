package fi.solita.henriny.lowcodecode.challenge.repository

import fi.solita.henriny.lowcodecode.challenge.repository.model.Game
import org.springframework.data.domain.Pageable
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface GameRepository : PagingAndSortingRepository<Game, Long> {
    fun findByNameContainingIgnoreCase(name: String, pageable: Pageable): List<Game>

    @Query("select * from games where name like :name and review_score >= :minScore")
    fun findByNameAndMinimumScore(name: String, minScore: Int): List<Game>
}
