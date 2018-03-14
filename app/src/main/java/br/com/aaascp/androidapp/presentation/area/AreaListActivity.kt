package br.com.aaascp.androidapp.presentation.area

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import br.com.aaascp.androidapp.R
import kotlinx.android.synthetic.main.activity_area_list.*
import android.arch.lifecycle.ViewModelProviders
import android.support.design.widget.Snackbar
import android.widget.Toast.LENGTH_LONG
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.infra.repository.Status
import br.com.aaascp.androidapp.util.TableUtils

class AreaListActivity : AppCompatActivity() {

    private lateinit var model: AreaListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_area_list)

        model = getViewModel()
        initView()
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
        val adapter = AreaListAdapter(this)
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
            setNetworkState(it)
        })
    }

    private fun showEmptyState() {

    }

    private fun initSwipeToRefresh() {
        model.refreshState.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it == NetworkState.LOADING
        })

        swipeRefreshLayout.setOnRefreshListener {
            model.refresh()
        }
    }

    private fun initView() {
        val lastUpdatedAt = TableUtils().getAreaTableLastUpdate()
        lastUpdatedAt?.let {
            lastUpdate.text =
                    String.format(
                            getString(R.string.last_update),
                            it)
        }
    }

    private fun setNetworkState(networkState: NetworkState?) {
        when (networkState?.status) {
            Status.RUNNING -> {
            }
            Status.SUCCESS -> {
                Snackbar.make(
                        root,
                        getString(R.string.area_list_success),
                        LENGTH_LONG)
                        .setAction(getString(R.string.close), {})
                        .show()
            }
            Status.FAILED -> {
                Snackbar.make(
                        root,
                        getString(R.string.area_list_error),
                        LENGTH_LONG)
                        .setAction(getString(R.string.retry), {
                            model.retry()
                        })
                        .show()
            }
        }
    }
}