package br.com.aaascp.androidapp.presentation.area

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import br.com.aaascp.androidapp.R
import kotlinx.android.synthetic.main.activity_area_list.*
import android.arch.lifecycle.ViewModelProviders

class AreaListActivity : AppCompatActivity() {

    private lateinit var model: AreaListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_area_list)

        model = getViewModel()
        initAdapter()
    }

    private fun getViewModel(): AreaListViewModel {
        return ViewModelProviders
                        .of(this)
                        .get(AreaListViewModel::class.java)
    }

    private fun initAdapter() {
        val adapter = AreaListAdapter(this)
        list.adapter = adapter

        model.areas.observe(
                this,
                Observer {
                    it?.let {
                        adapter.submitList(it)
                    }
                })

        model.networkState.observe(this, Observer {
            //            adapter.setNetworkState(it)
        })
    }
}