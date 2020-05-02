package com.marvic.popstats.service

import com.marvic.popstats.data.CoreBasedStatisticalAreaRepository
import com.marvic.popstats.domain.CoreBasedStatisticalArea
import com.marvic.popstats.util.getLogger
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.inValues
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service

@Service
class CoreBasedStatisticalAreaService(private val repository: CoreBasedStatisticalAreaRepository) {
    fun findByCode(code: String): CoreBasedStatisticalArea? = repository.findByCode(code)

    fun findAll(): List<CoreBasedStatisticalArea> = convertToList(repository.findAll())

    fun findAllBy(codes: List<String>, titles: List<String>): List<CoreBasedStatisticalArea> =
        convertToList(repository.findAllByTitleInOrCodeIn(titles, codes))

    fun findAllBy(filters: Filters): List<CoreBasedStatisticalArea> {
        val criteria = mutableListOf<Criteria>()

        if (!filters.codes.isNullOrEmpty()) criteria += (CoreBasedStatisticalArea::code inValues filters.codes)
        if (!filters.titles.isNullOrEmpty()) criteria += (CoreBasedStatisticalArea::title inValues filters.titles)
        if (filters.type != null) criteria += (CoreBasedStatisticalArea::areaType isEqualTo filters.type)

        return when {
            criteria.isEmpty() -> convertToList(repository.findAll())
            criteria.size == 1 -> convertToList(repository.findBAllyQuery(Query(criteria.first())))
            else -> convertToList(repository.findBAllyQuery(Query(Criteria().andOperator(*criteria.toTypedArray()))))
        }
    }

    private fun convertToList(iterable: Iterable<CoreBasedStatisticalArea>): List<CoreBasedStatisticalArea> =
        iterable.toList()

    companion object {
        @JvmStatic
        val logger = getLogger(CoreBasedStatisticalAreaService::class.java)
    }
}