package io.builderscon.conference2017.infra

import io.builderscon.client.model.Conference

interface ConferenceDAO {
    fun findAll(): Conference?
}