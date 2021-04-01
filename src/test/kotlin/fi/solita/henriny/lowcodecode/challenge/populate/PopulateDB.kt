package fi.solita.henriny.lowcodecode.challenge.populate

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.javafaker.Faker
import fi.solita.henriny.lowcodecode.challenge.repository.GameRepository
import fi.solita.henriny.lowcodecode.challenge.repository.model.Game
import fi.solita.henriny.lowcodecode.challenge.repository.model.HighScore
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.util.ResourceUtils
import java.time.ZoneId
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@SpringBootTest
@ComponentScan("fi.solita.henriny.lowcodecode.challenge")
@ActiveProfiles(profiles = ["dev"])
class PopulateDB {

    @Autowired
    lateinit var gameRepository: GameRepository

    @Test
    fun populate() {
        if(gameRepository.count() >0) return

        val faker = Faker()

        csvReader().open(ResourceUtils.getFile("classpath:video_games.csv")) {
            readAllAsSequence().filterIndexed { index, _ -> index != 0 }.map { row : List<String> ->
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
                    highScores = (0..Random.nextInt(100)).map {
                        HighScore(
                            id = null,
                            version = null,
                            game = null,
                            gamerName = faker.name().fullName(),
                            score = Random.nextLong(9999999L),
                            created = faker.date().past(1200, 10, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                        )
                    }.toSet()
                )
            }.forEach {
                gameRepository.save(it)
            }
        }
    }
}