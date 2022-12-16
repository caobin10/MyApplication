package com.demo.test.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.api.ApiException
import com.example.myapplication.api.ErrorStatus
import com.example.myapplication.api.ExceptionHandle
import com.example.myapplication.ui.common.CommonRepository
import com.example.myapplication.ui.common.UserRepository
import com.example.myapplication.util.bus.Bus
import com.example.myapplication.util.bus.USER_LOGIN_STATE_CHANGED
import com.example.myapplication.util.myToast
import com.google.gson.JsonParseException
import kotlinx.coroutines.*
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit

open class BaseViewModel : ViewModel() {

    val repository by lazy { CommonRepository() }

    val statusView = MutableLiveData<Int>()

    //登录状态失效
    val loginStatusInvalid: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @param error 错误时执行
     * @return Job
     */
    protected fun launch(block: Block<Unit>, error: Error? = null, cancel: Cancel? = null): Job {
        return viewModelScope.launch {
            try {
                block.invoke()
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
//                        onError(e)
                        handleException(e)
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @return Deferred<T>
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke() }
    }

    /**
     * 取消协程
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    /**
     * 统一处理错误
     * @param e 异常
     */
    fun onError(e: Exception) {

        statusView.value = ErrorStatus.UNKNOWN_ERROR
        when (e) {
            is ApiException -> {
                when (e.code) {
                    -1001 -> {
                        // 登录失效
                        UserRepository.clearLoginState()
                        Bus.post(USER_LOGIN_STATE_CHANGED, false)
                        loginStatusInvalid.value = true
                    }
                    -1 -> {
                        // 其他api错误
                        myToast(e.message)
                    }
                    else -> {
                        // 其他错误
                        myToast(e.message)
                    }
                }
            }
            is SocketException -> {
                statusView.value = ErrorStatus.NETWORK_ERROR
                // 连接失败
                myToast(App.context.getString(R.string.network_connection_failed))
            }
            is ConnectException -> {
                statusView.value = ErrorStatus.NETWORK_ERROR
                // 连接失败
                myToast(App.context.getString(R.string.network_connection_failed))
            }
            is UnknownHostException -> {
                statusView.value = ErrorStatus.NETWORK_ERROR
                // 连接失败
                myToast(App.context.getString(R.string.network_connection_failed))
            }
            is SocketTimeoutException -> {
                // 请求超时
                myToast(App.context.getString(R.string.network_request_timeout))
            }
            is JsonParseException -> {
                // 数据解析错误
                myToast(App.context.getString(R.string.api_data_parse_error))
            }
            else -> {
                // 其他错误
                e.message?.let { myToast(it) }
            }
        }
    }

    val exceptione = MutableLiveData<ExceptionHandle>()
    private fun handleException(e: Throwable) {
        e.printStackTrace()

        val ex = ExceptionHandle()

        if (e is SocketException) {//网络超时
            ex.message = "网络连接异常"
            ex.code = ErrorStatus.NETWORK_ERROR
        } else if (e is ConnectException) { //均视为网络错误
            ex.message = "网络连接异常"
            ex.code = ErrorStatus.NETWORK_ERROR
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException
        ) {   //均视为解析错误
            ex.message = "数据解析异常"
            ex.code = ErrorStatus.SERVER_ERROR
        } else if (e is ApiException) {//服务器返回的错误信息
            ex.message = e.message
            ex.code = ErrorStatus.SERVER_ERROR
        } else if (e is UnknownHostException) {
            ex.message = "网络连接异常"
            ex.code = ErrorStatus.NETWORK_ERROR
        } else if (e is IllegalArgumentException) {
            ex.message = "参数错误"
            ex.code = ErrorStatus.SERVER_ERROR
        } else if (e is SocketTimeoutException) {
            ex.message = "连接超时"
            ex.code = ErrorStatus.SERVER_ERROR
        } else {//未知错误
            ex.message = "未知错误，可能抛锚了吧~"
            ex.code = ErrorStatus.UNKNOWN_ERROR
        }

        exceptione.value = ex
    }
}