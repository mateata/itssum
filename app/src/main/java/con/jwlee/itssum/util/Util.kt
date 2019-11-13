package con.jwlee.itssum.util

import android.content.Context
import android.widget.Toast

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

}