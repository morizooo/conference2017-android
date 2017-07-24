package io.builderscon.conference2017.infra.api

import io.builderscon.client.OctavClient
import java.util.*

abstract class ApiClient {
    val id = "" // TODO Load from conf
    val locale = Locale.getDefault() ?: Locale("jp")
    val client = OctavClient.client()
}