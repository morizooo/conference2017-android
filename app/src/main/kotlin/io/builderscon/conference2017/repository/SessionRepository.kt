package io.builderscon.conference2017.repository

import io.builderscon.client.model.Session

interface SessionRepository {
    fun findAll(): List<Session>?
}