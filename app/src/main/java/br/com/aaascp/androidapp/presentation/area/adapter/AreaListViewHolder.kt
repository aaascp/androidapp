package br.com.aaascp.androidapp.presentation.area.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.infra.source.local.entity.Area
import br.com.aaascp.androidapp.presentation.ViewHolderBase
import br.com.aaascp.androidapp.presentation.lesson.LessonListActivity
import kotlinx.android.synthetic.main.row_area_item.view.*

class AreaListViewHolder private constructor(view: View) : ViewHolderBase<Area>(view) {

    companion object {
        fun create(parent: ViewGroup): AreaListViewHolder {
            val inflatedView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_area_item, parent, false)
            return AreaListViewHolder(inflatedView)
        }
    }

    private val root: View = view.root
    private val subject: TextView = view.subject
    private val title: TextView = view.title

    override fun bind(item: Area) {
        subject.text = item.subject
        title.text = item.title
        root.setOnClickListener {
            LessonListActivity.startForArea(
                    itemView.context,
                    item.id,
                    item.title,
                    item.subject)
        }
    }
}