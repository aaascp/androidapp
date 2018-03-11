package br.com.aaascp.androidapp.presentation.area

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import br.com.aaascp.androidapp.R
import kotlinx.android.synthetic.main.activity_area_list.*
import android.arch.lifecycle.ViewModelProviders

class AreaListActivity : AppCompatActivity() {

    private lateinit var adapter: AreaListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_area_list)

        adapter = AreaListAdapter(this)
        area_recycler_view.adapter = adapter
        setViewModel()
    }

    private fun setViewModel() {
        val viewModel =
                ViewModelProviders
                        .of(this)
                        .get(AreaListViewModel::class.java)

        viewModel.areas.observe(
                this,
                Observer {
                    it?.let {
                        adapter.setAreaList(it)
                    }
                })
    }
}