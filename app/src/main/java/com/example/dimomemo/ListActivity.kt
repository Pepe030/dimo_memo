package com.example.dimomemo

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dimomemo.data.ListViewModel
import com.example.dimomemo.data.MemoData

import kotlinx.android.synthetic.main.activity_list.*
import java.util.*

class ListActivity : AppCompatActivity() {

    // 뷰모델을 가져올 속성을 만든다.
    private var viewModel: ListViewModel? = null    // ListViewModel을 담을 변수 추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intent = Intent(applicationContext, DetailActivity::class.java)
            startActivity(intent)
            /**
             * FAB를 누르면 DetailActivity로 이동하는 코드
             */
        }

        /**
         * MemoListFragment를 화면에 표시
         */
        val fragmentTransation = supportFragmentManager.beginTransaction()
        fragmentTransation.replace(R.id.contentLayout, MemoListFragment())
        fragmentTransation.commit()

        /**
         * ListViewModel을 가져오는 코드
         */
        // 뷰모델은 앱의 기준으로 생성.
        // 앱의 객체인 application이 null인지 먼저 체크한다.
        viewModel = application!!.let {
            // ViewModel을 가져오기 위해 ViewModelProvider 객체를 생성한다.
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(ListViewModel::class.java)
            /**
             * viewModelStore는 ViewModel의 생성과 소멸의 기준
             * ViewModelPactory는 ViewModel을 실제로 생성하는 객체
             * ViewModelProvide의 get함수를 통해서 ListViewModel을 얻을 수 있다.
             */
        }
    }
}
