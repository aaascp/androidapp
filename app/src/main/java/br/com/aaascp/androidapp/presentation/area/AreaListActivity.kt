package br.com.aaascp.androidapp.presentation.area

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.aaascp.androidapp.R
import android.arch.lifecycle.ViewModelProviders
import android.view.LayoutInflater
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.infra.repository.Status
import br.com.aaascp.androidapp.presentation.SingleRowStaticViewAdapter
import br.com.aaascp.androidapp.presentation.area.adapter.AreaListAdapter

import kotlinx.android.synthetic.main.activity_area_list.*


class AreaListActivity : AppCompatActivity() {

    private lateinit var model: AreaListViewModel
    private lateinit var listAdapter: AreaListAdapter
    private lateinit var errorAdapter: SingleRowStaticViewAdapter
    private lateinit var loadingAdapter: SingleRowStaticViewAdapter
    private lateinit var emptyAdapter: SingleRowStaticViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_area_list)

        model = getViewModel()
    }

    override fun onStart() {
        super.onStart()

        initSwipeToRefresh()
        initAdapter()
    }

    private fun getViewModel(): AreaListViewModel {
        return ViewModelProviders
                .of(this)
                .get(AreaListViewModel::class.java)
    }

    private fun initSwipeToRefresh() {
        model.refreshState.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it == NetworkState.LOADING
        })

        swipeRefreshLayout.setOnRefreshListener {
            model.refresh()
        }
    }

    private fun initAdapter() {
        listAdapter = AreaListAdapter()

        errorAdapter =
                SingleRowStaticViewAdapter(
                        R.layout.row_items_error,
                        LayoutInflater.from(this))

        loadingAdapter =
                SingleRowStaticViewAdapter(
                        R.layout.row_items_loading,
                        LayoutInflater.from(this))

        emptyAdapter =
                SingleRowStaticViewAdapter(
                        R.layout.row_items_empty,
                        LayoutInflater.from(this))

        model.areas.observe(
                this,
                Observer {
                    it?.let {
                        listAdapter.submitList(it)
                    }
                })

        model.networkState.observe(this, Observer {
            showNetworkState(it)
        })
    }

    private fun showNetworkState(networkState: NetworkState?) {
        when (networkState?.status) {
            Status.FAILED -> if (listAdapter.itemCount == 0) list.adapter = errorAdapter
            Status.RUNNING -> if (listAdapter.itemCount == 0) {
                list.adapter = loadingAdapter
            }
            Status.SUCCESS -> {
                if (listAdapter.itemCount == 0) {
                    list.adapter = emptyAdapter
                } else {
                    list.adapter = listAdapter
                }
            }
        }
    }
}