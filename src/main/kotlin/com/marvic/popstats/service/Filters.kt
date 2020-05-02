package com.marvic.popstats.service

import com.marvic.popstats.domain.StatisticalAreaType

data class Filters(
    val codes: List<String> = listOf(),
    val titles: List<String> = listOf(),
    val type: StatisticalAreaType?
)
