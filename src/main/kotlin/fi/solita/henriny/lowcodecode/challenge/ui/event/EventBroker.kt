package fi.solita.henriny.lowcodecode.challenge.ui.event

interface Event

fun interface EventListener<E> where E : Event {
    fun handle(e: E)
}

object EventBroker {

    val listeners = HashMap<Class<out Event>, MutableSet<EventListener<Event>>>()

    fun <E> registerForEvents(type: Class<E>, listener: EventListener<E>) where E : Event {
        val ls = listeners[type] ?: HashSet()
        ls += listener as EventListener<Event>
        listeners[type] = ls
    }

    inline fun <reified E> sendEvent(event: E) where E : Event {
        listeners.getOrDefault(E::class.java, emptySet()).forEach {
            it.handle(event)
        }
    }
}


