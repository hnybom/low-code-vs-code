package fi.solita.henriny.lowcodecode.challenge.ui.data

import com.vaadin.flow.data.provider.DataProvider
import com.vaadin.flow.data.provider.Query
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.PagingAndSortingRepository

fun <T, F> Query<T, F>.toSpringDataPageRequest() =
    PageRequest.of(offset / limit, limit, VaadinSpringDataHelpers.toSpringDataSort(this))

object DataProviders {

    fun <T,ID> getDataProvider(pagingAndSortingRepository: PagingAndSortingRepository<T,ID>): DataProvider<T, Void> {

        return DataProvider.fromCallbacks(
            { pagingAndSortingRepository.findAll(it.toSpringDataPageRequest()).stream() },
            { pagingAndSortingRepository.count().toInt() }
        )
    }
}