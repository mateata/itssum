package con.jwlee.itssum.data

import android.content.Context
import con.jwlee.itssum.util.DLog
import con.jwlee.itssum.util.Util
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class CommunityParser {

    fun goodParser(context : Context): ArrayList<GoodData> {

        var goodData : GoodData = GoodData("","","","","","","",false,false,"",0,"",0,"",0)
        val itemlist = ArrayList<GoodData>()


        val assetManager = context.resources.assets;
        val inputStream= assetManager.open("good.xml")

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

                    if (tagName.equals("row")){ //각 열 태그(Row) 시작시 객체 초기화
                        goodData = GoodData("","","","","","","",false,false,"",0,"",0,"",0)
                    } else if (tagName.equals("업종")) {
                        tagID = 1;
                    } else if (tagName.equals("업소명")) {
                        tagID = 2;
                    } else if (tagName.equals("구")) {
                        tagID = 3;
                    } else if (tagName.equals("주소")) {
                        tagID = 4;
                    } else if (tagName.equals("연락처")) {
                        tagID = 5;
                    } else if (tagName.equals("업소자랑거리")) {
                        tagID = 6;
                    } else if (tagName.equals("영업시간")) {
                        tagID = 7;
                    } else if (tagName.equals("배달여부")) {
                        tagID = 8;
                    } else if (tagName.equals("주차여부")) {
                        tagID = 9;
                    } else if (tagName.equals("품목1")) {
                        tagID = 10;
                    } else if (tagName.equals("가격1")) {
                        tagID = 11;
                    } else if (tagName.equals("품목2")) {
                        tagID = 12;
                    } else if (tagName.equals("가격2")) {
                        tagID = 13;
                    } else if (tagName.equals("품목3")) {
                        tagID = 14;
                    } else if (tagName.equals("가격3")) {
                        tagID = 15;
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.getName().equals("row")){ //각 열 태그(Row) 종료시 리스트에 add
                        DLog().e(goodData.toString())
                        itemlist.add(goodData);
                    }
                    tagID = 0; // tagID 는 어떤 태그가 끝나든 초기화
                }
                XmlPullParser.TEXT -> {
                    if (!"".equals(parser.getText().trim())) {
                        when(tagID) {
                            1 -> { goodData.sector = parser.getText().trim() }
                            2 -> { goodData.name = parser.getText().trim() }
                            3 -> { goodData.place = parser.getText().trim() }
                            4 -> { goodData.address = parser.getText().trim() }
                            5 -> { goodData.phone = parser.getText().trim() }
                            6 -> { goodData.intro = parser.getText().trim() }
                            7 -> { goodData.time = parser.getText().trim() }
                            8 -> { goodData.delivery = if(parser.getText().trim().equals("Y")) {true} else {false}  }
                            9 -> { goodData.parking = if(parser.getText().trim().equals("Y")) {true} else {false}  }
                            10 -> { goodData.itemName1 = parser.getText().trim() }
                            11 -> { goodData.itemval1 = Util().setNumber(parser.getText().trim()) }
                            12 -> { goodData.itemName2 = parser.getText().trim() }
                            13 -> { goodData.itemval2 = Util().setNumber(parser.getText().trim()) }
                            14 -> { goodData.itemName3 = parser.getText().trim() }
                            15 -> { goodData.itemval3 = Util().setNumber(parser.getText().trim()) }
                        }
                    }
                }
            }
            eventType = parser.next();
        }

        return itemlist;

    }
}