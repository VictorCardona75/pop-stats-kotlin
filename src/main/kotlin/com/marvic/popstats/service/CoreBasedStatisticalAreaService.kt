package com.marvic.popstats.service

import com.marvic.popstats.data.CoreBasedStatisticalAreaRepository
import com.marvic.popstats.domain.CoreBasedStatisticalArea
import org.springframework.stereotype.Service

@Service
class CoreBasedStatisticalAreaService(private val repository: CoreBasedStatisticalAreaRepository) {
    fun findByCode(code: String): CoreBasedStatisticalArea? = repository.findByCode(code)

    fun findAll(): List<CoreBasedStatisticalArea> = convertToList(repository.findAll())

    fun findAllBy(codes: List<String>, titles: List<String>): List<CoreBasedStatisticalArea> =
        convertToList(repository.findAllByTitleInOrCodeIn(titles, codes))

    private fun convertToList(iterable: Iterable<CoreBasedStatisticalArea>): List<CoreBasedStatisticalArea> =
        iterable.toList()
}