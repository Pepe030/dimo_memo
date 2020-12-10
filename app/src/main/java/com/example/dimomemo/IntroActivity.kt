package com.example.dimomemo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity

// test
class IntroActivity : AppCompatActivity() {

    var handler: Handler? = null    // Handler: Runnable을 실행하는 클래스
    var runnable: Runnable? = null  // Runnable: 병렬 실행이 가능한 Thread를 만들어주는 클래스스

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        /**
         * 안드로이드 앱을 띄우는 Window의 속성을 변경하여
         * 시스템 UI를 숨기고 전체화면으로 표시하는 코드
          */
    }

    override fun onResume() {
        super.onResume()

        runnable = Runnable {
            val intent = Intent(applicationContext, ListActivity::class.java)
            startActivity(intent)
        }
        /**
         * Runnable이 실행되면 ListActivity로 이동하는 코드드
        */

        handler = Handler()
        handler?.run {
            postDelayed(runnable, 2000)
        }
        /**
         * Handler를 생성하고 2000m(2초)후 runnalbe을 실행
        */
    }

    override fun onPause() {
        super.onPause()

        handler?.removeCallbacks(runnable)
        /**
         * Activity Pause 상태일 때는 runnable도 중단하도록 함.
         */
    }
}