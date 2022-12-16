package com.example.myapplication.util

import android.content.Context
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.NonNull
import com.example.myapplication.App
import kotlin.system.exitProcess

/**
 * Created by WLM on 2021/8/5
 * Desc:
 */

/**
 * 获取手机大小（分辨率）
 *
 * @param
 * @return
 */
@NonNull
fun getScreenPix(): DisplayMetrics {
    val windowManager = App.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    //DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
    val displaysMetrics = DisplayMetrics()
    //获取手机窗口的Display 来初始化DisplayMetrics 对象
    //getManager()获取显示定制窗口的管理器。
    //获取默认显示Display对象
    //通过Display 对象的数据来初始化一个DisplayMetrics 对象
    windowManager.defaultDisplay.getMetrics(displaysMetrics)
    return displaysMetrics
}

/**
 * 获取版本号
 */
fun getVersionCode(): Int {
    return App.context.packageManager.getPackageInfo(App.context.packageName, 0).versionCode
}

/**
 * 得到软件显示版本信息
 */
fun getVerName(): String {
    var verName = ""
    try {
        val packageName = App.context.packageName
        verName = App.context.packageManager.getPackageInfo(packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    return verName
}

/**
 * 获取缓存版本号
 */
fun getCacheVerCode(): Int {
    return getSpValue(key = "cacheVerCode", defaultVal = 0)
}
/**
 * 缓存版本号
 */
fun setCacheVerCode(ver: Int = 0) {
    putSpValue(key = "cacheVerCode", value = ver)
}

/**
 * 缓存Basic数据库版本号
 */
fun getBasicDbVersionCode(): Int {
    return getSpValue(key = "basicDbVerCode", defaultVal = 0)
}
fun setBasicDbVersionCode(ver: Int? = 0) {
    putSpValue(key = "basicDbVerCode", value = ver)
}

/**
 * 退出app
 */
fun appExit() {
    try {
        val list = ActivityUtil.activities
        list.forEach {it.finish()}
        list.clear()

        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(0)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

/**
 *
 */
//fun getDictDataName(dataType:String,dataValue:String?):String{
//    if(dataValue.isNullOrEmpty()){
//        return ""
//    }
//    return getDictDao().queryBuilder().where(
//        DictDao.Properties.DataType.eq(dataType),
//        DictDao.Properties.DataValue.eq(dataValue)
//    ).build().unique()?.dataName?:""
//}
//
///**
// * 字典集合
// */
//fun getDictList(dataValue: String): MutableList<Dict>? {
//    return getDictDao().queryBuilder().where(DictDao.Properties.DataType.eq(dataValue)).build().list()
//}
//
//
//fun getDictDataValue(dataType:String,dataName:String):String{
//    return getDictDao().queryBuilder().where(
//        DictDao.Properties.DataType.eq(dataType),
//        DictDao.Properties.DataName.eq(dataName)
//    ).build().unique()?.dataValue?:""
//}
//
///**
// * 获取用户
// */
//fun getUser(): User? {
//    return UserRepository.getUser()
//}
//
///**
// * 没有网咯
// */
//fun isNoNetWork(): Boolean {
//    return !NetworkUtil.isNetworkAvailable(App.context)
//}
//
///**
// * 比较时间
// */
//@SuppressLint("SimpleDateFormat")
//fun compareTime(time1:String, time2:String): Boolean {
//    val sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
//    return try {
//        val date1 = sf.parse(time1)
//        val date2 = sf.parse(time2)
//
//        date1!!.before(date2)
//    } catch (e: ParseException) {
//        e.printStackTrace()
//        false
//    }
//}
//
///**
// * 获取地区名
// */
//fun getAllUnitName(unitCode: String?): String {
//    val sql = StringBuffer()
//
//    if(unitCode.isNullOrEmpty()){
//        return sql.toString()
//    }
//    sql.append(" unitCode = '${unitCode}' ")
//    if(unitCode.length > 12){
//        sql.append(" or unitCode = '${unitCode.substring(0, unitCode.length - 12)}' ")
//    }
//    if(unitCode.length > 6){
//        sql.append(" or unitCode = '${unitCode.substring(0, unitCode.length - 6)}' ")
//    }
//
//    val unitDao = DaoFactory.instant.getUnitDao()
//    val units = unitDao.queryBuilder()
//            .where(WhereCondition.StringCondition(sql.toString())).build().list()
//
//    var name = ""
//    units.forEach {
//        name += it.unitName+"_"
//    }
//
//    return  name.substring(0,name.length - 1)
//}
//
///**
// * 是否是乡镇管理员用户
// */
//fun isAble(): Boolean {
//
//    val user = getUser()
//    if(user == null){
//        return false
//    }else{
//        if("1" == user.role?.roleGroup && (user.unit?.unitCode?.length!! > 23)){
//            return true
//        }
//    }
//    return false
//}
//
///**
// * 提交按钮 控制开关
// * 是否是区县乡镇管理员用户
// * 放开到区县  允许修改基础信息 状况信息
// */
//fun isAble2(): Boolean {
//
//    val user = getUser()
//    if(user == null){
//        return false
//    }else{
//        if("1" == user.role?.roleGroup && (user.unit?.unitCode?.length!! >= 23)){
//            return true
//        }
//    }
//    return false
//}
//
///**
// * 获取 UnitTree 节点数据
// */
//fun getUnitNode(unitCode: String, level: Int): MutableList<Unit> {
//    val sql = "length(unitCode)==${unitCode.length + level* 6}"
//
//    val list = DaoFactory.instant.getUnitDao().queryBuilder().where(
//            UnitDao.Properties.UnitCode.like("$unitCode%"),
//            WhereCondition.StringCondition(sql)
//    ).build().list()
//    return list
//}
//
///**
// * 获取 SitCode 节点数据
// */
//fun getSitCodeNode(code: String?=null): MutableList<SitCode> {
//    return if(code.isNullOrEmpty()){
//        DaoFactory.instant.getSitCodeDao().queryBuilder().where(
//                WhereCondition.StringCondition("length(code)==2")
//        ).build().list()
//    }else{
//        DaoFactory.instant.getSitCodeDao().queryBuilder().where(
//                SitCodeDao.Properties.Code.like("${code}_")
//        ).build().list()
//    }
//
//}
//
//fun isFund(type:String,code:String,identNum:String?):Boolean{
//    return if("radTxtGrp" == type){
//        //在特殊项目中,年龄大于17的 不填资金
//        !(isEquals(code,"1CEC|1CEA|1CEB|1CFA|1CFC|1CGA|1CGB|1CJA|1CKA|1CKB|1CKC|1CKD")
//                && getAge(identNum) > 17)
//    }else{
//        false
//    }
//}
//
///**
// * 获取 SerCode 节点数据
// */
//fun getSerCodeNode(code: String?=null, mUnitCode:String? = null): MutableList<SerCode> {
//
//    val unitCode = if(mUnitCode.isNullOrEmpty()){
//        getUser()?.unit?.unitCode?:""
//    }else{
//        mUnitCode
//    }
//
//
//    return if(!code.isNullOrEmpty()){
//
//        val sql2 = if(code.startsWith("3")){
//            "code LIKE '${code}_' AND (unitScope='0' OR unitScope = '${unitCode.substring(0,17)}')"
//        }else if(code.startsWith("4")){
//            "code LIKE '${code}_' AND (unitScope='0' OR unitScope = '${unitCode.substring(0,23)}')"
//        }else{
//            "code like '${code}_'"
//        }
//
//        DaoFactory.instant.getSerCodeDao().queryBuilder().where(
//                WhereCondition.StringCondition(sql2)
//        ).build().list()
//
//    }else{
//        var sql = "code = '1' "
//        if(unitCode.length >= 17){
//            sql ="$sql OR (code = '3' AND (unitScope='0' OR unitScope = '${unitCode.substring(0,17)}')) "
//        }
//        if(unitCode.length >= 23){
//            sql ="$sql OR (code = '4' AND (unitScope='0' OR unitScope = '${unitCode.substring(0,23)}')) "
//        }
//
//        DaoFactory.instant.getSerCodeDao().queryBuilder().where(
//                WhereCondition.StringCondition(sql)
//        ).build().list()
//    }
//}
//
///**
// * 获取 SerCode 节点数据
// * 默认 加载需求项
// */
//fun getSerCodeNode(code: String?=null, mUnitCode:String? = null,isReq:Boolean = true): MutableList<SerCode> {
//
//    val unitCode = if(mUnitCode.isNullOrEmpty()){
//        getUser()?.unit?.unitCode?:""
//    }else{
//        mUnitCode
//    }
//
//
//    return if(!code.isNullOrEmpty()){
//
//        var sql = if(code.startsWith("3")){
//            "code LIKE '${code}_' AND (unitScope='0' OR unitScope = '${unitCode.substring(0,17)}')"
//        }else if(code.startsWith("4")){
//            "code LIKE '${code}_' AND (unitScope='0' OR unitScope = '${unitCode.substring(0,23)}')"
//        }else{
//            "code like '${code}_'"
//        }
//
//        sql = if(isReq){
//            "$sql AND reqIndeed = '2' "
//        }else{
//            "$sql AND reqIndeed = '1' "
//        }
//
//
//        DaoFactory.instant.getSerCodeDao().queryBuilder().where(
//            WhereCondition.StringCondition(sql)
//        ).build().list()
//
//    }else{
//        var sql = "code = '1' "
//        if(unitCode.length >= 17){
//            sql ="$sql OR (code = '3' AND (unitScope='0' OR unitScope = '${unitCode.substring(0,17)}')) "
//        }
//        if(unitCode.length >= 23){
//            sql ="$sql OR (code = '4' AND (unitScope='0' OR unitScope = '${unitCode.substring(0,23)}')) "
//        }
//
//        sql = if(isReq){
//            "($sql) AND reqIndeed = '2' "
//        }else{
//            "($sql) AND reqIndeed = '1' "
//        }
//
//        DaoFactory.instant.getSerCodeDao().queryBuilder().where(
//            WhereCondition.StringCondition(sql)
//        ).build().list()
//    }
//}
//
//fun getMapToStr(codeStr: String?): HashMap<String, String> {
//    val codeMap = HashMap<String,String>()
//    if(!codeStr.isNullOrEmpty()){
//        val codeArr = codeStr.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//        for (code in codeArr) {
//            val cv = code.split("\\/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//            codeMap[cv[0].replace("$", "")] = cv[1].replace("&", "")
//        }
//    }
//    return codeMap
//}
//
//fun getStrToMap(codeMap: Map<String, String>?): String {
//    val codeBuilder = StringBuilder()
//    if (codeMap != null && codeMap.isNotEmpty()) {
//        for ((key, value) in codeMap) {
//            codeBuilder.append("|").append("$").append(key).append("$")
//                    .append("/").append("&").append(value).append("&")
//        }
//    }
//    if (codeBuilder.isNotEmpty()) {
//        codeBuilder.deleteCharAt(0)
//    }
//    return codeBuilder.toString()
//}
//
///**
// * 拨打电话
// */
//fun callPhone(phone:String){
//    try {
//        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        App.context.startActivity(intent)
//    }catch (e:Exception){
//        myToast("拨号失败！")
//    }
//}
//
///**
// * 获取年龄
// */
//fun getAge(indentNum:String?):Int{
//    if(isIdentNum(indentNum)){
//        val identNum = IdentNum(indentNum!!)
//        val age = identNum.age
//        return age
//    }
//    return 0
//}
//
///**
// * 获取生日
// */
//fun getBirthdate(indentNum:String?):String{
//    if(!indentNum.isNullOrEmpty()){
//        val identNum = IdentNum(indentNum)
//        val birthdate = identNum.bithdateStr
//        return birthdate
//    }
//    return ""
//}
//
//fun getSex(indentNum:String?):Int?{
//    if(!indentNum.isNullOrEmpty()){
//        val identNum = IdentNum(indentNum)
//        val sex = identNum.sex
//        return sex
//    }
//    return null
//}
//
//fun getLevel(unitCode: String): Int {
//    var level = 0
//    when (unitCode.length) {
//        11 -> level = 1
//        17 -> level = 2
//        23 -> level = 3
//        29 -> level = 4
//        35 -> level = 5
//    }
//    return level
//}
//
///**
// * 获取日历的一个实例，里面包含了当前的年月日
// */
//fun getCurrentMonth(): Int {
//    //返回当前月
//    val calendar: Calendar = Calendar.getInstance()
//    return calendar.get(Calendar.MONTH) +1
//}
//
//fun getStaffDaoSeesion()=DaoFactory.instant.getStaffDaoSeesion()
//fun getDisBaseDao()=DaoFactory.instant.getDisBaseDao()
//fun getSerMonthFundDao()=DaoFactory.instant.getSerMonthFundDao()
//fun getSerMonthNumDao()=DaoFactory.instant.getSerMonthNumDao()
//fun getDisDetailDao()=DaoFactory.instant.getDisDetailDao()
//fun getBaseSurveyDao()=DaoFactory.instant.getBaseSurveyDao()
//fun getDisCodeDao()=DaoFactory.instant.getDisCodeDao()
//fun getDictDao()=DaoFactory.instant.getDictDao()
//fun getUnitDao()=DaoFactory.instant.getUnitDao()
//fun getSitCodeDao()=DaoFactory.instant.getSitCodeDao()
//fun getSerCodeDao()=DaoFactory.instant.getSerCodeDao()
