package com.marvic.popstats

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PopStatsKotlinApplication

fun main(args: Array<String>) {
    runApplication<PopStatsKotlinApplication>(*args)
}
