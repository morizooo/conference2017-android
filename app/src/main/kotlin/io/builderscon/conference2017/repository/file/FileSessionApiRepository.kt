package io.builderscon.conference2017.repository.file

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import io.builderscon.client.model.Session
import io.builderscon.conference2017.repository.SessionRepository


class FileSessionApiRepository: FileRepository(), SessionRepository {
    override fun findAll(): List<Session>? {
        val type = Types.newParameterizedType(List::class.java, Session::class.java)
        val adapter: JsonAdapter<List<Session>> = moshi.adapter(type)
        return adapter.fromJson(readJson("/assets/json/sessions.json"))
    }

}
