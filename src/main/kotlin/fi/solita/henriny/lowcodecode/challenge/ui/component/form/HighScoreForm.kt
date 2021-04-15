package fi.solita.henriny.lowcodecode.challenge.ui.component.form

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.data.binder.BeanValidationBinder
import com.vaadin.flow.data.binder.Binder
import fi.solita.henriny.lowcodecode.challenge.service.GamesService
import fi.solita.henriny.lowcodecode.challenge.ui.event.EventBroker
import fi.solita.henriny.lowcodecode.challenge.ui.event.HighScoreAdded
import fi.solita.henriny.lowcodecode.challenge.ui.model.HighScoreUIModel
import java.time.LocalDateTime


class HighScoreForm(
    private val gameId: Long,
    private val gamesService: GamesService
    ) : KComposite() {

    private val root = ui {
        verticalLayout {
            addClassName("add-high-score-form")
            h4("Add new high score")
            formLayout {

                val binder = BeanValidationBinder(HighScoreUIModel::class.java)

                textField("Gamer name") {
                    bind(binder).trimmingConverter()
                        .withValidator({ name -> name?.isNotEmpty() ?: false}, "Name cannot be empty")
                        .bind("gamerName")
                }

                integerField ("Score") {
                    bind(binder)
                        .withValidator({ score -> score ?: 0 > 0 }, "Score needs to be larger than 0")
                        .bind("score")
                }

                dateTimePicker("Created") {
                    bind(binder)
                        .withValidator(
                            { created -> created?.isBefore(LocalDateTime.now()) ?: false },
                            "Time cannot be in the future or empty"
                        )
                        .bind("created")
                }

                binder.bean = HighScoreUIModel(gameId = gameId)

                horizontalLayout {
                    button("Add") {
                        addClickListener {
                            handleAddition(binder)
                        }
                    }
                    justifyContentMode = FlexComponent.JustifyContentMode.END
                    setWidthFull()
                }
            }
        }
    }

    private fun handleAddition(binder: Binder<HighScoreUIModel>) {
        if (binder.validate().isOk) {
            EventBroker.sendEvent(
                HighScoreAdded(
                    game = gamesService.addHighScoreToGame(binder.bean)
                )
            )
            binder.bean = HighScoreUIModel(gameId = gameId)
        }
    }

}