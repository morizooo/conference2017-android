package io.builderscon.conference2017.repository.api

import io.builderscon.client.OctavClient
import java.util.*

abstract class ApiRepository {
    val id = "" // TODO Load from conf
    val locale = Locale.getDefault() ?: Locale("jp")
    val client = OctavClient.client()
}