package br.com.aaascp.androidapp.presentation.lesson.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson
import br.com.aaascp.androidapp.presentation.ViewHolderBase
import br.com.aaascp.androidapp.util.TableUtils
import kotlinx.android.synthetic.main.row_last_update.view.*

class LessonHeaderViewHolder private constructor(view: View) : ViewHolderBase<Lesson>(view) {

    companion object {
        fun create(parent: ViewGroup): LessonHeaderViewHolder {
            val inflatedView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_last_update, parent, false)
            return LessonHeaderViewHolder(inflatedView)
        }
    }

    private val lastUpdate: TextView = view.lastUpdate

    override fun bind(item: Lesson) {
        lastUpdate.text = String.format(
                itemView.context.getString(R.string.last_update),
                TableUtils().getAreaTableLastUpdate()
        )
    }
}

