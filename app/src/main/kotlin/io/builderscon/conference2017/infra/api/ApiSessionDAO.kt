package io.builderscon.conference2017.infra.api

import io.builderscon.client.model.Session
import io.builderscon.conference2017.infra.SessionDAO

class ApiSessionDAO : ApiClient(), SessionDAO {
    override fun findAll(): List<Session>? {
        val sessions = client.sessions(id, locale.language)
        return sessions.execute().body()
    }
}