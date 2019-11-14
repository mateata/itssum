package con.jwlee.itssum.util

import android.util.Log
import con.jwlee.itssum.data.AppControl


class DLog {
    val TAG = "ITERROR"

    /** Log Level Error  */
    fun e(message: String) {
        if (AppControl.DEBUG) Log.e(TAG, buildLogMsg(message))
    }

    /** Log Level Warning  */
    fun w(message: String) {
        if (AppControl.DEBUG) Log.w(TAG, buildLogMsg(message))
    }

    /** Log Level Information  */
    fun i(message: String) {
        if (AppControl.DEBUG) Log.i(TAG, buildLogMsg(message))
    }

    /** Log Level Debug  */
    fun d(message: String) {
        if (AppControl.DEBUG) Log.d(TAG, buildLogMsg(message))
    }

    /** Log Level Verbose  */
    fun v(message: String) {
        if (AppControl.DEBUG) Log.v(TAG, buildLogMsg(message))
    }

    fun buildLogMsg(message: String): String {
        val ste = Thread.currentThread().stackTrace[4]
        val sb = StringBuilder()
        sb.append("[")
        sb.append(ste.fileName!!.replace(".kt", ""))
        sb.append("::")
        sb.append(ste.methodName)
        sb.append("]")
        sb.append(message)
        return sb.toString()
    }
}