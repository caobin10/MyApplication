package com.example.myapplication.File

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class AppUtils private constructor() {
    companion object {
        /**
         * 复制离线库文件到 databases 目录
         */
        fun copyDbFileFromAsset(context: Context, dbName: String) {
            var `is`: InputStream
            var os: OutputStream
            val dbDir = File(context.filesDir.parent + "/databases")
            if (!dbDir.exists()) {
                dbDir.mkdir()
            }
            var outDbFile: File
            outDbFile = File(dbDir, dbName)
            try {
                os = FileOutputStream(outDbFile)
                val buffer = ByteArray(1024)
                var length: Int
                `is` = context.assets.open(dbName)
                while (`is`.read(buffer).also {
                        length = it
                    } > 0) {
                    os.write(buffer, 0, length)
                }
                os.flush()
                `is`.close()
                os.close()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}