package com.marvic.popstats.data

import com.marvic.popstats.domain.CoreBasedStatisticalArea

interface CoreBasedStatisticalAreaRepository : ReadOnlyRepository<CoreBasedStatisticalArea, String> {
    fun findAllByTitleInOrCodeIn(
        titles: Iterable<String>,
        codes: Iterable<String>
    ): Iterable<CoreBasedStatisticalArea>
}