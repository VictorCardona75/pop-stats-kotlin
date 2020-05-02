package com.marvic.popstats.data

import com.marvic.popstats.domain.CoreBasedStatisticalArea
import com.marvic.popstats.util.getLogger
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.findOne
import org.springframework.data.mongodb.core.query.Query

interface DynamicCoreBasedStatisticalAreaRepository {
    fun findBAllyQuery(query: Query): Iterable<CoreBasedStatisticalArea>

    fun findOneByQuery(query: Query): CoreBasedStatisticalArea?
}

interface CoreBasedStatisticalAreaRepository : ReadOnlyRepository<CoreBasedStatisticalArea, String>,
    DynamicCoreBasedStatisticalAreaRepository {
    fun findAllByTitleInOrCodeIn(
        titles: Iterable<String>,
        codes: Iterable<String>
    ): Iterable<CoreBasedStatisticalArea>
}

class DynamicCoreBasedStatisticalAreaRepositoryImpl(val mongoOperations: MongoOperations) :
    DynamicCoreBasedStatisticalAreaRepository {
    override fun findBAllyQuery(query: Query): Iterable<CoreBasedStatisticalArea> {
        logger.debug(query.toString())
        return mongoOperations.find(query)
    }

    override fun findOneByQuery(query: Query): CoreBasedStatisticalArea? {
        logger.debug(query.toString())
        return mongoOperations.findOne(query)
    }

    companion object {
        @JvmStatic
        val logger = getLogger(DynamicCoreBasedStatisticalAreaRepositoryImpl::class.java)
    }
}