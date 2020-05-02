package com.marvic.popstats

import com.marvic.popstats.domain.StatisticalAreaType
import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CoreBasedStatisticalAreaSpecs(@LocalServerPort val port: Int) {
    @BeforeEach
    fun setup() {
        RestAssured.port = port
        RestAssured.basePath = "/population/cbsas"
    }

    @Nested
    inner class `All core based statistical areas` {
        @Test
        fun `are returned if no filters are specified`() {
            Given {
                log().params()
            } When {
                get()
            } Then {
                log().ifError()
                statusCode(HttpStatus.OK.value())
                body("itemsIncluded", equalTo(926))
            }
        }
    }

    @Nested
    inner class `Requested core based statistical areas` {
        @Test
        fun `are returned if one code is specified`() {
            Given {
                log().params()
                queryParam("code", "42660")
            } When {
                get()
            } Then {
                log().ifError()
                statusCode(HttpStatus.OK.value())
                body("itemsIncluded", equalTo(1))
            }
        }

        @Test
        fun `are returned if several codes are specified`() {
            Given {
                log().params()
                queryParam("code", "41180", "42660")
            } When {
                get()
            } Then {
                log().ifError()
                statusCode(HttpStatus.OK.value())
                body("itemsIncluded", equalTo(2))
            }
        }

        @Test
        fun `are returned if one title is specified`() {
            Given {
                log().params()
                queryParam("title", "St. Louis, MO-IL")
            } When {
                get()
            } Then {
                log().ifError()
                statusCode(HttpStatus.OK.value())
                body("itemsIncluded", equalTo(1))
            }
        }

        @Test
        fun `are returned if several titles are specified`() {
            Given {
                log().params()
                queryParam("title", "St. Louis, MO-IL", "Seattle-Tacoma-Bellevue, WA")
            } When {
                get()
            } Then {
                log().ifError()
                statusCode(HttpStatus.OK.value())
                body("itemsIncluded", equalTo(2))
            }
        }

        @Test
        fun `are returned if an area type is specified`() {
            Given {
                log().params()
                queryParam("type", StatisticalAreaType.METROPOLITAN.toString())
            } When {
                get()
            } Then {
                log().ifError()
                statusCode(HttpStatus.OK.value())
                body("itemsIncluded", equalTo(384))
            }
        }
    }

    @Nested
    inner class `An indiviual core based statistical area` {
        @Test
        fun `is returned if a code is provided`() {
            Given {
                log().params()
                pathParam("code", "41180")
            } When {
                get("/{code}")
            } Then {
                log().ifError()
                statusCode(HttpStatus.OK.value())
                body("itemsIncluded", equalTo(1))
            }
        }
    }
}