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
    ): ViewHolderBase<Lesson> = when (viewType) {
        HEADER_ITEM -> {
            LessonHeaderViewHolder.create(parent)
        }
        LIST_ITEM -> {
            LessonListViewHolder.create(parent)
        }
        else -> throw IllegalArgumentException("unknown view type $viewType")
    }

    override fun onBindViewHolder(
            holder: ViewHolderBase<Lesson>,
            position: Int) {

        val lesson = getItem(position)
        lesson?.let { holder.bind(lesson) }
    }

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> {
            LessonListAdapter.HEADER_ITEM
        }
        else -> {
            LessonListAdapter.LIST_ITEM
        }
    }

    companion object {
        private const val HEADER_ITEM = R.layout.row_last_update
        private const val LIST_ITEM = R.layout.row_lesson_item

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Lesson>() {
            override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean =
                    oldItem.id == newItem.id
        }
    }
}