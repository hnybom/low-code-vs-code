package fi.solita.henriny.lowcodecode.challenge.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridSortOrderBuilder
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.UIScope
import fi.solita.henriny.lowcodecode.challenge.repository.GamesRepository
import fi.solita.henriny.lowcodecode.challenge.repository.model.Game
import fi.solita.henriny.lowcodecode.challenge.repository.model.HighScore
import fi.solita.henriny.lowcodecode.challenge.service.GamesService
import fi.solita.henriny.lowcodecode.challenge.ui.view.dialog.HighScoreDialog
import fi.solita.henriny.lowcodecode.challenge.ui.data.DataProviders
import fi.solita.henriny.lowcodecode.challenge.ui.event.Event
import fi.solita.henriny.lowcodecode.challenge.ui.event.EventBroker
import fi.solita.henriny.lowcodecode.challenge.ui.event.HighScoreAdded
import fi.solita.henriny.lowcodecode.challenge.ui.event.EventListener
import java.util.*
import java.util.Locale

@UIScope
@Route("/")
class MainView(
    private val gamesRepository: GamesRepository,
    private val gamesService: GamesService
    ) : KComposite() {

    private var gameGrid : Grid<Game>? = null

    private val root = ui {
        UI.getCurrent().locale = Locale("fi", "FI")
        appLayout {
            navbar {

            }
            content {
                verticalLayout {
                    h2("Games")
                    gameGrid = grid(dataProvider = DataProviders.getDataProvider(gamesRepository)) {
                        addColumn(Game::name).setHeader("Name").setSortProperty("name")
                        addColumn(Game::platform).setHeader("Platform").setSortProperty("platform")
                        addColumn(Game::publisher).setHeader("Publisher").setSortProperty("publisher")
                        val rs = addColumn(Game::reviewScore).setHeader("Review score").setSortProperty("reviewScore")
                        addColumn(Game::releaseYear).setHeader("Released").setSortProperty("releaseYear")
                        sort(
                            GridSortOrderBuilder<Game>().thenDesc(rs).build()
                        )

                        addItemClickListener {
                            val highScoreDialog = HighScoreDialog(it.item, gamesService)
                            highScoreDialog.open()
                        }

                        EventBroker.registerForEvents(HighScoreAdded::class.java) { gameGrid?.refresh() }
                    }

                }
            }
        }
    }
}