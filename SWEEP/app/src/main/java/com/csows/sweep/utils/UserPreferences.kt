package com.csows.sweep.utils

import android.content.Context
import android.content.SharedPreferences
import com.csows.sweep.R

class UserPreferences {

    companion object{
        lateinit var pref: SharedPreferences
        lateinit var editor: SharedPreferences.Editor

        const val LOGIN = "Login"
        const val BRANCHNAME = "BranchName"
        const val ID = "iD"
        const val OFFICERMOBILE = "OfficerMobile"
        const val OFFICERNAME = "OfficerName"


      /*  const val MOBILE = "Mobile"
        const val UserName = "UserName"
        const val Password = "Password"
        const val AuthorityName = "AuthorityName"

        const val AuthorityMobile = "AuthorityMobile"
        const val AuthorityID = "AuthorityID"
        const val UserID = "UserID"
        const val DesignationList = "DesignationList"
        const val MarkingList = "MarkingList"
        const val OfcLati = "OfcLati"
        const val OfcLongi = "OfcLongi"
        const val ServiceList = "ServiceList"
        const val BranchList = "BranchList"*/

        /*{"LoginDetails":[{"BranchName":"sveep","ID":1,"OfficerMobile":"8285121470","OfficerName":"Susheel"}]}*/
    }

    constructor(context: Context) {
        val prefName = "pref_".plus(context.resources.getString(R.string.file_path))
        pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        editor = pref.edit()
    }

    fun setLogin(login: Boolean) {
        editor = pref.edit()
        editor.putBoolean(LOGIN, login).apply()
    }

    fun getLogin(): Boolean {
        return pref.getBoolean(LOGIN, false)
    }

    fun setBranchName(branchName: String) {
        editor = pref.edit()
        editor.putString(BRANCHNAME, branchName).apply()
    }

    fun getBranchName(): String? {
        return pref.getString(BRANCHNAME, "")
    }

    fun setID(id: Int) {
        editor = pref.edit()
        editor.putInt(ID, id).apply()
    }

    fun getID(): Int {
        return pref.getInt(ID, 0)
    }

    fun setOfficerMobile(officerMobile: String) {
        editor = pref.edit()
        editor.putString(OFFICERMOBILE, officerMobile).apply()
    }

    fun getOfficerMobile(): String? {
        return pref.getString(OFFICERMOBILE, "")
    }

    fun setOfficerName(officerName: String) {
        editor = pref.edit()
        editor.putString(OFFICERNAME, officerName).apply()
    }

    fun getOfficerName(): String? {
        return pref.getString(OFFICERNAME, "")
    }



    /* fun setMobile(mobile: String) {
         editor = pref.edit()
         editor.putString(MOBILE, mobile).apply()
     }

     fun getMobile(): String? {
         return pref.getString(MOBILE, "")
     }

     fun setUserName(userName: String) {
         editor = pref.edit()
         editor.putString(UserName, userName).apply()
     }

     fun getUserName(): String? {
         return pref.getString(UserName, "")
     }

     fun setPassword(password: String) {
         editor = pref.edit()
         editor.putString(Password, password).apply()
     }

     fun getPassword(): String? {
         return pref.getString(Password, "")
     }

    fun setAuthorityName(authName: String) {
         editor = pref.edit()
         editor.putString(AuthorityName, authName).apply()
     }

     fun getAuthorityName(): String?{
         return pref.getString(AuthorityName, "")
     }

     fun setAuthorityMobile(authMobile: String) {
         editor = pref.edit()
         editor.putString(AuthorityMobile, authMobile).apply()

     }

     fun getAuthorityMobile(): String?{
         return pref.getString(AuthorityMobile, "")
     }
     fun setAuthorityID(authID: String) {
         editor = pref.edit()
         editor.putString(AuthorityID, authID).apply()

     }

     fun getAuthorityID(): String?{
         return pref.getString(AuthorityID, "")
     }
     fun setUserID(userId: String) {
         editor = pref.edit()
         editor.putString(UserID, userId).apply()

     }

     fun getUserID(): String?{
         return pref.getString(UserID, "")
     }
     fun setDesignationList(desigList: ArrayList<LoginDetail>) {
         val gson = Gson()
         val designationListJson = gson.toJson(desigList)
         editor = pref.edit()
         editor.putString(DesignationList, designationListJson).apply()

     }

     fun getDesignationList(): String?{
         return pref.getString(DesignationList, "")
     }
     fun setMarkingList(markingList: String) {
         editor = pref.edit()
         editor.putString(MarkingList, markingList).apply()

     }

     fun getMarkingList(): String?{
         return pref.getString(MarkingList, "")
     }
     fun setOfcLati(ofcLati: String) {
         editor = pref.edit()
         editor.putString(OfcLati, ofcLati).apply()

     }

     fun getOfcLati(): String?{
         return pref.getString(OfcLati, "")
     }
     fun setOfcLongi(ofcLongi: String) {
         editor = pref.edit()
         editor.putString(OfcLongi, ofcLongi).apply()

     }

     fun getOfcLongi(): String?{
         return pref.getString(OfcLongi, "")
     }

     fun setServiceList(serviceList: String) {
         editor = pref.edit()
         editor.putString(ServiceList, serviceList).apply()

     }

     *//* fun getServiceList(): String?{
         return pref.getString(ServiceList, "")
     } *//*

    fun getServiceList(): Array<String?> {
        return pref.getString(ServiceList, "")!!.split(",").toTypedArray()
    }

    fun setBranchList(branchList: String) {
        editor = pref.edit()
        editor.putString(BranchList, branchList).apply()
    }

    fun getBranchList(): Array<String?> {
      //  return pref.getString(BranchList, "")
        return pref.getString(BranchList, "")!!.split(",").toTypedArray()
    }
*/
}

