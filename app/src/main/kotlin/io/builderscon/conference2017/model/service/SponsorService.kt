package io.builderscon.conference2017.model.service

import io.builderscon.client.model.Sponsor
import io.builderscon.conference2017.repository.api.ApiConferenceRepository
import io.builderscon.conference2017.repository.file.FileConferenceRepository

class SponsorService {
    fun read(): List<Sponsor>? {
        val conferenceRepository = FileConferenceRepository()
        return conferenceRepository.findAll()?.sponsors
    }
}