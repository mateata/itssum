package con.jwlee.itssum.data

import android.content.Context
import con.jwlee.itssum.util.DLog
import con.jwlee.itssum.util.Util
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory


class MarketParser {

    //
    val bigXml = "bigmarket.xml";
    val oldXml = "oldmarket.xml";
    val expXml = "expmarket.xml";

    fun mParser(xmlName : String, context : Context ): ArrayList<Mvalue> {

        var mValue : Mvalue = Mvalue("","","",0,0,0,0,0,0,0,0,0)
        val itemlist = ArrayList<Mvalue>()


        val assetManager = context.resources.assets;
        val inputStream= assetManager.open(xmlName)

        val factory = XmlPullParserFactory.newInstance()
		var parser = factory.newPullParser()
		parser.setInput(inputStream, "UTF-8")

        // 파서 관련 필요 변수
        var eventType = parser.getEventType()
        var tagID = 0;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when(eventType) {
                XmlPullParser.START_DOCUMENT -> {
                    //현재 공란
                }
                XmlPullParser.END_DOCUMENT -> {
                    //현재 공란
                }
                XmlPullParser.START_TAG -> {
                    var tagName = parser.getName();

                    if (tagName.equals("Row")){ //각 열 태그(Row) 시작시 객체 초기화
                        mValue = Mvalue("","","",0,0,0,0,0,0,0,0,0)
                    } else if (tagName.equals("구분")) {
                        tagID = 1;
                    } else if (tagName.equals("품목")) {
                        tagID = 2;
                    } else if (tagName.equals("규격및단위")) {
                        tagID = 3;
                    } else if (tagName.startsWith("중구")) { // 구별 가격부터는 기준가게가 바뀔수 있으므로 startWith로 처
                        tagID = 4;
                    } else if (tagName.startsWith("동구")) {
                        tagID = 5;
                    } else if (tagName.startsWith("미추홀구")) {
                        tagID = 6;
                    } else if (tagName.startsWith("연수구")) {
                        tagID = 7;
                    } else if (tagName.startsWith("남동구")) {
                        tagID = 8;
                    } else if (tagName.startsWith("부평구")) {
                        tagID = 9;
                    } else if (tagName.startsWith("계양구")) {
                        tagID = 10;
                    } else if (tagName.startsWith("서구")) {
                        tagID = 11;
                    } else if (tagName.startsWith("금주가격_평균")) {
                        tagID = 12;
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.getName().equals("Row")){ //각 열 태그(Row) 종료시 리스트에 add
                        DLog().e(mValue.toString())
                        itemlist.add(mValue);
                    }
                    tagID = 0; // tagID 는 어떤 태그가 끝나든 초기화
                }
                XmlPullParser.TEXT -> {
                    if (!"".equals(parser.getText().trim())) {
                        when(tagID) {
                            1 -> { mValue.gubun = parser.getText().trim() }
                            2 -> { mValue.item = parser.getText().trim().replace(" ", "") }
                            3 -> { mValue.name = parser.getText().trim() }
                            4 -> { mValue.middle = Util().setNumber(parser.getText().trim()) }
                            5 -> { mValue.east = Util().setNumber(parser.getText().trim()) }
                            6 -> { mValue.michuhol = Util().setNumber(parser.getText().trim()) }
                            7 -> { mValue.yeonsu = Util().setNumber(parser.getText().trim()) }
                            8 -> { mValue.southeast = Util().setNumber(parser.getText().trim()) }
                            9 -> { mValue.bupyeong = Util().setNumber(parser.getText().trim()) }
                            10 -> { mValue.geyang = Util().setNumber(parser.getText().trim()) }
                            11 -> { mValue.west = Util().setNumber(parser.getText().trim()) }
                            12 -> { mValue.average = Util().setNumber(parser.getText().trim()) }
                        }
                    }
                }
            }
            eventType = parser.next();
        }

        return itemlist;

    }
}