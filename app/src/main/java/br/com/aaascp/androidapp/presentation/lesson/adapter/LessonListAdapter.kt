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
    ) = when (viewType) {
        HEADER_TYPE -> LessonHeaderViewHolder.create(parent)
        LIST_TYPE -> LessonListViewHolder.create(parent)
        else -> throw IllegalArgumentException("unknown viewType: $viewType")
    }

    override fun onBindViewHolder(
            holder: ViewHolderBase<Lesson>,
            position: Int) {

        val index = if (position == 0) 0 else position - 1
        val lesson = currentList?.get(index)
        lesson?.let { holder.bind(lesson) }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> HEADER_TYPE
        else -> LIST_TYPE
    }

    companion object {
        private const val HEADER_TYPE = R.layout.row_last_update
        private const val LIST_TYPE = R.layout.row_lesson_item

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Lesson>() {
            override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean =
                    oldItem.id == newItem.id
        }
    }
}