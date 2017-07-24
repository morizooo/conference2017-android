package io.builderscon.conference2017.infra

import io.builderscon.client.model.Session

interface SessionDAO {
    fun findAll(): List<Session>?
}