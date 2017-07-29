package io.builderscon.conference2017.model.repository

import io.builderscon.client.model.Sponsor
import io.builderscon.conference2017.infra.ConferenceDAO
import io.builderscon.conference2017.infra.api.ApiConferenceDAO
import io.builderscon.conference2017.infra.file.FileConferenceDAO

class SponsorRepository {

    val conferenceRepository: ConferenceDAO = ApiConferenceDAO().let {
        if (it.id.isEmpty()) FileConferenceDAO() else ApiConferenceDAO()
    }

    fun read(): List<Sponsor> {
        if (cachedSponsor.isEmpty()) cachedSponsor = conferenceRepository.findAll()?.sponsors ?: emptyList()

        return cachedSponsor
    }

    companion object {
        private var cachedSponsor: List<Sponsor> = emptyList()
    }
}