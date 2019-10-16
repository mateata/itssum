package con.jwlee.itssum.util

class Util {

    fun setNumber(str: String): Int {
        var result = 0;

        try {
            result = Integer.parseInt(str)
        } catch (e: Exception) {

        }

        return result
    }
}