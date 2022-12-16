package com.example.myapplication.util

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.example.myapplication.App
import com.example.myapplication.R

/**
 * 安全的toast 默认剧中
 */
fun myToast(msg: String, gravity: Int = Gravity.CENTER){

    if(!msg.isNullOrEmpty()){
        showToast(msg,gravity)
    }

}

fun showToast(msg:String,gravity: Int = Gravity.CENTER,duration:Int = Toast.LENGTH_SHORT){
    App.context.apply {
        val toast = Toast(this)
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val v = inflater.inflate(R.layout.view_toast_default,null)

        toast.view = v

        when(gravity){
            Gravity.CENTER -> toast.setGravity(Gravity.CENTER,0,0)
            Gravity.TOP -> toast.setGravity(Gravity.TOP,0,220)
        }

//        v.findViewById<TextView>(android.R.id.message).text = msg
        toast.setText(msg)
        toast.duration = duration
        toast.show()
    }

}

///**
// * 加载中————
// */
//fun showLoadDialog(): DialogLayer {
//    var dialog = AnyLayer.dialog()
//        .outsideInterceptTouchEvent(false)
//        .contentView(R.layout.dialog_loading)
//        .backgroundDimDefault()
//    return dialog
//}
//
///**
// * 顶部消息对话框
// */
//fun showTopMsgDialog(msg:String){
//
//    val dialog = AnyLayer.dialog()
//        .contentView(R.layout.dialog_top_msg)
//        .backgroundDimDefault()
//        .avoidStatusBar(true)
//        .gravity(Gravity.TOP)
//        .animStyle(DialogLayer.AnimStyle.TOP)
//    dialog.show()
//
//    dialog.getView<MaxHeightScrollView>(R.id.scrollView)?.setMaxHeight(getScreenPix().heightPixels * 3 / 5)
//
//    val tv = dialog.getView<TextView>(R.id.tv_dialog_content)
//    tv?.text = msg
//
//}
//
///**
// * 提示对话框
// */
//fun showMsgDialog(msg: CharSequence, confirmListener: Layer.OnClickListener){
//    val dialog = AnyLayer.dialog()
//        .contentView(R.layout.dialog_msg)
//        .backgroundDimDefault()
//        .interceptKeyEvent(true)
//        .onClickToDismiss(R.id.cancel)
//        .onClick(confirmListener, R.id.confirm)
//
//    dialog.show()
//
//    dialog.getView<TextView>(R.id.content)?.text = msg
//}
//
///**
// * 隐私政策
// */
//fun showPrivacyDialog( confirmListener: Layer.OnClickListener){
//    val isAgreement = getSpValue(key = "Agreement_Privacy",defaultVal = false)
//    if(isAgreement){
//        return
//    }
//
//
//    var isFirstDisAgree = true
//
//    val dialog = AnyLayer.dialog()
//        .contentView(R.layout.dialog_msg)
//        .backgroundDimDefault()
//        .cancelableOnTouchOutside(false)
//        .interceptKeyEvent(true)
//        .onClick({ layer, view ->
//            if(isFirstDisAgree){
//                isFirstDisAgree = !isFirstDisAgree
//                layer.getView<TextView>(R.id.content)?.apply {
//
//                    val disAgreeText: String = context.getString(R.string.disAgree)
//                    val spannableString = SpannableString(disAgreeText)
//
//                    spannableString.setSpan(
//                        object :ClickableSpan(){
//                            override fun onClick(widget: View) {
//                                val url = "http://www.scliangfu.com/Themes/Manages/隐私政策.html"
//                                ActivityUtil.start(
//                                    AgentWebActivity::class.java,
//                                    mapOf(
//                                        AgentWebActivity.PARAM_WEB_TITLE to "隐私政策",
//                                        AgentWebActivity.PARAM_WEB_URL to url
//                                    )
//                                )
//                            }
//                        },
//                        disAgreeText.indexOf("《"),
//                        disAgreeText.indexOf("》") + 1,
//                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
//                    )
//
//                    spannableString.setSpan(
//                        StyleSpan(Typeface.BOLD_ITALIC),
//                        disAgreeText.indexOf("《"),
//                        disAgreeText.indexOf("》") + 1,
//                        0
//                    )
//
//                    text = spannableString
//                    movementMethod = LinkMovementMethod.getInstance()
//                    highlightColor = Color.TRANSPARENT
//
//                }
//            }else{
//                //退出
//                appExit()
//            }
//        },R.id.cancel)
//        .onClick(confirmListener, R.id.confirm)
//    dialog.show()
//
//    dialog.getView<TextView>(R.id.title)?.text = "隐私政策"
//    dialog.getView<TextView>(R.id.content)?.apply {
//
//        val privacyText = context.getString(R.string.privacy)
//        val spannableString = SpannableString(privacyText)
//        spannableString.setSpan(
//            object :ClickableSpan(){
//                override fun onClick(widget: View) {
//                    val url = "http://www.scliangfu.com/Themes/Manages/隐私政策.html"
//                    ActivityUtil.start(
//                        AgentWebActivity::class.java,
//                        mapOf(
//                            AgentWebActivity.PARAM_WEB_TITLE to "隐私政策",
//                            AgentWebActivity.PARAM_WEB_URL to url
//                        )
//                    )
//                }
//            },
//            privacyText.indexOf("《"),
//            privacyText.indexOf("》") + 1,
//            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
//        )
//        spannableString.setSpan(
//            StyleSpan(Typeface.BOLD_ITALIC),
//            privacyText.indexOf("《"),
//            privacyText.indexOf("》") + 1,
//            0
//        )
//
//        text = spannableString
//        movementMethod = LinkMovementMethod.getInstance()
//        highlightColor = Color.TRANSPARENT
//    }
//}
//
//
//
///**
// * 二维码
// */
//fun showQrCodeDialog(bitmap: Bitmap){
//
//    val dialog = AnyLayer.dialog()
//        .contentView(R.layout.dialog_qr_code)
//        .backgroundDimDefault()
//        .avoidStatusBar(true)
//        .gravity(Gravity.TOP)
//        .animStyle(DialogLayer.AnimStyle.TOP)
//    dialog.show()
//
//    dialog.getView<ImageView>(R.id.qrCodePhoto)?.load(bitmap)
//
//}
//
///**
// * 引导视图
// */
//fun showGuideLayer(context: Context, view: View,msg:String,above:Boolean = true){
//
//    val ll = LinearLayout(context).apply {
//        orientation = LinearLayout.HORIZONTAL
//        gravity = right
//    }
//    val textView = TextView(context).apply {
//        text = msg
//        textColor = context.resources.getColor(R.color.white)
//        textSize = 16f
//        rightPadding = 10
//    }
////    val but = TextView(context).apply {
////        text = "确定"
////        textSize = 16f
////        setPadding(50,15,50,15)
////        background = context.resources.getDrawable(R.drawable.shape_guide)
////        textColor = context.resources.getColor(R.color.white)
////    }
//
//    ll.addView(textView)
////    ll.addView(but)
//
//    var verticalAlign= if(above) GuideLayer.Align.Vertical.ABOVE else GuideLayer.Align.Vertical.BELOW
//    var marginTop = if(above) 0 else 30
//    var marginBottom = if(above) 30 else 0
//
//
//    var dialog = GuideLayer(context)
//        .mapping(
//            GuideLayer.Mapping()
//                .targetView(view)
//                .paddingLeft(30)
//                .paddingRight(30)
//                .paddingTop(-10)
//                .paddingBottom(-10)
//                .guideView(ll)
//                .horizontalAlign(GuideLayer.Align.Horizontal.ALIGN_PARENT_RIGHT)
//                .verticalAlign(verticalAlign)
//                .marginTop(marginTop)
//                .marginBottom(marginBottom)
//                .marginRight(30)
//
//        )
//    dialog.show()
//
////    but.setOnClickListener {
////        dialog.dismiss()
////    }
//}
//
///**
// * 提交对话框
// */
//fun createProgressDefault(
//    content: Context,
//    msg: String,
//    confirmListener: View.OnClickListener? = null
//): DialogLayer? {
//
//    var dialog = AnyLayer.dialog()
//        .contentView(R.layout.dialog_progress_default)
//        .cancelableOnTouchOutside(false)
//        .backgroundDimDefault()
//    dialog.show()
//
//    if(confirmListener == null){
//        dialog.getView<Button>(R.id.btn_ensure)?.setOnClickListener {
//            dialog.dismiss()
//        }
//    }else{
//        dialog.getView<Button>(R.id.btn_ensure)?.setOnClickListener(confirmListener)
//    }
//
//    var tvMsg = dialog.getView<TextView>(R.id.tv_msg)
//    tvMsg?.text = msg
//
//    var helper = ProgressHelper(content)
//    helper.progressWheel = dialog.getView<ProgressWheel>(R.id.pw_progressWheel)
//    helper.barWidth = 5
//
//    return dialog
//}
//
///**
// * @param type 1 提交成功 0 失败
// */
//fun playAnimation(content: Context, type: Int, msg: CharSequence, dialog: DialogLayer){
//
//    val mErrorInAnim = OptAnimationLoader.loadAnimation(content, R.anim.error_frame_in)
//    val mErrorXInAnim = OptAnimationLoader.loadAnimation(content, R.anim.error_x_in) as AnimationSet
//
//    val errorFl = dialog.getView<FrameLayout>(R.id.fl_error)
//    val errorIv = dialog.getView<ImageView>(R.id.iv_error)
//
//    val successFl = dialog.getView<FrameLayout>(R.id.fl_success)
//    val progressWheel = dialog.getView<ProgressWheel>(R.id.pw_progressWheel)
//    val successStv = dialog.getView<SuccessTickView>(R.id.stv_success)
//
//    if(type == 0){
//        successFl?.visibility = View.GONE
//        errorFl?.visibility = View.VISIBLE
//        errorFl?.startAnimation(mErrorInAnim)
//        errorIv?.startAnimation(mErrorXInAnim)
//    }else{
//        successFl?.visibility = View.VISIBLE
//        errorFl?.visibility = View.GONE
//        successStv?.visibility = View.VISIBLE
//        successStv?.startTickAnim()
//        progressWheel?.stopSpinning()
//        progressWheel?.progress = 1f
//    }
//    if(msg.isNotEmpty()){
//        dialog.getView<TextView>(R.id.tv_msg)?.text = msg
//    }
//    dialog.getView<Button>(R.id.btn_ensure)?.visibility = View.VISIBLE
//}
//
///**
// * 默认的输入框对话框
// */
//fun showInpDefaultDialog(
//    title: String?,
//    content: String?,
//    textWatcher: TextWatcher,
//    callback: OnResultCallback<String>
//): Layer {
//    val dialog = AnyLayer.dialog().contentView(R.layout.dialog_inp_default)
//
//    //使用默认的背景
//    dialog.backgroundDimDefault()
//
//    dialog.interceptKeyEvent(true)
//    dialog.onClickToDismiss(R.id.cancel)
//    dialog.show()
//
//    dialog.getView<TextView>(R.id.title)?.text = title
//
//    val input = dialog.getView<TextInputEditText>(R.id.selName)
//    if(!content.isNullOrEmpty())input?.setText(content)
//    input?.addTextChangedListener(textWatcher)
//
//    dialog.getView<TextView>(R.id.confirm)?.setOnClickListener {
//        dialog.dismiss()
//        callback.onResult(input?.text?.trim().toString())
//    }
//
//    return dialog
//}
//
//
///**
// * 底部弹出的列表对话框（单选）
// */
//fun showRadListDialog(
//    title: String?,
//    selValue: String?,
//    dictList: List<Dict>,
//    callback: OnResultCallback<String>
//) {
//    val dialog = AnyLayer.dialog()
//        .contentView(R.layout.dialog_rad_list)
//        .backgroundDimDefault()
//        .gravity(Gravity.BOTTOM)
//        .swipeDismiss(SwipeLayout.Direction.BOTTOM)
//        .onClickToDismiss(R.id.flFork)
//
//    dialog.show()
//
//    val mAdapter = ItemDictAdapter(selValue ?: "").apply{
//        setOnItemClickListener { _, _, position ->
//            val dict = getItem(position)
//
//            if(dict.dataValue.equals(selValue)){
//                dict.dataValue = ""
//            }
//
//            callback.onResult(dict.dataValue)
//            dialog.dismiss()
//        }
//    }
//
//    dialog.getView<TextView>(R.id.title)?.text = title?:""
//    dialog.getView<MaxHeightRecyclerView>(R.id.mRecyclerView)?.apply {
//        setMaxHeight(getScreenPix().heightPixels * 1 / 2)
//        adapter = mAdapter
//
//        mAdapter.setList(dictList)
//
//        val pos = mAdapter.getSelectIndex()
//
//        scrollToPosition(pos)
//    }
//
//}
//
//fun showCondNameDialog(
//    datas: List<DisCode>,
//    view: View,
//    callback: OnResultCallback<DisCode>,
//    defaultBackground: Boolean = true
//){
//
//    val dialog = AnyLayer.dialog()
//        .contentView(R.layout.dialog_rad_list)
//        .gravity(Gravity.BOTTOM)
//        .backgroundDimDefault()
//        .animStyle(DialogLayer.AnimStyle.BOTTOM)
//        .swipeDismiss(SwipeLayout.Direction.BOTTOM)
//        .onClickToDismiss(R.id.flFork)
//    dialog.show()
//
//    dialog.getView<TextView>(R.id.title)?.text = "查询条件"
//
//    val selValue =
//        if(view.tag == null){
//            ""
//        }else{
//            (view.tag as DisCode).code
//        }
//
//    val mAdapter = ItemDisCodeAdapter(selValue).apply{
//        setOnItemClickListener { _, _, position ->
//            val disCode = getItem(position)
//
//            callback.onResult(disCode)
//            dialog.dismiss()
//        }
//    }
//
//    dialog.getView<MaxHeightRecyclerView>(R.id.mRecyclerView)?.apply {
//        setMaxHeight(getScreenPix().heightPixels * 1 / 2)
//        adapter = mAdapter
//        mAdapter.setList(datas)
//    }
//
//}
//
//fun showCondValueDialog(
//    title: String?,
//    view: View? = null,
//    dictList: List<Dict>,
//    callback: OnResultCallback<Dict>
//) {
//    val dialog = AnyLayer.dialog()
//        .contentView(R.layout.dialog_rad_list)
//        .backgroundDimDefault()
//        .gravity(Gravity.BOTTOM)
//        .swipeDismiss(SwipeLayout.Direction.BOTTOM)
//        .onClickToDismiss(R.id.flFork)
//
//    dialog.show()
//
//    val selValue = if(view?.tag == null){
//        ""
//    }else{
//        view?.tag as String
//    }
//
//    val mAdapter = ItemDictAdapter(selValue).apply{
//        setOnItemClickListener { _, _, position ->
//            val dict = getItem(position)
//
//            if(dict.dataValue.equals(selValue)){
//                dict.dataValue = ""
//            }
//
//            callback.onResult(dict)
//            dialog.dismiss()
//        }
//    }
//
//    dialog.getView<TextView>(R.id.title)?.text = title?:""
//    dialog.getView<MaxHeightRecyclerView>(R.id.mRecyclerView)?.apply {
//        setMaxHeight(getScreenPix().heightPixels * 1 / 2)
//        adapter = mAdapter
//
//        mAdapter.setList(dictList)
//
//        val pos = mAdapter.getSelectIndex()
//
//        scrollToPosition(pos)
//    }
//
//}
//
///**
// * 时间限制的对话框
// * @param isMaxDate true 有最大限制 false 无上限
// */
//fun showDateDialog(
//    content: Context?,
//    callback: OnResultCallback<Date>,
//    isMaxDate: Boolean = true
//){
//
//    content?.let {
//        //获取日历的一个实例，里面包含了当前的年月日
//        val calendar: Calendar = Calendar.getInstance()
//        //构建一个日期对话框，该对话框已经集成了日期选择器
//        //DatePickerDialog的第二个构造参数指定了日期监听器
//        val dialog = DatePickerDialog(
//            content,
//            { p0, year, monthOfYear, dayOfMonth ->
//                val mDate =
//                    SimpleDateFormat("yyyy-MM-dd").parse("${year}-${monthOfYear + 1}-${dayOfMonth}")
//                //                    viewData.selName = "${year}-${monthOfYear+1}-${dayOfMonth}"
//                callback.onResult(mDate)
//            },
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        )
//
//        if(isMaxDate){
//            dialog.datePicker.maxDate = Date().time
//        }
//
//        //把日期对话框显示在界面上
//        dialog.show()
//    }
//}
//
//
///**
// * 底部弹出的列表对话框（多选）
// */
//fun showChkListDialog(
//    title: String?,
//    selname: String,
//    dictList: List<Dict>,
//    callback: OnResultCallback<String>
//) {
//    val dialog = AnyLayer.dialog()
//        .contentView(R.layout.dialog_rad_list)
//        .backgroundDimDefault()
//        .gravity(Gravity.BOTTOM)
//        .swipeDismiss(SwipeLayout.Direction.BOTTOM)
//        .onClickToDismiss(R.id.flFork)
//    dialog.show()
//
//    val sels = mutableSetOf<Dict>()
//    val names = selname.split(",")
//    dictList.forEach {
//        if(names.contains(it.dataName)){
//            sels.add(it)
//        }
//    }
//
//    val mAdapter = ChkListAdapter(sels).apply{
//        setOnItemClickListener { parent, view, position ->
//            val dict = getItem(position)
//
//            if(sels.contains(dict)){
//                sels.remove(dict)
//            }else{
//                sels.add(dict)
//            }
//            setCheckSelValue(sels)
//        }
//    }
//
//    dialog.getView<TextView>(R.id.title)?.text = title?:""
//    dialog.getView<MaxHeightRecyclerView>(R.id.mRecyclerView)?.apply {
//        setMaxHeight(getScreenPix().heightPixels * 1 / 2)
//        adapter = mAdapter
//
//        mAdapter.setList(dictList)
//    }
//
//    dialog.getView<LinearLayout>(R.id.ll_dialog_confirm)?.isGone = false
//    dialog.getView<TextView>(R.id.tv_dialog_confirm)?.setOnClickListener {
//
//        var valueBuilder =  StringBuilder()
//        sels.forEach {
//            valueBuilder.append("|$" + it.dataValue + "$");
//        }
//        if(valueBuilder.isNotEmpty()){
//            valueBuilder = valueBuilder.deleteCharAt(0)
//        }
//
//        dialog.dismiss()
//        callback.onResult(valueBuilder.toString())
//    }
//}
//
//
///**
// * 地区选择
// */
//fun showUnitCheckDialog(unitCode: String?, callback: OnResultCallback<ItemUnitNode>){
//    val dialog = AnyLayer.dialog()
//        .contentView(R.layout.dialog_rad_list)
//        .backgroundDimDefault()
//        .gravity(Gravity.BOTTOM)
//        .animStyle(DialogLayer.AnimStyle.BOTTOM)
//        .swipeDismiss(SwipeLayout.Direction.BOTTOM)
//        .onClickToDismiss(R.id.flFork)
//    dialog.show()
//
//    dialog.getView<TextView>(R.id.title)?.text = "地区选择"
//
//    val rv = dialog.getView<MaxHeightRecyclerView>(R.id.mRecyclerView)
//    rv?.setMaxHeight(getScreenPix()!!.heightPixels * 1 / 2)
//
//    val mAdapter = UnitTreeAdapter(object : UnitCallBack<ItemUnitNode> {
//        override fun setCallBack(item: ItemUnitNode) {
//            callback.onResult(item)
//            dialog.dismiss()
//        }
//    })
//
//    if(!unitCode.isNullOrEmpty()){
//        mAdapter.setCurrentUnitCode(unitCode)
//    }
//
//    rv?.adapter = mAdapter
//
//    getUser()?.let {
//        val list = initNodeData(it.unit!!.unitCode!!)
//        mAdapter.setList(list)
//    }
//}
//
//
//private fun initNodeData(unitCode: String): MutableList<BaseNode> {
//    val list= mutableListOf<BaseNode>()
//
//    val prent =  getUnitNode(unitCode, 0)
//    val children = getUnitNode(unitCode, 1)
//
//    prent.forEach {
//        val child = mutableListOf<BaseNode>()
//
//        children.forEach {
//            val childItem = ItemUnitNode(it.unitCode!!, it.unitName!!)
//            child.add(childItem)
//        }
//        val item = ItemUnitNode(it.unitCode!!, it.unitName!!, childNode = child)
//
//        //默认第一项展开
//        item.isExpanded = true
//        list.add(item)
//    }
//
//    return list
//}
//
//
///**
// * 服务详情
// */
//fun showDetailedDialog(
//    entity: ItemIndNode,
//    map: MutableMap<String, String>,
//    cancelListener: Layer.OnClickListener,
//    callback: OnResultCallback<MutableMap<String, String>>
//): Layer? {
//
//    val dialog = AnyLayer.dialog()
//        .contentView(R.layout.dialog_detailed)
//        .backgroundDimDefault()
//        .onClick(cancelListener, R.id.cancel)
//        .onVisibleChangeListener(object : Layer.OnVisibleChangeListener {
//            override fun onShow(layer: Layer) {
//                val dialogLayer = layer as DialogLayer
//                dialogLayer.compatSoftInput(
//                    layer.getView(R.id.tv_value4),
//                    layer.getView(R.id.tv_value5),
//                    layer.getView(R.id.serResult),
//                    layer.getView(R.id.serRemark)
//                )
//            }
//            override fun onDismiss(layer: Layer) {
//                val dialogLayer = layer as DialogLayer
//                dialogLayer.removeSoftInput()
//            }
//        })
//    //乡镇管理员 设置点击空白和返回不消失
//    if (isAble()) {
//        (dialog as DialogLayer).run {
//            cancelableOnTouchOutside(false)
//            cancelableOnClickKeyBack(false)
//        }
//    }
//    dialog.show()
//
//    //隐藏提交栏
//    if (!isAble()){
//        dialog.getView<RelativeLayout>(R.id.rl)?.visibility = View.GONE
//    }
//
//    val descTv = dialog.getView<TextView>(R.id.tv_desc)
//    descTv?.text = entity.serTip
//    // 服务部门
//    val serDepartTimeTv = dialog.getView<TextView>(R.id.tv_value1)
//    serDepartTimeTv?.text =map["serDepart"]
//    // 服务项目
//    dialog.getView<TextView>(R.id.tv_value2)?.text =  map["serItem"]
//    // 资金
//    val fundEd = dialog.getView<EditText>(R.id.tv_value4)
//    fundEd?.setText(map["serIndFund"])
//    // 非残联资金
//    val noDPFEd = dialog.getView<EditText>(R.id.tv_value5)
//    noDPFEd?.setText(map["serFund_NoDPF"])
//
//    if (!entity.isFund) {
//        dialog.getView<View>(R.id.ll_fund)?.visibility = View.GONE
//        dialog.getView<View>(R.id.ll_no_dpf)?.visibility = View.GONE
//    }
//
//    // 服务效果
//    val resultEd = dialog.getView<TextInputEditText>(R.id.serResult)
//    resultEd?.setText(map["serResult"])
//
//    // 服务备注
//    val remarkEd = dialog.getView<TextInputEditText>(R.id.serRemark)
//    remarkEd?.setText(map["serRemark"])
//
//    //获取当前月
//    val month = getCurrentMonth()
//
//    // 服务时间
//    val spinner = dialog.getView<MaterialSpinner>(R.id.spinner)
//    val dicts = mutableListOf<String>()
//
//    dicts.add(0, "未选择")
//    //选中下标
//    var selectedIndex = 0
//    for (index in 0 until month){
//        dicts.add(index + 1, "${index + 1}月")
//
//        if("${index +1}" == map["serTime"]){
//            selectedIndex = index +1
//        }
//    }
//
//    spinner?.setItems(dicts)
//    spinner?.selectedIndex = selectedIndex
//    spinner?.setOnItemSelectedListener { view, position, id, item ->
//        if("未选择" != item){
//            //去除单位 月
//            item.toString().apply {
//                map["serTime"] = this.substring(0, this.length - 1)
//            }
//        }
//    }
//
//    //免费
//    val chFree = dialog.getView<CheckBox>(R.id.ck_free)
//    if(entity.code == "1AA"){
//        chFree?.apply {
//            visibility = View.VISIBLE
//            if(selectedIndex != 0 && fundEd!!.text.isNullOrEmpty()){
//                isChecked = true
//                dialog.getView<View>(R.id.ll_fund)?.isGone = isChecked
//                dialog.getView<View>(R.id.ll_no_dpf)?.isGone = isChecked
//            }
//            setOnCheckedChangeListener { buttonView, isChecked ->
//                //清空资金
//                if(isChecked){
//                    fundEd?.setText("")
//                    noDPFEd?.setText("")
//                    map["serIndFund"] = ""
//                    map["serFund_NoDPF"] = ""
//                }
//                dialog.getView<View>(R.id.ll_fund)?.isGone = isChecked
//                dialog.getView<View>(R.id.ll_no_dpf)?.isGone = isChecked
//            }
//        }
//    }
//
//    //资金来源
//    if(entity.code == "1DA"){
//        val dictList = getDictList("56")
//        dialog.getView<View>(R.id.ll_fund_source)?.visibility = View.VISIBLE
//
//        val items = mutableListOf<String>()
//        val values = mutableListOf<String>()
//        items.add(0, "未选择")
//        values.add(0, "")
//
//        //选中下标
//        var selectedIndex = 0
//
//        val serFundSource = map["serFundSource"]
//
//        dictList?.forEachIndexed { index, dict ->
//            items.add(dict.dataName)
//            values.add(dict.dataValue)
//            if(serFundSource == dict.dataValue){
//                selectedIndex = index + 1
//            }
//        }
//
//        val fundSourceSpinner = dialog.getView<MaterialSpinner>(R.id.fundSource)
//        fundSourceSpinner?.setItems(items)
//        fundSourceSpinner?.selectedIndex = selectedIndex
//        fundSourceSpinner?.setOnItemSelectedListener { view, position, id, item ->
//            map["serFundSource"] = values[position]
//        }
//    }
//
//
//    setTextChangedListener(fundEd,map)
//    setTextChangedListener(noDPFEd,map)
//    setTextChangedListener(resultEd,map)
//    setTextChangedListener(remarkEd,map)
//
//
//    dialog.getView<MaxHeightScrollView>(R.id.scrollView)?.setMaxHeight(getScreenPix().heightPixels * 3 / 5)
//
//    dialog.getView<TextView>(R.id.confirm)?.apply {
//
//        setOnClickListener {
//
//            if(isRescueProject(entity.code)){
//                myToast("该项目不允许修改!")
//                return@setOnClickListener
//            }
//
//            if(map["serTime"].isNullOrEmpty() || "未选择" == map["serTime"]){
//                myToast("请填写服务时间!")
//                return@setOnClickListener
//            }
//            if(entity.isFund){
//                if(map["serIndFund"].isNullOrEmpty()){
//                    if(entity.code == "1AA" && chFree!!.isChecked){
//                    }else{
//                        myToast("请填写累计资金!")
//                        return@setOnClickListener
//                    }
//                }
//
//                //6.温暖万家行走访慰问  需填写资金来源
//                if(entity.code == "1DA" && map["serFundSource"].isNullOrEmpty() ){
//                    myToast("请填写资金来源!")
//                    return@setOnClickListener
//                }
//
//                val indFund= parseInt(map["serIndFund"], -1)
//                if(indFund != -1){
//                    if(indFund < entity.fundMin){
//                        myToast("累计资金不能低于 ${entity.fundMin}（元）!")
//                        return@setOnClickListener
//                    }
//                    if(indFund > entity.fundMax){
//                        myToast("累计资金不能超过 ${entity.fundMax}（元）!")
//                        return@setOnClickListener
//                    }
//                }
//
//                val indFundNoDpf= parseInt(map["serFund_NoDPF"], 0)
//                if(!map["serFund_NoDPF"].isNullOrEmpty()){
//                    if(indFundNoDpf > indFund){
//                        myToast("其中其他部门资金不能大于累计资金!")
//                        return@setOnClickListener
//                    }
//                }
//            }
//
//            dialog.dismiss()
//            callback.onResult(map)
//        }
//
//
//    }
//
//    return  dialog
//}
//
//fun showOrgOrPpcDetailedDialog(
//    entity: ItemIndNode,
//    map: MutableMap<String, String>,
//    cancelListener: Layer.OnClickListener,
//    callback: OnResultCallback<MutableMap<String, String>>
//): Layer? {
//
//    val dialog = AnyLayer.dialog()
//        .contentView(R.layout.dialog_detailed)
//        .backgroundDimDefault()
//        .cancelableOnClickKeyBack(false)
//        .cancelableOnTouchOutside(false)
//        .onClick(cancelListener, R.id.cancel)
//        .onVisibleChangeListener(object : Layer.OnVisibleChangeListener {
//            override fun onShow(layer: Layer) {
//                val dialogLayer = layer as DialogLayer
//                dialogLayer.compatSoftInput(
//                    layer.getView(R.id.tv_value4),
//                    layer.getView(R.id.tv_value5),
//                    layer.getView(R.id.serResult),
//                    layer.getView(R.id.serRemark)
//                )
//            }
//            override fun onDismiss(layer: Layer) {
//                val dialogLayer = layer as DialogLayer
//                dialogLayer.removeSoftInput()
//            }
//        })
//
//
//    dialog.show()
//
//
//    val descTv = dialog.getView<TextView>(R.id.tv_desc)
//    descTv?.text = entity.serTip
//    // 服务部门
//    val serDepartTimeTv = dialog.getView<TextView>(R.id.tv_value1)
//    serDepartTimeTv?.text =map["serDepart"]
//    // 服务项目
//    dialog.getView<TextView>(R.id.tv_value2)?.text =  map["serItem"]
//    // 资金
//    val fundEd = dialog.getView<EditText>(R.id.tv_value4)
//    fundEd?.setText(map["serIndFund"])
//    // 非残联资金
//    val noDPFEd = dialog.getView<EditText>(R.id.tv_value5)
//    noDPFEd?.setText(map["serFund_NoDPF"])
//
//    if (!entity.isFund) {
//        dialog.getView<View>(R.id.ll_fund)?.visibility = View.GONE
//        dialog.getView<View>(R.id.ll_no_dpf)?.visibility = View.GONE
//    }
//
//    // 服务效果
//    val resultEd = dialog.getView<TextInputEditText>(R.id.serResult)
//    resultEd?.setText(map["serResult"])
//
//    // 服务备注
//    val remarkEd = dialog.getView<TextInputEditText>(R.id.serRemark)
//    remarkEd?.setText(map["serRemark"])
//
//    //获取当前月
//    val month = getCurrentMonth()
//
//    // 服务时间
//    val spinner = dialog.getView<MaterialSpinner>(R.id.spinner)
//    val dicts = mutableListOf<String>()
//
//    dicts.add(0, "未选择")
//    //选中下标
//    var selectedIndex = 0
//    for (index in 0 until month){
//        dicts.add(index + 1, "${index + 1}月")
//
//        if("${index +1}" == map["serTime"]){
//            selectedIndex = index +1
//        }
//    }
//    spinner?.setItems(dicts)
//    spinner?.selectedIndex = selectedIndex
//    spinner?.setOnItemSelectedListener { view, position, id, item ->
//        if("未选择" != item){
//            //去除单位 月
//            item.toString().apply {
//                map["serTime"] = this.substring(0, this.length - 1)
//            }
//        }
//    }
//
//    //免费
//    val chFree = dialog.getView<CheckBox>(R.id.ck_free)
//    if(entity.code == "1AA"){
//        chFree?.apply {
//            visibility = View.VISIBLE
//            if(selectedIndex != 0 && fundEd!!.text.isNullOrEmpty()){
//                isChecked = true
//                dialog.getView<View>(R.id.ll_fund)?.isGone = isChecked
//                dialog.getView<View>(R.id.ll_no_dpf)?.isGone = isChecked
//            }
//            setOnCheckedChangeListener { buttonView, isChecked ->
//                //清空资金
//                if(isChecked){
//                    fundEd?.setText("")
//                    noDPFEd?.setText("")
//                    map["Fund"] = ""
//                    map["Fund_NoDPF"] = ""
//                }
//                dialog.getView<View>(R.id.ll_fund)?.isGone = isChecked
//                dialog.getView<View>(R.id.ll_no_dpf)?.isGone = isChecked
//            }
//        }
//    }
//
//    //资金来源
//    if(entity.code == "1DA"){
//        val dictList = getDictList("56")
//        dialog.getView<View>(R.id.ll_fund_source)?.visibility = View.VISIBLE
//
//        val items = mutableListOf<String>()
//        val values = mutableListOf<String>()
//        items.add(0, "未选择")
//        values.add(0, "")
//
//        //选中下标
//        var selectedIndex = 0
//
//        val serFundSource = map["serFundSource"]
//
//        dictList?.forEachIndexed { index, dict ->
//            items.add(dict.dataName)
//            values.add(dict.dataValue)
//            if(serFundSource == dict.dataValue){
//                selectedIndex = index + 1
//            }
//        }
//
//        val fundSourceSpinner = dialog.getView<MaterialSpinner>(R.id.fundSource)
//        fundSourceSpinner?.setItems(items)
//        fundSourceSpinner?.selectedIndex = selectedIndex
//        fundSourceSpinner?.setOnItemSelectedListener { view, position, id, item ->
//            map["serFundSource"] = values[position]
//        }
//    }
//
//    setTextChangedListener(fundEd,map)
//    setTextChangedListener(noDPFEd,map)
//    setTextChangedListener(resultEd,map)
//    setTextChangedListener(remarkEd,map)
//
//    dialog.getView<MaxHeightScrollView>(R.id.scrollView)?.setMaxHeight(getScreenPix().heightPixels * 3 / 5)
//
//
//    dialog.getView<TextView>(R.id.confirm)?.apply {
//
//        text = context.getString(R.string.submit)
//        setOnClickListener {
//
//            if(isRescueProject(entity.code)){
//                myToast("该项目不允许修改!")
//                return@setOnClickListener
//            }
//
//            if(map["serTime"].isNullOrEmpty() || "未选择" == map["serTime"]){
//                myToast("请填写服务时间!")
//                return@setOnClickListener
//            }
//            if(entity.isFund){
//                if(map["serIndFund"].isNullOrEmpty()){
//                    if(entity.code == "1AA" && chFree!!.isChecked){
//                    }else{
//                        myToast("请填写累计资金!")
//                        return@setOnClickListener
//                    }
//                }
//
//                //6.温暖万家行走访慰问  需填写资金来源
//                if(entity.code == "1DA" && map["serFundSource"].isNullOrEmpty() ){
//                    myToast("请填写资金来源!")
//                    return@setOnClickListener
//                }
//
//                val indFund= parseInt(map["serIndFund"], -1)
//
//                if(indFund != -1){
//                    if(indFund < entity.fundMin){
//                        myToast("累计资金不能低于 ${entity.fundMin}（元）!")
//                        return@setOnClickListener
//                    }
//                    if(indFund > entity.fundMax){
//                        myToast("累计资金不能超过 ${entity.fundMax}（元）!")
//                        return@setOnClickListener
//                    }
//                }
//
//                val indFundNoDpf= parseInt(map["serFund_NoDPF"], 0)
//                if(!map["serFund_NoDPF"].isNullOrEmpty()){
//                    if(indFundNoDpf > indFund){
//                        myToast("其中其他部门资金不能大于累计资金!")
//                        return@setOnClickListener
//                    }
//                }
//            }
//            //隐藏键盘
//            it.hideSoftInput()
//            Timer().schedule(object :TimerTask(){
//                override fun run() {
//                    callback.onResult(map)
//                }
//            },100)
//        }
//    }
//
//    return  dialog
//}
//
//private fun setTextChangedListener(et:EditText?,map: MutableMap<String, String>){
//    et?.addTextChangedListener(object :TextWatcher{
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        }
//
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//        }
//
//        override fun afterTextChanged(s: Editable?) {
//            //判断是否是0开头
//            if (s?.startsWith("0")!!) {
//                et.setText(s.subSequence(1, s.length))
//            }
//            when(et.id){
//                R.id.tv_value4-> map["serIndFund"] = et.text.toString().trim()
//                R.id.tv_value5-> map["serFund_NoDPF"] = et.text.toString().trim()
//                R.id.serResult-> map["serResult"] = et.text.toString().trim()
//                R.id.serRemark-> map["serRemark"] = et.text.toString().trim()
//            }
//
//        }
//    })
//}
//
//fun showAppUpdateDialog(){
//    val dialog = AnyLayer.dialog().contentView(R.layout.activity_app_update)
//    //使用默认的背景
//    dialog.backgroundDimDefault()
//    dialog.interceptKeyEvent(true)
//    dialog.onClickToDismiss(R.id.cancel)
//    dialog.show()
//
//
//}
//
//
//
//interface OnResultCallback<T> {
//    fun onResult(t: T)
//}