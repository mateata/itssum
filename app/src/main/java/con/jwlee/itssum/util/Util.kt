package con.jwlee.itssum.util

import android.content.Context
import android.widget.Toast
import android.content.Intent
import android.content.pm.ResolveInfo



class Util {

    // 문자열을 Int로. 만약 변환안되는 값이면 0으로 리턴
    fun setNumber(str: String): Int {
        var result = 0;

        try {
            val tempStr = str.replace(",", "")
            result = tempStr.toInt()
        } catch (e: Exception) {

        }

        return result
    }

    fun toastLong(context : Context, str : String) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show()
    }

    fun getPackageList(context: Context): Boolean {
        var isExist = false

        val pkgMgr = context.getPackageManager()
        val mApps: List<ResolveInfo>
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        mApps = pkgMgr.queryIntentActivities(mainIntent, 0)

        try {
            for (i in mApps.indices) {
                if (mApps[i].activityInfo.packageName.equals("gov.incheon.incheonercard")) {
                    isExist = true
                    break
                }
            }
        } catch (e: Exception) {
            isExist = false
        }

        return isExist
    }
}