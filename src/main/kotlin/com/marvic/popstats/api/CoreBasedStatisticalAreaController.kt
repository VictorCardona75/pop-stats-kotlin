package com.marvic.popstats.api

import com.marvic.popstats.domain.CoreBasedStatisticalArea
import com.marvic.popstats.domain.StatisticalAreaType
import com.marvic.popstats.service.CoreBasedStatisticalAreaService
import com.marvic.popstats.service.Filters
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/population/cbsas"])
class CoreBasedStatisticalAreaController(private val service: CoreBasedStatisticalAreaService) {
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllUnlessFiltered(
        @RequestParam(name = "code") codes: List<String>?,
        @RequestParam(name = "title") titles: List<String>?,
        @RequestParam(name = "type") type: StatisticalAreaType?
    ): Result<CoreBasedStatisticalArea> {
        val filters = createFilters(codes, titles, type)

        val result = if (filters == null) service.findAll() else service.findAllBy(filters)
        if (result.isEmpty()) throw ContentNotFoundException("No results found")

        return Result(result)
    }

    @GetMapping(path = ["/{code:\\d{5}}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getByCode(@PathVariable code: String): Result<CoreBasedStatisticalArea> =
        service.findByCode(code)?.let { Result(listOf(it)) } ?: throw ContentNotFoundException("No results found")

    private fun createFilters(codes: List<String>?, titles: List<String>?, type: StatisticalAreaType?): Filters? =
        if (codes.isNullOrEmpty()  && titles.isNullOrEmpty() && type == null) {
            null
        } else {
            Filters(codes ?: listOf(), fixTitles(titles), type)
        }

    private fun fixTitles(titles: List<String>?): List<String> {
        val titleList = titles?.toMutableList() ?: mutableListOf()
        // Titles will contain commas which split the title in two.  So scan titles for splits and recombine.
        val iter = titleList.listIterator()
        while (iter.hasNext()) {
            val title = iter.next()
            if (title.matches(Regex("(?:[A-Z]{2}-?)+")) && iter.hasPrevious()) {
                iter.remove()
                iter.set("${iter.previous()}, $title")
            }
        }

        return titleList
    }
}
