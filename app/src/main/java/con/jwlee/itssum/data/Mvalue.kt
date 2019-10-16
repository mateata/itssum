package con.jwlee.itssum.data

data class Mvalue (
    var gubun : String,  //구분
    var item : String,   //품목
    var name : String,   //물건명,단위
    var west : Int,     //서구
    var east : Int,     //동구
    var middle : Int,   //중구
    var southeast : Int, //남동구
    var michuhol : Int,  //미추홀구
    var yeonsu : Int,    //연수구
    var bupyeong : Int,  //부평구
    var geyang : Int     //계양구

    )