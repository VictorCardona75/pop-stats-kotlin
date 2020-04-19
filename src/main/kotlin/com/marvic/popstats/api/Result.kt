package com.marvic.popstats.api

class Result<T>(val payload: List<T>) {
    val itemsIncluded
        get() = payload.size
}