package com.example.dimomemo.data

import androidx.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmObject
import io.realm.RealmResults

/**
 * RealmResult를 LiveData로 사용할 수 있는 클래스
 */

class RealmLiveData<T: RealmObject> (private val realmResults: RealmResults<T>)
    : LiveData<RealmResults<T>> () { // LiveData를 상속받아 class를 만들고 생성자에서 RealmReults를 받음
    
    init { // 받아온 realmResults를 value에 추가(Observe가 동작하도록 하기 위해서)
        value = realmResults
    }

    // RealmResult가 갱신될 때 동작할 리스너 작성 (갱신되는 값을 value에 할당함)
    private val listener = RealmChangeListener<RealmResults<T>> {
        value = it
    }

    // LiveData가 활성화될 때 realmResults에 리스너를 붙여줌
    override fun onActive() {
        super.onActive()
        realmResults.addChangeListener(listener)
    }

    // LiveData가 비활성화 될 때 리스너를 제거함
    override fun onInactive() {
        super.onInactive()
        realmResults.removeChangeListener(listener)
    }
}