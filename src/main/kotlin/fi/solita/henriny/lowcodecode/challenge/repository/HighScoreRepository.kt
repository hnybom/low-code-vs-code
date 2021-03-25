package fi.solita.henriny.lowcodecode.challenge.repository

import fi.solita.henriny.lowcodecode.challenge.repository.model.HighScore
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface HighScoreRepository : PagingAndSortingRepository<HighScore, Long>
