package hoods.com.newsy.utils

import java.io.InputStreamReader

class MockResponseFileReader(path: String) {
    val content: String

    init {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream(path)
            ?: throw IllegalArgumentException("File not found $path")
        content = InputStreamReader(inputStream).use { reader ->
            reader.readText()
        }
    }
}