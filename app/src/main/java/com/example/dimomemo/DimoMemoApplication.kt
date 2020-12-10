package com.example.dimomemo

import android.app.Application
import io.realm.Realm

/* 앱을 제어하는 객체인 Application을 상속받아 DimoMemoApplication 클래스를 생성 */
class DimoMemoApplication() : Application() {

    /*
    앱 시작시 실행되는 onCreate()함수를 override하고 그 안에서 Realm 데이터베이스를 초기화 함.
     */
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}