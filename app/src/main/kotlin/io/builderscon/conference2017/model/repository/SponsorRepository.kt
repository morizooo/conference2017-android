package io.builderscon.conference2017.model.repository

import io.builderscon.client.model.Sponsor
import io.builderscon.conference2017.infra.file.FileConferenceDAO

class SponsorRepository {
    fun read(): List<Sponsor> {
        val conferenceRepository = FileConferenceDAO()
        return conferenceRepository.findAll()?.sponsors ?: emptyList()
    }
}