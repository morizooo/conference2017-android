package io.builderscon.conference2017.infra.api

import io.builderscon.client.model.Conference
import io.builderscon.conference2017.infra.ConferenceDAO

class ApiConferenceDAO : ApiClient(), ConferenceDAO {
    override fun findAll(): Conference? {
        val conference = client.conference(id, locale.language)
        return conference.execute().body()
    }
}