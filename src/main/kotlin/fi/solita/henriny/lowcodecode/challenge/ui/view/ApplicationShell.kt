package fi.solita.henriny.lowcodecode.challenge.ui.view

import com.vaadin.flow.component.page.*
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.server.PWA

@Viewport("width=device-width, initial-scale=1")
@PageTitle("Low code challenge")
@BodySize(height = "100vh", width = "100vw")
@Meta(name = "author", content = "Henri Nybom")
@PWA(
    name = "Low code challenge",
    shortName = "LCC"
)
@Push
class ApplicationShell : AppShellConfigurator {

}