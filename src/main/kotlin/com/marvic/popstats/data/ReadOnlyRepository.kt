package com.marvic.popstats.data

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface ReadOnlyRepository<T, ID> : Repository<T, ID> {
    fun count(): Long

    fun existsById(id: ID): Boolean

    fun findAll(): Iterable<T>

    fun findAllById(ids: Iterable<ID>): Iterable<T>

    fun findByCode(id: ID): T?
}