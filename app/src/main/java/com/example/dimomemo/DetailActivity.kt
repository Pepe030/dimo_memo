package com.example.dimomemo

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dimomemo.data.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_scrolling.*

class DetailActivity : AppCompatActivity() {

    // DetailViewModel을 위한 변수를 생성
    private var viewModel: DetailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
//        findViewById<CollapsingToolbarLayout>(R.id.toolbarlayout).title = title
//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        // viewModel을 생성
        viewModel = application!!.let {
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(DetailViewModel::class.java)
        }

        // 제목과 내용이 변경될 때 화면에 갱신할 수 있도록 한다.
        viewModel!!.let {
            it.title.observe(this, Observer { supportActionBar?.title = it } )
            it.content.observe(this, Observer { contentEdit.setText(it) } )
        }

        // ListActivity에서 특정한 메모가 선택되어 왔을 때 id를 받아 메모를 로딩해준다.
        // 새로작성할때는 메모 ID가 없기때문에 동작하지 않는다.
        val memoId = intent.getStringExtra("MEMO_ID")
        if(memoId != null) viewModel!!.loadMemo(memoId)

        // 툴바 부분을 터치하면 제목을 작성할 수 있도록 한다.
        toolbarlayout.setOnClickListener {
            // LayoutInflater를 통해 아까 작성한 dialog_title을 뷰로 만든다.
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_title, null)
            // 이 뷰에서 findViewById 함수를 통해 EditText인 titleEdit을 변수로 가져온다
            val titleEdit = view.findViewById<EditText>(R.id.titleEdit)
            // 다이얼로그를 만들수 있는 얼럿다이얼로그 빌더를 통해 다이얼로그를 만들어 띄운다.
            AlertDialog.Builder(this)
                .setTitle("제목을 입력하세요")  // 제목을 지정
                .setView(view)  // 방금 얻어온 뷰를 설정하고
                .setNegativeButton("취소", null)  // 취소버튼과 확인버튼을 만든다.
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                    supportActionBar?.title = titleEdit.text.toString()
                    toolbarlayout.title = titleEdit.text.toString()
                }).show()   // 확인버튼에는 눌렀을 때 동작하는 리스너를 추가한다. 입력한 텍스트를 제목으로 설정
            // 그리고 화면에 띄운다.
        }


    }

    // 뒤로가기 버튼을 눌렀을 때 동작하는 onBackPressed() 함수를 override
    override fun onBackPressed() {
        super.onBackPressed()

        // 뒤로가기를 할때 viewModel의 addOrUpdateMemo()를 호출하여 변경된 값들을 바로 저장
        viewModel?.addOrUpdateMemo(
            supportActionBar?.title.toString(),
            contentEdit.text.toString()
        )
    }
}