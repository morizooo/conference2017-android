package io.builderscon.conference2017.infra.file

import io.builderscon.client.model.Conference
import io.builderscon.conference2017.infra.ConferenceDAO

class FileConferenceDAO : FileReader(), ConferenceDAO {
    override fun findAll(): Conference? {
        val adapter = moshi.adapter(Conference::class.java)
        return adapter.fromJson(readJson("/assets/json/conference.json"))
    }
}