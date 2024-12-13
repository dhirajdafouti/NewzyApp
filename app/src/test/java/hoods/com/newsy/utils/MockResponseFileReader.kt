package hoods.com.newsy.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test
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

class MockResponseFileReaderTest {
    @Test
    fun `read simple file`() {
        val reader = MockResponseFileReader("test.json")
        assertThat(reader.content).isEqualTo("\"success\"")
    }
}