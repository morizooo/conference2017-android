package io.builderscon.conference2017.repository.file

import io.builderscon.client.model.Conference
import io.builderscon.conference2017.repository.ConferenceRepository

class FileConferenceRepository: FileRepository(),  ConferenceRepository {
    override fun findAll(): Conference? {
        val adapter = moshi.adapter(Conference::class.java)
        return adapter.fromJson(readJson("/assets/json/conference.json"))
    }
}