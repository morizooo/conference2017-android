package io.builderscon.conference2017.repository.api

import io.builderscon.client.model.Conference
import io.builderscon.conference2017.repository.ConferenceRepository

class ApiConferenceRepository : ApiRepository(), ConferenceRepository {
    override fun findAll(): Conference? {
        val conference = client.conference(id, locale.language)
        return conference.execute().body()
    }
}