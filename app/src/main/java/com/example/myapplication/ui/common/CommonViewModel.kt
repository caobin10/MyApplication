package com.example.myapplication.ui.common

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.demo.test.base.BaseViewModel
import com.example.myapplication.AppConfig
import com.example.myapplication.factory.DaoFactory
import com.example.myapplication.model.bean.GetData
import com.example.myapplication.util.getCacheVerCode
import com.example.myapplication.util.getVersionCode
import com.example.myapplication.util.setCacheVerCode
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class CommonViewModel : BaseViewModel() {

    val result = MutableLiveData<GetData>()

    fun getData(args: Map<String, String>) {

        if(AppConfig.DEBUG) {
            args.forEach {
                Log.i(it.key, it.value)
            }
        }

        launch(
            block = {
                val data = repository.getData(args)
                result.value = data
            },
            error = {
            }
        )
    }

    fun getSessionId(args: Map<String, String>) {
        launch(
            block = {
//                val data = repository.getSessionId(args)
//                result.value = data
            },
            error = {
            }
        )
    }

    val otherResult = MutableLiveData<GetData>()
    fun getOtherData(args: MutableMap<String, String>) {
        launch(
            block = {
                val data = repository.getData(args)
                otherResult.value = data
            },
            error = {
            }
        )
    }

    val refreshResult = MutableLiveData<GetData>()
    fun refreshData(args: MutableMap<String, String>) {
        launch(
            block = {
                val data = repository.getData(args)
                refreshResult.value = data
            },
            error = {
                val getData = GetData()
                getData.isSuccess = false
                saveResult.value = getData
            }
        )
    }

    val saveResult = MutableLiveData<GetData>()
    fun saveData(args: MutableMap<String, String>) {
        if(AppConfig.DEBUG) {
            args.forEach {
                Log.i(it.key, it.value)
            }
        }
        launch(
            block = {
                val data = repository.getData(args)
                saveResult.value = data
            },
            error = {
                val getData = GetData()
                getData.isSuccess = false
                saveResult.value = getData
            }
        )
    }

    val checkResult = MutableLiveData<GetData>()
    fun checkData(args: MutableMap<String, String>) {
        if(AppConfig.DEBUG) {
            args.forEach {
                Log.i(it.key, it.value)
            }
        }

        launch(
            block = {
                val data = repository.getData(args)
                checkResult.value = data
            },
            error = {
                val getData = GetData()
                getData.isSuccess = false
                getData.message = "保存失败！"
                checkResult.value = getData
            }
        )
    }


    val checkStateFlag = MutableLiveData<GetData>()
    fun checkStateFlag(args: MutableMap<String, String>) {
        launch(
            block = {
                val data = repository.getData(args)
                checkStateFlag.value = data
            },
            error = {
            }
        )
    }

    val deleteResult = MutableLiveData<GetData>()
    fun deleteData(args: MutableMap<String, String>) {
        launch(
                block = {
                    val data = repository.getData(args)
                    deleteResult.value = data
                },
                error = {
                    //加载出错 关闭对话框
                }
        )
    }

    var body = MutableLiveData<ResponseBody>()
    fun downFile(args: MutableMap<String, String>) {
        launch(
            block = {
//                val responseBody =  repository.downFile(args)
//                body.value = responseBody.body()
            },
            error = {}
        )
    }

    var updateResult = MutableLiveData<ResponseBody>()
//    fun checkUpdate(url:String) {
//        launch(
//                block = {
//                    val responseBody =  repository.checkUpdate(url)
//                    updateResult.value = responseBody.body()
//                }
//        )
//    }




    fun saveDataAndUploadFile(args:MutableMap<String,String>, flies:MutableMap<String,File>){

        val partList = mutableListOf<MultipartBody.Part>()

        flies.forEach {
            val key = it.key
            val file = it.value

            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData(key, file.name, requestFile)
            partList.add(body)
        }

        launch(
                block = {
//                    var getData =  repository.upLoadFiles(args,partList)

                },
                error = {}
        )
    }


    /**
     * 拷贝assets 文件到 databases 目录
     */
    fun copyDbFileFromAsset(context: Context, dbMap: Map<String, Boolean?> ) {
        context.doAsync {
            var `is`: InputStream
            var os: OutputStream
            val dbDir = File(context.filesDir.parent + "/databases")
            if (!dbDir.exists()) {
                dbDir.mkdir()
            }
            var outDbFile: File

            val verCode = getVersionCode()
            val cacheVerCode = getCacheVerCode()

            for ((dbName) in dbMap) {
                if (cacheVerCode < verCode) {
                    //staff.db  disabler.db 只需缓存一次
                    if(cacheVerCode >0 &&(dbName == "staff.db" || dbName == "disabler.db")){
                        continue
                    }
                    outDbFile = File(dbDir, dbName)
                    try {
                        os = FileOutputStream(outDbFile)
                        val buffer = ByteArray(1024)
                        var length: Int
                        `is` = context.assets.open("db/$dbName")
                        while (`is`.read(buffer).also { length = it } > 0) {
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
            setCacheVerCode(verCode)

            if(cacheVerCode < 5001){
                //指定 某个版本 删除本地数据
                clearData()
            }else {
                //删除不在地区范围的数据
                deleteDbData()
            }


            //特殊处理  DisDetail 增加字段serFundSource
            if(cacheVerCode < 5001){
                val helper = DaoFactory.instant.getOpenHelper("staff.db")
                val db = helper.writableDatabase
                try {
                    db.execSQL("ALTER TABLE DisDetail ADD 'serFundSource' TEXT ")
                }catch (e:Exception){}
            }

        }
    }

    private fun deleteDbData() {
        doAsync {
        }
    }

    private fun clearData() {
        doAsync {
//            getDisBaseDao().deleteAll()
//            getBaseSurveyDao().deleteAll()
//            getSerMonthFundDao().deleteAll()
//            getSerMonthNumDao().deleteAll()
//            getDisDetailDao().deleteAll()
        }
    }
}