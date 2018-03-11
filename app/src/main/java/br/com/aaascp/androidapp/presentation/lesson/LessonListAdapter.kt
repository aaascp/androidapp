package br.com.aaascp.androidapp.presentation.lesson

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson
import kotlinx.android.synthetic.main.row_lesson_item.view.*

class LessonListAdapter(
        private val context: Context
) : PagedListAdapter<Lesson, LessonListAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Lesson>() {
            override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean =
                    oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
    ): LessonListAdapter.ViewHolder {

        val view =
                LayoutInflater.from(context)
                        .inflate(
                                R.layout.row_lesson_item,
                                parent,
                                false)
        return LessonListAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(
            holder: LessonListAdapter.ViewHolder,
            position: Int) {

        val lesson = getItem(position)
        val index = position + 1

        holder.count.text = "$index"
        holder.id.text = lesson?.id
        holder.title.text = lesson?.title
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val count: TextView = itemView.lesson_item_count
        val id: TextView = itemView.lesson_item_id
        val title: TextView = itemView.lesson_item_title
    }
}