package fi.solita.henriny.lowcodecode.challenge.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.grid.GridSortOrderBuilder
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.UIScope
import fi.solita.henriny.lowcodecode.challenge.repository.GameRepository
import fi.solita.henriny.lowcodecode.challenge.repository.model.Game
import fi.solita.henriny.lowcodecode.challenge.service.GamesService
import fi.solita.henriny.lowcodecode.challenge.ui.component.dialog.HighScoreDialog
import fi.solita.henriny.lowcodecode.challenge.ui.data.DataProviders
import fi.solita.henriny.lowcodecode.challenge.ui.event.EventBroker
import fi.solita.henriny.lowcodecode.challenge.ui.event.HighScoreAdded
import java.util.*

@UIScope
@Route("/")
class MainView(
    private val gameRepository: GameRepository,
    private val gamesService: GamesService
    ) : KComposite() {

    private val root = ui {
        UI.getCurrent().locale = Locale("fi", "FI")
        appLayout {
            content {
                verticalLayout {
                    h2("Games")
                    grid(dataProvider = DataProviders.getDataProvider(gameRepository)) {
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

                        EventBroker.registerForEvents(HighScoreAdded::class.java) {
                            ui.get().access {
                                this.refresh()
                            }
                        }
                    }

                }
            }
        }
    }
}