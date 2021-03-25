package fi.solita.henriny.lowcodecode.challenge.ui.view.dialog

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.ComponentEventListener
import com.vaadin.flow.component.Unit
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridSortOrderBuilder
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.data.provider.DataProvider
import fi.solita.henriny.lowcodecode.challenge.repository.model.Game
import fi.solita.henriny.lowcodecode.challenge.repository.model.HighScore
import fi.solita.henriny.lowcodecode.challenge.service.GamesService
import fi.solita.henriny.lowcodecode.challenge.ui.event.Event
import fi.solita.henriny.lowcodecode.challenge.ui.event.EventBroker
import fi.solita.henriny.lowcodecode.challenge.ui.event.EventListener
import fi.solita.henriny.lowcodecode.challenge.ui.event.HighScoreAdded


class HighScoreDialog(
    private var game: Game,
    gamesService: GamesService
) : KComposite() {

    private var dialog: Dialog? = null
    private var grid: Grid<HighScore>? = null
    private val highScoreForm = HighScoreForm(game.id!!, gamesService)

    private val root = ui {
        dialog {

            dialog = this
            setWidth(70.0F, Unit.PERCENTAGE)

            verticalLayout {
                h3(game.name)

                grid = grid(dataProvider = DataProvider.ofCollection(game.getSortedScores())) {
                    addColumn(HighScore::gamerName).setHeader("Gamer name").setSortProperty("gamerName")
                    val scoreCol = addColumn(HighScore::score).setHeader("Score").setSortProperty("score")
                    addColumn(HighScore::created).setHeader("High score time").setSortProperty("created")
                    sort(
                        GridSortOrderBuilder<HighScore>().thenDesc(scoreCol).build()
                    )
                    setWidthFull()
                }

                add(highScoreForm)

                with(EventBroker) {
                    registerForEvents(HighScoreAdded::class.java
                    ) {
                        if (it.game.id == game.id) {
                            game = it.game
                            grid?.setItems(DataProvider.ofCollection(game.getSortedScores()))
                            grid?.refresh()
                        }
                    }
                }

                horizontalLayout {
                    button("Done") {
                        onLeftClick { dialog?.close() }
                    }
                    justifyContentMode = FlexComponent.JustifyContentMode.END
                    setWidthFull()
                }
            }

        }
    }

    fun open() = dialog?.open()

}