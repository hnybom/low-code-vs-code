package fi.solita.henriny.lowcodecode.challenge.repository

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.javafaker.Faker
import fi.solita.henriny.lowcodecode.challenge.repository.model.Game
import fi.solita.henriny.lowcodecode.challenge.repository.model.HighScore
import org.slf4j.LoggerFactory
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.ZoneId
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Component
class GamePopulator(val gameRepository: GameRepository) {

    private val log = LoggerFactory.getLogger(GamePopulator::class.java)

    @EventListener
    fun onApplicationEvent(event: ContextRefreshedEvent) {
        if (gameRepository.count() != 0L) {
            log.info("Game DB is not empty skip population.")
            return
        }

        val faker = Faker()

        csvReader().open(this::class.java.classLoader.getResourceAsStream("/video_games.csv")) {
            readAllAsSequence().filterIndexed { index, _ -> index != 0 }.map { row: List<String> ->
                Game(
                    id = null,
                    version = null,
                    name = row[0],
                    platform = row[12],
                    genre = row[5],
                    publisher = row[7],
                    rating = row[13],
                    releaseYear = row[15].toInt(),
                    reviewScore = row[9].toInt(),
                    highScores = (0..Random.nextInt(5)).map {
                        HighScore(
                            id = null,
                            version = null,
                            game = null,
                            gamerName = faker.name().fullName(),
                            score = Random.nextLong(9999999L),
                            created = faker.date().past(1200, 10, TimeUnit.DAYS).toInstant()
                                .atZone(ZoneId.systemDefault()).toLocalDateTime()
                        )
                    }.toSet()
                )
            }.forEach {
                gameRepository.save(it)
            }
        }

    }

}