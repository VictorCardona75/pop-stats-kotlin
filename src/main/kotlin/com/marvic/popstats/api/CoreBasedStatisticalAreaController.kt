package com.marvic.popstats.api

import com.marvic.popstats.domain.CoreBasedStatisticalArea
import com.marvic.popstats.service.CoreBasedStatisticalAreaService
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
        @RequestParam(name = "title") titles: List<String>?
    ): Result<CoreBasedStatisticalArea> {
        val codeList = codes ?: listOf()
        val titleList = titles?.toMutableList() ?: mutableListOf()
        val result = if (codeList.isNotEmpty() || titleList.isNotEmpty()) {
            // Titles will contain commas which split the title in two.  So scan titles for splits and recombine.
            val iter = titleList.listIterator()
            while (iter.hasNext()) {
                val title = iter.next()
                if (title.matches(Regex("(?:[A-Z]{2}-?)+")) && iter.hasPrevious()) {
                    iter.remove()
                    iter.set("${iter.previous()}, $title")
                }
            }
            service.findAllBy(codeList, titleList)
        } else {
            service.findAll()
        }

        if (result.isEmpty()) throw ContentNotFoundException("No results found")

        return Result(result)
    }

    @GetMapping(path = ["/{code:\\d{5}}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getByCode(@PathVariable code: String): Result<CoreBasedStatisticalArea> =
        service.findByCode(code)?.let { Result(listOf(it)) } ?: throw ContentNotFoundException("No results found")
}
