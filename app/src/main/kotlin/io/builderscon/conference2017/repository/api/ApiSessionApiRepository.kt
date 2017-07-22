package io.builderscon.conference2017.repository.api

import io.builderscon.client.model.Session
import io.builderscon.conference2017.repository.SessionRepository

class ApiSessionApiRepository : ApiRepository(), SessionRepository {
    override fun findAll(): List<Session>? {
        val sessions = client.sessions(id, locale.language)
        return sessions.execute().body()
    }
}