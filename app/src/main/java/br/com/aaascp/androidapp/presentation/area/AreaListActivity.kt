package br.com.aaascp.androidapp.presentation.area

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import br.com.aaascp.androidapp.R
import kotlinx.android.synthetic.main.activity_area_list.*
import android.arch.lifecycle.ViewModelProviders
import br.com.aaascp.androidapp.presentation.util.ObserverUtil

class AreaListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_area_list)

        setViewModel()
    }

    private fun setViewModel() {
        val viewModel =
                ViewModelProviders
                        .of(this)
                        .get(AreaListViewModel::class.java)

        viewModel.areas?.observe(
                this,
                ObserverUtil.Companion.OnChanged({
                    area_recycler_view.adapter =
                                    AreaListAdapter(
                                            this,
                                            it)
                })
        )
    }
}