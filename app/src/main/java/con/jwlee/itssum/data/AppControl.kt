package con.jwlee.itssum.data

class AppControl {

    val DEBUG = true; // 디버그/릴리즈모드 표기(약식)

    // 고정값

    //각 xml 파일 버전 및 키값(차후 버전업이 잦아지면 날짜등으로 변경)
    val bigDBversion = 1 // 대형마트
    val oldDBversion = 1 // 재래시장
    val expDBversion = 1 // 기업형슈퍼(홈플 익프)
    val goodDBversion = 1 // 인천 착한가격업소

    val bigDBKey = "bigDBver"
    val oldDBKey = "oldDBver"
    val expDBKey = "expDBver"
    val goodDBKey = "goodDBver"

    val locationKey = "locationKey"

    //인천 e음카드 할인률 : 2019.11월 기준
    val incheonCashbag = 0.97 // 인천 전체 3%, 구별로 다른 적립율은 아래 변수추가
    val westCashbag = 0.93  // 서구 7%, 30만원 이하 기준
    val yeonsuCashbag = 0.90 // 연수구 10%, 30만원 이하 기준

    // 변동값 :

    var setLocation = 4; // 1 : 중구, 2 : 동구, 3 : 미추홀구, 4 : 연수구, 5: 남동구, 6: 부평구, 7: 계양구, 8: 서구 // 기본값 : 연수구
    var mGubun = 0; // 0: 대형마트, 1: 재래시장

}