package fi.solita.henriny.lowcodecode.challenge.ui.data

import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider
import com.vaadin.flow.data.provider.DataProvider
import com.vaadin.flow.data.provider.Query
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

fun <T, F> Query<T, F>.toSpringDataPageRequest() =
    PageRequest.of(offset / limit, limit, VaadinSpringDataHelpers.toSpringDataSort(this))

object DataProviders {

    fun <T, ID> getDataProvider(
        pagingAndSortingRepository: PagingAndSortingRepository<T, ID>,
        filtered: (String, Pageable) -> List<T>
    ): ConfigurableFilterDataProvider<T, Void, String>? {

        return DataProvider.fromFilteringCallbacks<T, String?>(
            {
                if (it.filter.isEmpty || it.filter.get()
                        .isBlank()
                ) pagingAndSortingRepository.findAll(it.toSpringDataPageRequest()).stream()
                else filtered(it.filter.get(), it.toSpringDataPageRequest()).stream()
            },
            {
                if (it.filter.isEmpty) pagingAndSortingRepository.count().toInt()
                else filtered(it.filter.get(), it.toSpringDataPageRequest()).size
            }
        ).withConfigurableFilter()


    }
}