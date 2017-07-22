package io.builderscon.conference2017.repository

import io.builderscon.client.model.Conference

interface ConferenceRepository {
    fun findAll(): Conference?
}