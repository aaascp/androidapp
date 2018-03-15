package br.com.aaascp.androidapp.presentation.lesson.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson
import br.com.aaascp.androidapp.presentation.ViewHolderBase

class LessonListAdapter : PagedListAdapter<Lesson, ViewHolderBase<Lesson>>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
    ): ViewHolderBase<Lesson> {
        return LessonListViewHolder.create(parent)
    }

    override fun onBindViewHolder(
            holder: ViewHolderBase<Lesson>,
            position: Int) {

        val lesson = getItem(position)
        lesson?.let { holder.bind(lesson) }

    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Lesson>() {
            override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean =
                    oldItem.id == newItem.id
        }
    }
}