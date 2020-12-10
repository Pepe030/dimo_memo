package com.example.dimomemo.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class MemoData (
    @PrimaryKey // id 속성 위에 @PrimaryKey 어노테이션 붙인다.
    var id: String = UUID.randomUUID().toString(),  // id: 메모의 고유 id
    var createdAt: Date = Date(),   // createdAt: 작성시간
    var title: String = "", // title: 제목
    var content: String = "",   // content: 내용
    var summary: String = "",   // summary: 내용요약
    var imageFile: String = "", // imageFile: 첨부이미지 파일이름
    var latitude: Double = 0.0, // latitude: 위도
    var longitude: Double = 0.0, // longitude: 경도
    var alarmTime: Date = Date(),   // alarmTime: 알람시간
    var weather: String = ""    // weather: 날씨
) : RealmObject()   // RealmObject를 상속받도록 함. 이때 반드시 open 클래스로 변경!

