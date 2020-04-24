package com.marvic.popstats.api

data class Result<T>(val payload: List<T>) {
    val itemsIncluded
        get() = payload.size
}