package con.jwlee.itssum.data

import androidx.appcompat.app.AppCompatActivity
import con.jwlee.itssum.util.Util
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory



class MarketParser : AppCompatActivity(){

    //
    val bigXml = "bigmarket.xml";
    val oldXml = "oldmarket.xml";


    fun mParser(xmlName : String): ArrayList<Mvalue> {

        var mValue : Mvalue = Mvalue("","","",0,0,0,0,0,0,0,0)
        val itemlist = ArrayList<Mvalue>()


        val assetManager = resources.assets
        val inputStream= assetManager.open(xmlName)

        val factory = XmlPullParserFactory.newInstance()
		val parser = factory.newPullParser()
		parser.setInput(inputStream, "UTF-8")

        var eventType = parser.getEventType()
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when(eventType) {
                XmlPullParser.START_DOCUMENT -> {
                    //현재 공란
                }
                XmlPullParser.END_DOCUMENT -> {
                    //현재 공란
                }
                XmlPullParser.START_TAG -> {
                    if (parser.getName().equals("Row")){ //각 열 태그(Row) 시작시 객체 초기화
                        mValue = Mvalue("","","",0,0,0,0,0,0,0,0)
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.getName().equals("Row")){ //각 열 태그(Row) 종료시 리스트에 add
                        itemlist.add(mValue);
                    }
                }
                XmlPullParser.TEXT -> {
                    if (parser.getName().equals("구분")){
                        mValue.gubun = parser.getText().trim()
                    } else if (parser.getName().equals("품목")){
                        mValue.item = parser.getText().trim()
                    } else if (parser.getName().equals("규격및단위")){
                        mValue.name = parser.getText().trim();
                    } else if (parser.getName().startsWith("중구_")){ // 구별 가격부터는 기준가게가 바뀔수 있으므로 startWith로 처
                        mValue.middle = Util().setNumber(parser.getText().trim())
                    } else if (parser.getName().startsWith("동구_")){
                        mValue.east = Util().setNumber(parser.getText().trim())
                    } else if (parser.getName().startsWith("미추홀구_")){
                        mValue.michuhol = Util().setNumber(parser.getText().trim())
                    } else if (parser.getName().startsWith("연수구_")){
                        mValue.yeonsu = Util().setNumber(parser.getText().trim())
                    } else if (parser.getName().startsWith("남동구_")){
                        mValue.southeast = Util().setNumber(parser.getText().trim())
                    } else if (parser.getName().startsWith("부평구_")){
                        mValue.bupyeong = Util().setNumber(parser.getText().trim())
                    } else if (parser.getName().startsWith("계양구_")){
                        mValue.geyang = Util().setNumber(parser.getText().trim())
                    } else if (parser.getName().startsWith("서구_")){
                        mValue.west = Util().setNumber(parser.getText().trim())
                    }
                }
            }
        }


        return itemlist;

    }
}