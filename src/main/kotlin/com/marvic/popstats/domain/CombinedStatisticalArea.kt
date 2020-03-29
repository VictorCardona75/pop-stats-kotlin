package com.marvic.popstats.domain

/**
 * The `CombinedStatisticalArea` Class represents two or more adjacent core based
 * statistical areas (CBSAs) that have an employment interchange measure of 15 or more.  The
 * employment interchange measure (EIM) is a measure of ties between two adjacent entities. It is
 * the sum of the percentage of workers living in the smaller entity who work in the larger
 * entity and the percentage of employment in the smaller entity that is accounted for by workers
 * who reside in the larger entity.
 *
 * @constructor creates a new instance using the specified values
 */
class CombinedStatisticalArea(
    _code: String,
    _title: String,
    _populationEstimates: List<PopulationEstimate>,
    val statisticalAreas: List<CoreBasedStatisticalArea>
) : StatisticalReportingArea(_code, _title, _populationEstimates) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as CombinedStatisticalArea

        if (statisticalAreas != other.statisticalAreas) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + statisticalAreas.hashCode()
        return result
    }

    override fun toString(): String {
        return "CombinedStatisticalArea(code='$code', title='$title', statisticalAreas=$statisticalAreas)"
    }
}