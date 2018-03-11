package br.com.aaascp.androidapp.presentation.lesson

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson
import kotlinx.android.synthetic.main.row_lesson_item.view.*

class LessonListAdapter(
        private val context: Context,
        private val lessons: List<Lesson>
) : RecyclerView.Adapter<LessonListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view =
                LayoutInflater.from(context)
                        .inflate(
                                R.layout.row_lesson_item,
                                parent,
                                false)
        return LessonListAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val lesson = lessons[position]
        holder?.let {
            it.id.text = lesson.id
            it.title.text = lesson.title
        }
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.lesson_item_id
        val title: TextView = itemView.lesson_item_title
    }
}