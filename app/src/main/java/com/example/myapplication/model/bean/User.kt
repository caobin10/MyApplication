package com.example.myapplication.model.bean

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName(value = "Role")
    var role: Role? = null
    @SerializedName(value = "Unit")
    var unit: Unit? = null
    @SerializedName(value = "UserKind")
    var userKind: UserKind = UserKind.UserPlat
    @SerializedName(value = "LoginErrorRecord")
    var loginErrorRecord: String? = null
    @SerializedName(value = "LoginErrorTime")
    var loginErrorTime: String? = null
    @SerializedName(value = "ID")
    var id: String? = null
    @SerializedName(value = "UserName")
    var userName: String = ""
    @SerializedName(value = "PassWord")
    var passWord: String = ""
    @SerializedName(value = "UnitID")
    var unitID: String? = null
    @SerializedName(value = "RoleID")
    var roleID: String? = null
    @SerializedName(value = "DeleteFlag")
    var deleteFlag: Boolean? = null
    @SerializedName(value = "UpdateTime")
    var updateTime: String? = null
    @SerializedName(value = "CreateTime")
    var createTime: String? = null

    //残疾朋友
    @SerializedName(value = "DisableID")
    var disableId: String? = null

    constructor()
    constructor( userName: String) {
        this.userName = userName
    }
    constructor( userName: String, passWord: String,userKind: UserKind) {
        this.userName = userName
        this.passWord = passWord
        this.userKind = userKind
    }
}

class Unit{
    @SerializedName(value = "UnitCode")
    var unitCode: String? = null
    @SerializedName(value = "UnitName")
    var unitName: String? = null
    @SerializedName(value = "ParentCode")
    var parentCode: String? = null
    @SerializedName(value = "Remark")
    var remark: String? = null
    @SerializedName(value = "UnitLevel")
    val unitLevel: String? = null

    constructor(unitCode: String?, unitName: String?, parentCode: String?, remark: String?) {
        this.unitCode = unitCode
        this.unitName = unitName
        this.parentCode = parentCode
        this.remark = remark
    }

}

class Role{
    @SerializedName(value = "ID")
    var id: String? = null
    @SerializedName(value = "RoleCode")
    var roleCode: String? = null
    @SerializedName(value = "RoleName")
    var roleName: String? = null
    @SerializedName(value = "RoleGroup")
    var roleGroup: String? = null
    @SerializedName(value = "Remark")
    var remark: String? = null
    @SerializedName(value = "UpdateTime")
    var updateTime: String? = null
    @SerializedName(value = "CreateTime")
    var createTime: String? = null
    @SerializedName(value = "DeleteFlag")
    var deleteFlag: Boolean? = null
}

class UserInfo{
    @SerializedName(value = "ID")
    var id: String? = null
    @SerializedName(value = "DeleteFlag")
    var deleteFlag: Boolean? = null
    @SerializedName(value = "UpdateTime")
    var updateTime: String? = null
    @SerializedName(value = "CreateTime")
    var createTime: String? = null
    @SerializedName(value = "UserID")
    var userID: String? = null
    @SerializedName(value = "Imei")
    var imei: String? = null
    @SerializedName(value = "Phone_Model")
    var phoneModel: String? = null
    @SerializedName(value = "OS_Ver")
    var osVer: String? = null
    @SerializedName(value = "IP")
    var ip: String? = null

    constructor()
}

enum class UserKind {
    UserPlat,
    DisableUser,
    Volunteer
}