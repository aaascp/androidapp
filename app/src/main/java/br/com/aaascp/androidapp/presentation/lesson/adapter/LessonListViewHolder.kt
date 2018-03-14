package br.com.aaascp.androidapp.presentation.lesson.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson
import br.com.aaascp.androidapp.presentation.ViewHolderBase
import kotlinx.android.synthetic.main.row_lesson_item.view.*

class LessonListViewHolder(view: View) : ViewHolderBase<Lesson>(view) {

    companion object {
        fun create(parent: ViewGroup): LessonListViewHolder {
            val inflatedView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_lesson_item, parent, false)
            return LessonListViewHolder(inflatedView)
        }
    }

    private val title: TextView = view.title

    override fun bind(item: Lesson) {
        title.text = item.title
    }
}