package com.example.dimomemo.data

import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import java.util.*

/**
 * DAO : Data Access Object
 * 데이터베이스에 직접 접근하는 대신 필요한 쿼리를 함수로 미리 작성해두어
 * 쿼리의 재사용성을 높이기 위한 클래스이다.
 */

class MemoDao(private val realm: Realm) { // MemoDao 클래스를 만들고 생성자에서 Realm 인스턴스를 받도록 함

    /**
     * getAllMemos() : 함수에서는 DB에 담긴 MemoData를 생성시간의 역순으로 정렬하여 받아옴
     */
    fun getAllMemos(): RealmResults<MemoData> {
        return realm.where(MemoData::class.java)
            .sort("createdAt", Sort.DESCENDING)
            .findAll()
    }

    /**
     * selectMemo() : 지정된 id의 메모를 가져와서 반환하는 함수
     */
    fun selectMemo(id: String): MemoData {
        return realm.where(MemoData::class.java)
            .equalTo("id", id)
            .findFirst() as MemoData
    }

    /**
     * addOrUpdateMemo(): 메모를 생성하거나 수정하는 함수
     */
    fun addOrUpdateMemo(memoData: MemoData, title: String, content: String) {
        /*
        쿼리 구문.
        DB를 업데이트 하는 쿼리는 반드시 executeTransaction() 함수로 감싸야 함.
        executeTransaction()으로 감싸면 쿼리가 끝날 때까지 DB를 안전하게 사용 가능
        */
        realm.executeTransaction {
            memoData.title = title
            memoData.content = content
            memoData.createdAt = Date()

            /* 메모 데이터의 각종 내용들을 변경하고 */
            if (content.length > 100)
                memoData.summary = content.substring(0..100)
            else
                memoData.summary = content

            /* Managed 상태가 아닌 객체는 copyToRealm() 함수로 DB에 추가 */
            if (!memoData.isManaged) {
                it.copyToRealm(memoData)
            }
        }
    }
}
