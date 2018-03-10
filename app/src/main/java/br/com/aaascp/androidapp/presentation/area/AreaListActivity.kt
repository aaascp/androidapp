package br.com.aaascp.androidapp.presentation.area

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import br.com.aaascp.androidapp.R
import kotlinx.android.synthetic.main.activity_area_list.*
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.widget.RecyclerView
import br.com.aaascp.androidapp.domain.entity.Area
import br.com.aaascp.androidapp.infra.repository.AreaRepository


class AreaListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_area_list)
        val viewModel = ViewModelProviders.of(this).get(AreaListViewModel::class.java)
        viewModel.init(AreaRepository(this))
        viewModel.areas?.observe(
                this,
                MyObserver(
                        this,
                        area_recycler_view))
    }

    class MyObserver(
            private val context: Context,
            private val recyclerView: RecyclerView
    ) : Observer<List<Area>> {

        override fun onChanged(areas: List<Area>?) {
            areas?.let {
                recyclerView.adapter = AreaListAdapter(it, context)
            }
        }

    }
}