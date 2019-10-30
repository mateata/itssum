package con.jwlee.itssum.data

import android.os.Parcel
import android.os.Parcelable

data class Mvalue(
    var gubun: String,  //구분
    var item: String,   //품목
    var name: String,   //물건명,단위
    var middle: Int,   //중구
    var east: Int,     //동구
    var michuhol: Int,  //미추홀구
    var yeonsu: Int,    //연수구
    var southeast: Int, //남동구
    var bupyeong: Int,  //부평구
    var geyang: Int,     //계양구
    var west: Int,     //서구
    var average: Int     //평균가격(해당 항목 가격이 없을때 씀)

    ) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(gubun)
        parcel.writeString(item)
        parcel.writeString(name)
        parcel.writeInt(middle)
        parcel.writeInt(east)
        parcel.writeInt(michuhol)
        parcel.writeInt(yeonsu)
        parcel.writeInt(southeast)
        parcel.writeInt(bupyeong)
        parcel.writeInt(geyang)
        parcel.writeInt(west)
        parcel.writeInt(average)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mvalue> {
        override fun createFromParcel(parcel: Parcel): Mvalue {
            return Mvalue(parcel)
        }

        override fun newArray(size: Int): Array<Mvalue?> {
            return arrayOfNulls(size)
        }
    }

}