package com.marvic.popstats.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class ContentNotFoundException(message: String?) : Exception(message)