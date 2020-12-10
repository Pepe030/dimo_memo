package com.example.dimomemo.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.realm.Realm

class ListViewModel : ViewModel() { //  : ViewModel() 이 부분 때문에 인터페이스 어쩌고 오류가 생겼었음.

    // Realm 인스턴스를 생성하여 사용하는 변수 생성
    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    // Realm 인스턴스를 넣어 MemoDao를 생성하여 사용하는 변수 생성
    private val memoDao: MemoDao by lazy {
        MemoDao(realm)
    }

    // MemoDao에서 모든 메모를 가져와서 RealmLiveData로 벼환하여 사용하는 변수 생성
    val memoLiveData: RealmLiveData<MemoData> by lazy {
        RealmLiveData<MemoData> (memoDao.getAllMemos())
    }

    // LiveViewModel이 더 이상 사용되지 않을 때 Realm 인스턴스를 닫아준다.
    override fun onCleared() {
        super.onCleared()
        realm.close()
    }

    /*
    /**
     *  MemoData의 MutableList를 저장하는 속성
     */
    private val memos: MutableList<MemoData> = mutableListOf()  // 메모 데이터가 담길 뮤터벌리스트 생성

    /**
     * MutableList를 담을 MutableLiveData를 추가
     * (성능을 위해서 lazy를 사용하여 지연 초기화)
     */
    val memoLiveData: MutableLiveData<MutableList<MemoData>> by lazy { // 뮤터벌리스트를 감싸서 사용할 뮤터벌라이브데이터 생성, 초기화를 지연하기 위해 레이지 사용
        MutableLiveData<MutableList<MemoData>>().apply {
            value = memos
        }
    }

    /**
     * 메모(MemoData 객체)를 리스트에 추가하고
     * MutableLiveData의 value를 갱신하여
     * Observer를 호출하도록 하는 함수
     */
    fun addMemo(data: MemoData) {   // value값이 새로 할당되어야 observer가 작동하기 때문에
        val tempList = memoLiveData.value   // 기존에 value(즉 MutableList)를 가져와서
        tempList?.add(data) // 메모 데이터를 추가한 후에
        memoLiveData.value = tempList   // 다시 value에 넣어줘야 한다.
    }

     */
}