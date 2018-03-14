package br.com.aaascp.androidapp.presentation.lesson

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.Toast
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.infra.repository.Status
import br.com.aaascp.androidapp.presentation.SingleRowStaticViewAdapter
import kotlinx.android.synthetic.main.activity_lesson_list.*

class LessonListActivity : AppCompatActivity() {

    private lateinit var model: LessonListViewModel
    private val adapter = LessonListAdapter(this)

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

        initAdapter()
        initToolbar(title, subject)

        model.showLessonsForArea(id)
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

    private fun initAdapter() {
        list.adapter = adapter

        model.lessons.observe(
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

    private fun showNetworkState(networkState: NetworkState?) {
        when (networkState?.status) {
            Status.RUNNING -> {
            }
            Status.SUCCESS -> showSuccessNetwork()
            Status.FAILED -> showFailedNetwork()
        }
    }

    private fun showSuccessNetwork() {
        Snackbar.make(
                root,
                getString(R.string.list_success),
                Toast.LENGTH_LONG)
                .setAction(getString(R.string.close), {})
                .show()
    }

    private fun showFailedNetwork() {
        Snackbar.make(
                root,
                getString(R.string.list_error),
                Toast.LENGTH_LONG)
                .setAction(getString(R.string.retry), {
                    model.retry()
                })
                .show()
    }
}