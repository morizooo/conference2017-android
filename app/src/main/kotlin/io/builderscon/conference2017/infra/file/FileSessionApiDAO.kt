package io.builderscon.conference2017.infra.file

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import io.builderscon.client.model.Session
import io.builderscon.conference2017.infra.SessionDAO


class FileSessionApiDAO : FileReader(), SessionDAO {
    override fun findAll(): List<Session>? {
        val type = Types.newParameterizedType(List::class.java, Session::class.java)
        val adapter: JsonAdapter<List<Session>> = moshi.adapter(type)
        return adapter.fromJson(readJson("/assets/json/sessions.json"))
    }

}
