package con.jwlee.itssum.data

import java.io.Serializable

data class GoodData(
    var sector : String,
    var name : String,
    var place : String,
    var address : String,
    var phone : String,
    var intro : String,
    var time : String,
    var delivery : Boolean,
    var parking : Boolean,
    var previous : Boolean,
    var favorite : Boolean,
    var itemName1 : String,
    var itemval1 : Int,
    var itemName2 : String,
    var itemval2 : Int,
    var itemName3 : String,
    var itemval3 : Int
) : Serializable