package io.builderscon.conference2017.infra.file

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import java.util.*

abstract class FileReader {
    val moshi: Moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()

    fun readJson(jsonFilePath: String) = FileReader::class.java.getResource(jsonFilePath).readText()
}
