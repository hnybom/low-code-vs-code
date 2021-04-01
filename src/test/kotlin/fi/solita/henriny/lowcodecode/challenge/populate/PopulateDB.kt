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

    }
}