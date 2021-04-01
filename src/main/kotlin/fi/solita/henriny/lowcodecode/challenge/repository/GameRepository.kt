package fi.solita.henriny.lowcodecode.challenge.repository

import fi.solita.henriny.lowcodecode.challenge.repository.model.Game
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface GameRepository : PagingAndSortingRepository<Game, Long>
