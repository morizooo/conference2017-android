package io.builderscon.conference2017.model

import io.builderscon.client.model.Session

data class Track(val name: String, val sessions: List<Session>?)