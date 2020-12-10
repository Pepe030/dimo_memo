package com.example.dimomemo.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.RecyclerView
import com.example.dimomemo.R
import kotlinx.android.synthetic.main.item_memo.view.*
import java.text.SimpleDateFormat

/**
 * 생성자에서 MemoDate의 MutableList를 받음
 */
class MemoListAdapter (private val list: MutableList<MemoData>)
                    /* 메모를 담은 리스트를 생성자에서 받아야 함 */
    : RecyclerView.Adapter<ItemViewHolder>() {

    private val dateFormat = SimpleDateFormat("MM/dd HH:mm")
    /* Date 객체를 사람이 볼수 있는 문자열로 변환하기 위한 객체
    "MM/dd HH:mm" 월/일 시:분 으로 출력력*/

    // itemClickListener용 변수를 추가한다.
    lateinit var itemClickListener: (itemId: String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder {
        // item_memo 레이아웃을 로드한다.
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memo, parent, false)

        // 아이템이 클릭될 때 view의 tag에서 메모 id를 받아서 리스너에 넘긴다. (tag는 아래에서 추가함)
        view.setOnClickListener {
            itemClickListener?.run {
                val memoId = it.tag as String
                this(memoId)
            }
        }

        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
        /**
         * 리스트에 담긴 메모의 수를 반환한다.
         */
    }

    /**
     * 참고: View의 visibility의 종류
     * VISIBLE: View를 화면에 표시
     * INVISIBLE: View의 내용만 감추고 영역은 유지
     * GONE: View의 내용 및 영역까지 제거
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (list[position].title.isNotEmpty()) {
            holder.containerView.titleView.visibility = View.VISIBLE
            holder.containerView.titleView.text = list[position].title
        } else {
            holder.containerView.titleView.visibility = View.GONE
        }
        /**
         * 제목부터 표시한다. titleView에 표시
         * 제목이 있는 경우에는 titleView를 보이도록(VISIBLE) 하고
         * 제목이 없다면 titleView를 아예 감추도록(GONE) 한다.
         */
        holder.containerView.summaryView.text = list[position].summary
        // 요약내용(summary)를 그대로 출력하도록 한다. (summaryView에 표시함)
        holder.containerView.dateView.text = dateFormat.format(list[position].createdAt)
        // 작성시간(createAt)을 위에 만들어 놓은 SimpleDateFormat 객체로 포맷팅하여 출력한다.
        holder.containerView.tag = list[position].id
        // 아이템 view에 메모의 id를 설정해준다.
    }

}
