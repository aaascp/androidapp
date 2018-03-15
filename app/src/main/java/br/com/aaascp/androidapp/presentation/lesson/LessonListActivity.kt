package br.com.aaascp.androidapp.presentation.lesson

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.infra.repository.ListState
import br.com.aaascp.androidapp.infra.repository.ListState.*
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.presentation.SingleRowStaticViewAdapter
import br.com.aaascp.androidapp.presentation.lesson.adapter.LessonListAdapter

import kotlinx.android.synthetic.main.activity_lesson_list.*

class LessonListActivity : AppCompatActivity() {

    private lateinit var model: LessonListViewModel
    private lateinit var listAdapter: LessonListAdapter
    private lateinit var errorAdapter: SingleRowStaticViewAdapter
    private lateinit var loadingAdapter: SingleRowStaticViewAdapter
    private lateinit var emptyAdapter: SingleRowStaticViewAdapter


    companion object {
        private const val AREA_ID_EXTRA = "AREA_ID_EXTRA"
        private const val AREA_TITLE_EXTRA = "AREA_TITLE_EXTRA"
        private const val AREA_SUBJECT_EXTRA = "AREA_SUBJECT_EXTRA"

        fun startForArea(
                context: Context,
                id: String,
                title: String,
                subject: String) {

            val intent = Intent(context, LessonListActivity::class.java)
            intent.putExtra(AREA_ID_EXTRA, id)
            intent.putExtra(AREA_TITLE_EXTRA, title)
            intent.putExtra(AREA_SUBJECT_EXTRA, subject)

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lesson_list)

        val id = intent.extras.getString(AREA_ID_EXTRA)
        val title = intent.extras.getString(AREA_TITLE_EXTRA)
        val subject = intent.extras.getString(AREA_SUBJECT_EXTRA)

        model = getViewModel()
        initToolbar(title, subject)

        model.showLessonsForArea(id)
    }

    override fun onStart() {
        super.onStart()

        initSwipeToRefresh()
        initAdapter()
    }

    private fun getViewModel(): LessonListViewModel {
        return ViewModelProviders
                .of(this)
                .get(LessonListViewModel::class.java)
    }

    private fun initToolbar(title: String, subjectValue: String) {
        toolbar.title =
                String.format(
                        title)

        subject.text = subjectValue

        toolbar.setNavigationOnClickListener {
            this.onBackPressed()
        }
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
        listAdapter = LessonListAdapter()

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

        list.adapter = listAdapter

        model.lessons.observe(
                this,
                Observer {
                    listAdapter.submitList(it)
                })

        model.listState.observe(this, Observer {
            showState(it)
        })
    }

    private fun showState(state: ListState?) {
        when (state) {
            LOADING -> list.adapter = loadingAdapter
            EMPTY -> list.adapter = emptyAdapter
            ERROR -> list.adapter = errorAdapter
            FILLED -> list.adapter = listAdapter
        }
    }
}