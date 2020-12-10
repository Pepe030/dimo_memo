package com.example.dimomemo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dimomemo.data.ListViewModel
import com.example.dimomemo.data.MemoListAdapter
import kotlinx.android.synthetic.main.fragment_memo_list.*

class MemoListFragment : Fragment() {

    // MemoListAdapter와 ListViewModel을 담을 속성을 선언
    private lateinit var listAdapter: MemoListAdapter
    private var viewModel: ListViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memo_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // ViewModel을 가져오는 코드
        viewModel = activity!!.application!!.let {
            ViewModelProvider(
                activity!!.viewModelStore,
                ViewModelProvider.AndroidViewModelFactory(it))
                .get(ListViewModel::class.java)
            /**
             * activity!!.application: 애플리케이션 객체는 액티비티를 참조하는 객체에서 가져와서 사용
             * activity!!.viewModelStore: 액티비티의 속성을 가져온다.
             */
        }

        viewModel!!.let {
            // MemoLiveData를 가져와서 Adapter에 담아 RecyclerView에 출력하도록 함.
            it.memoLiveData.value?.let {
                listAdapter = MemoListAdapter(it)
                memoListView.layoutManager =
                    LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                memoListView.adapter = listAdapter
                // ListAdapter에 itemClickListener에서 DetailActivity로 이동하도록 한다.
                listAdapter.itemClickListener = {
                    val intent = Intent(activity, DetailActivity::class.java)
                    // 동시에 아이템을 클릭할 때 받은 메모id도 함께 전달한다.
                    intent.putExtra("MEMO_ID", it)
                    startActivity(intent)
                }
            }
            // MemoLiveData에 observe 함수를 통해 값이 변할 때 동작할 옵저버를 붙여줌
            // Observer 내에서는 adapter의 갱신 코드를 호출한다.
            it.memoLiveData.observe(this,
                Observer {
                    listAdapter.notifyDataSetChanged()
            })
        }
    }

    // 메모를 작성하고 돌아왔을 때 리스트가 갱신되도록 onResume() 함수를 override함.
    override fun onResume() {
        super.onResume()
        listAdapter.notifyDataSetChanged()
    }
}