import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class FileUtil {
    fun readTextFil(fullPath: String): String {
        val file = File(fullPath)
        if (!file.exists()) return ""

        val reader = FileReader(file)
        val buffer = BufferedReader(reader)
        var temp = ""
        val result = StringBuffer()

        while (true) {
            temp = buffer.readLine()
            if (temp == null) break;
            else result.append(buffer)
        }

        buffer.close()
        return result.toString()

        var content = readTextFil("${}/파일명.txt")
    }
}