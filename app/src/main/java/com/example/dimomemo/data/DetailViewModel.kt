package com.example.dimomemo.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.realm.Realm

// DetailActivty에서 사용하는 ViewModel

class DetailViewModel: ViewModel() { // ViewModel을 상속받아 DetailViewModel 클래스를 작성
    // 제목과 내용에 로드할 내용을 MutableLiveData로 선언한다.
    // 객체 생성시에는 value값을 빈 값으로 변경하자.
    val title: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    val content: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    // 나중에 저장할 메모데이터 객체를 미리 만들어준다.
    private var memoData = MemoData()

    // ListViewModel에서 처럼 Realm과 memoDao를 초기화하고
    private val realm: Realm by lazy { Realm.getDefaultInstance() }
    private val memoDao: MemoDao by lazy { MemoDao(realm) }
    // 종료할 때 닫아주는 코드를 넣는다.
    override fun onCleared() {
        super.onCleared()
        realm.close()
    }

    // 기존 메모를 수정하는 경우를 생각하여 메모의 id가 주어지면 메모의 내용을 로드하도록 한다.
    fun loadMemo(id: String) {
        memoData = memoDao.selectMemo(id)
        title.value = memoData.title
        content.value = memoData.content
    }

    // 메모의 추가나 수정을 위한 함수를 MemoDao와 연결시켜준다.
    fun addOrUpdateMemo(title: String, content: String) {
        memoDao.addOrUpdateMemo(memoData, title, content)
    }
}