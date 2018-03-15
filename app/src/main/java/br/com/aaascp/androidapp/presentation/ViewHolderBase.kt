package br.com.aaascp.androidapp.presentation

import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson


abstract class ViewHolderBase<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T)
}
