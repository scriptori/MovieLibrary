package me.sf.movielibrary.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.OutputStreamWriter
import java.nio.file.Paths
import me.sf.movielibrary.json.model.Movie

object JsonUtil {

    private val gson: Gson
        get() = GsonBuilder().setPrettyPrinting().create()

    fun <T : Any> fromJsonString(jsonStr: String, clazz: Class<T>): T {
        return gson.fromJson(jsonStr, clazz)
    }

    fun <T : Any> fromJsonFile(jsonFile: File, clazz: Class<T>): T {
        val bufferedReader = BufferedReader(FileReader(jsonFile))
        return gson.fromJson(bufferedReader, clazz)
    }

    fun jsonPrettyPrinting(jsonString: String): String {
        val listType = object : TypeToken<ArrayList<Movie?>?>() {}.type
        val currentValues: List<Movie> = Gson().fromJson(jsonString, listType)
        return GsonBuilder().setPrettyPrinting().create().toJson(
            currentValues
        )
    }

    private fun toJson(root: Any) = gson.toJson(root)

    fun writeToFile(fileName: String, folderName: String, content: Any) {
        val path = Paths.get("./", folderName).toFile()
        if (!path.exists()) path.mkdir()
        File(path, "${fileName}.json").also { file ->
            try {
                file.createNewFile()
                val out = FileOutputStream(file)
                OutputStreamWriter(out).append(toJson(content)).close()
                out.flush()
                out.close()
            } catch (e: Throwable) {
                println("File write failed: $e")
            }
        }
    }
}

