package com.example.myapplication.base

import android.os.Bundle
import android.util.Xml
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Layer
import androidx.lifecycle.ViewModelProvider
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.classic.common.MultipleStatusView
import kotlinx.coroutines.GlobalScope
import okhttp3.ResponseBody
import q.rorbin.badgeview.QBadgeView
import zlc.season.downloadx.core.DownloadTask
import java.io.File
import java.io.InputStream
import java.io.OutputStream

abstract class BaseVmCommonActivity : BaseActivity() {
//    private lateinit var downloadTask: DownloadTask
//    private var descTv: TextView? = null
//    private var progressBar: ProgressBar? = null
//    private lateinit var dialog: Layer
//    private var version: Version? = null
//    protected var submitDialog: DialogLayer? = null
//    protected open lateinit var mViewModel: CommonViewModel
//
//    protected var tvUnit: UnitView? = null
//    protected var myAcetInput: ClearEditText? = null
//
//    protected val badgeView by lazy {
//        QBadgeView(this)
//    }
//
//
//    /**
//     * 请求参数Map
//     */
//    protected var args = mutableMapOf<String, String>()
//
//    /**
//     * 起始位
//     */
//    protected var offset: Int = 0
//
//    /**
//     * 每页加载数
//     */
//    protected var limit: Int = 20
//
//    /**
//     * 多种状态的 View 的切换
//     */
//    protected var mLayoutStatusView: MultipleStatusView? = null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        initViewModel()
//        observe()
//        tvUnit?.let {
//            setUnitTextView()
//        }
//
//        // 因为Activity恢复后savedInstanceState不为null，
//        // 重新恢复后会自动从ViewModel中的LiveData恢复数据，
//        // 不需要重新初始化数据。
//        if (savedInstanceState == null) {
//            initData()
//        }
//
//        mLayoutStatusView?.setOnClickListener { initData() }
//
//        checkUpdate()
//    }
//
//    open fun initData() {
//        if (offset == 0) mLayoutStatusView?.showLoading()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//    }
//
//    open fun observe() {
//        // 登录失效，跳转登录页
//        mViewModel.loginStatusInvalid.observe(this) {
//            if (it) {
//                Bus.post(USER_LOGIN_STATE_CHANGED, false)
//                ActivityUtil.start(LoginActivity::class.java)
//            }
//        }
//
//        mViewModel.exceptione.observe(this) {
//            when (it.code) {
//                ErrorStatus.NETWORK_ERROR -> {
//                    if (offset == 0) mLayoutStatusView?.showNoNetwork()
//                }
//                else -> if (offset == 0) mLayoutStatusView?.showError(it.message)
//            }
//        }
//
//        mViewModel.updateResult.observe(this) {
//            if (it != null) {
//                parserFile(it)
//            }
//        }
//    }
//
//    private fun initViewModel() {
//        mViewModel = ViewModelProvider(this).get(CommonViewModel::class.java)
//    }
//
//    fun showSubmitDialog() {
//        submitDialog = createProgressDefault(this, "操作执行中...")
//        submitDialog?.cancelableOnTouchOutside(false)
//        submitDialog?.cancelableOnKeyBack(false)
//    }
//
//    fun search(v: View) {
//        hideSoftKeyboard()
//        window.decorView.postDelayed({
//            offset = 0
//            initData()
//        }, 100)
//    }
//
//    /**
//     * 加载更多适配
//     */
//    fun <T> setLoadMoreAdapter(mAdapter: BaseQuickAdapter<T, BaseViewHolder>) {
//        //设置加载更多
//        mAdapter.loadMoreModule.setOnLoadMoreListener {
//            offset += limit
//            initData()
//        }
//        mAdapter.loadMoreModule.isAutoLoadMore = true
//        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
//        mAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
//    }
//
//
//    /**
//     * 加载更多结束
//     */
//    fun <T> loadMoreResult(
//        getData: GetData?,
//        data: List<T>,
//        mAdapter: BaseQuickAdapter<T, BaseViewHolder>
//    ) {
//        mLayoutStatusView?.showContent()
//        getData?.let {
//            if (it.isSuccess) {
//                if (data.isEmpty() && offset == 0) {
//                    mLayoutStatusView?.showEmpty()
//                } else {
//                    if (offset == 0) {
//                        mLayoutStatusView?.showContent()
//                        mAdapter.setList(data)
//                    } else {
//                        mAdapter.addData(data)
//                    }
//
//                    if (data.size < limit) {
//                        //如果s少于20,显示没有更多数据布局
//                        val isLoadEndMoreGone = (offset == 0)
//                        mAdapter.loadMoreModule.loadMoreEnd(isLoadEndMoreGone)
//                    } else {
//                        mAdapter.loadMoreModule.loadMoreComplete()
//                    }
//                }
//
//                if (it.extra.isNotEmpty()) {
//                    val count = (it.extra).toInt()
//                    myAcetInput?.let {
//                        badgeView.bindTarget(it).setBadgeNumber(count).isExactMode = true
//                    }
//                }
//
//            } else {
//                myToast(it.message)
//            }
//        }
//    }
//
//    /**
//     * 加载结束
//     */
//    fun loadResult(getData: GetData) {
//        if (getData.isSuccess) {
//            if (getData.data.isNullOrEmpty()) {
//                mLayoutStatusView?.showEmpty()
//            } else {
//                setLoadResultData(getData)
//                mLayoutStatusView?.showContent()
//            }
//        } else {
//            mLayoutStatusView?.showError(getData.message)
//        }
//    }
//
//    /**
//     * 加载结束设置数据
//     */
//    open fun setLoadResultData(data: GetData) {
//    }
//
//    open fun setUnitTextView(level: Int = 5) {
//        tvUnit?.setLevel(level)
//        tvUnit?.setSelectBack(object : UnitView.SelectCallBack<ItemUnitNode> {
//            override fun setCallBack(item: ItemUnitNode) {
//                offset = 0
//                mLayoutStatusView?.showLoading()
//                initData()
//            }
//        })
//    }
//
//    /**
//     * 检查更新
//     */
//    private fun checkUpdate() {
//        if (this is SplashActivity) {
//            return
//        }
//        val url = AppConfig.SERVER_MOBILE_VERSION_XML
//        mViewModel.checkUpdate(url)
//    }
//
//    //解析xml
//    private fun parserFile(body: ResponseBody) {
//        val basicVersion = getBasicDbVersionCode()
//        //解析xml
//        val pullParser = Xml.newPullParser()
//        pullParser.setInput(body.charStream())
//        version = XmlVersionParser.parseNodes(pullParser)
//
//        version?.let {
//            runOnUiThread {
//                if (getVersionCode() < it.code?.toInt() ?: 0) {
//                    //升级APP
//                    ActivityUtil.start(
//                        AppUpdateActivity::class.java,
//                        mapOf("version" to it)
//                    )
//                } else {
//                    //更新本地数据库
//                    if (basicVersion > 0 && basicVersion < it.basicVersion?.toInt() ?: 0) {
//
//                        dialog = AnyLayer.dialog(this)
//                            .contentView(R.layout.progress)
//                            .backgroundDimDefault()
//                            .cancelableOnTouchOutside(false)
//                            .cancelableOnKeyBack(false)
//                            .interceptKeyEvent(true)
//                        dialog.show()
//
//                        progressBar = dialog.getView(R.id.progressbar)
//                        descTv = dialog.getView(R.id.textview)
//
//                        download(version)
//                    } else {
//                        setBasicDbVersionCode(version?.basicVersion?.toInt())
//                    }
//                }
//
//                //验证apk
//                if (!AppConfig.DEBUG) {
//                    val md5 = VerityUtil.getSignMd5Str(this)
//                    val apkFileMd5 = VerityUtil.getAppFileMd5(this)
//
//                    if (it.verity1 != md5 || it.verity2 != apkFileMd5) {
//                        alert(getString(R.string.verity_msg), getString(R.string.warning)) {
//                            yesButton {
//                                //退出
//                                appExit()
//                            }
//                        }.show().setCancelable(false)
//                    }
//
//                }
//            }
//        }
//    }
//
//    private fun download(version: Version?) {
//        val url = String.format(BASICE_URL, version?.basicVersion)
//
//        downloadTask = GlobalScope.download(url)
//        downloadTask.state()
//            .onEach { setState(it) }
//            .launchIn(lifecycleScope)
//        downloadTask.start()
//    }
//
//    private fun setState(state: State) {
//        //设置进度
//        val percent = state.progress.percent()
//        dialog.getView<ProgressBar>(R.id.progressbar)?.progress = percent.toInt()
//
//        descTv?.text = when (state) {
//            is State.None -> {
//                "资源文件有更新"
//            }
//            is State.Waiting -> {
//                "资源等待中..."
//            }
//            is State.Downloading -> {
//                "资源下载中 ${state.progress.percentStr()}"
//            }
//            is State.Failed -> {
//                "下载失败，请重试"
//            }
//            is State.Stopped -> {
//                "继续"
//            }
//            is State.Succeed -> {
//                //成功后将文件写入到数据库
//                downloadTask.file()?.let {
//                    copyFileToDatabases(it)
//                }
//                "资源文件更新完成，请退出后重新启动。"
//            }
//        }
//    }
//
//    /**
//     * 复制文件到 databases 目录
//     */
//    private fun copyFileToDatabases(dbFile: File) {
//        try {
//            val `is`: InputStream
//            val os: OutputStream
//            val dbDir = File(filesDir.parent + "/databases")
//            if (!dbDir.exists()) {
//                dbDir.mkdir()
//            }
//            val outDbFile = File(dbDir, "basic.db")
//
//            os = FileOutputStream(outDbFile)
//            val buffer = ByteArray(1024)
//            var length: Int
//            `is` = dbFile.inputStream()
//            while (`is`.read(buffer).also { length = it } > 0) {
//                os.write(buffer, 0, length)
//            }
//            os.flush()
//            `is`.close()
//            os.close()
//
//            //保存版本号
//            setBasicDbVersionCode(version?.basicVersion?.toInt())
//
//            //退出重新启动
//            val btn = dialog.getView<Button>(R.id.button)
//            btn?.apply {
//                visibility = View.VISIBLE
//                setOnClickListener {
//                    dbFile.delete()
//                    appExit()
//                }
//            }
//
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//    }
}