package br.com.aaascp.androidapp.presentation.area

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import br.com.aaascp.androidapp.R
import kotlinx.android.synthetic.main.activity_area_list.*
import android.arch.lifecycle.ViewModelProviders
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.widget.Toast.LENGTH_LONG
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.infra.repository.Status
import br.com.aaascp.androidapp.presentation.SingleRowStaticViewAdapter

class AreaListActivity : AppCompatActivity() {

    private lateinit var model: AreaListViewModel
    private val adapter = AreaListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_area_list)

        model = getViewModel()
    }

    override fun onStart() {
        super.onStart()

        initAdapter()
        initSwipeToRefresh()
    }

    private fun getViewModel(): AreaListViewModel {
        return ViewModelProviders
                .of(this)
                .get(AreaListViewModel::class.java)
    }

    private fun initAdapter() {
        list.adapter = adapter

        model.areas.observe(
                this,
                Observer {
                    it?.let {
                        if (it.isEmpty()) {
                            showEmptyState()
                        } else {
                            adapter.submitList(it)
                        }
                    }
                })

        model.networkState.observe(this, Observer {
            showNetworkState(it)
        })
    }

    private fun showEmptyState() {
        list.adapter =
                SingleRowStaticViewAdapter(
                        R.layout.row_items_empty,
                        LayoutInflater.from(this))
    }

    private fun initSwipeToRefresh() {
        model.refreshState.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it == NetworkState.LOADING
        })

        swipeRefreshLayout.setOnRefreshListener {
            model.refresh()
        }
    }

    private fun showNetworkState(networkState: NetworkState?) {
        when (networkState?.status) {
            Status.RUNNING -> { }
            Status.SUCCESS -> showSuccessNetwork()
            Status.FAILED -> showFailedNetwork()
        }
    }

    private fun showSuccessNetwork() {
        Snackbar.make(
                root,
                getString(R.string.list_success),
                LENGTH_LONG)
                .setAction(getString(R.string.close), {})
                .show()
    }

    private fun showFailedNetwork() {
        Snackbar.make(
                root,
                getString(R.string.list_error),
                LENGTH_LONG)
                .setAction(getString(R.string.retry), {
                    model.retry()
                })
                .show()
    }
}