package br.com.aaascp.androidapp.presentation.lesson

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.aaascp.androidapp.R
import kotlinx.android.synthetic.main.activity_lesson_list.*

class LessonListActivity : AppCompatActivity() {

    private lateinit var model: LessonListViewModel

    companion object {
        private const val AREA_ID_EXTRA = "AREA_ID_EXTRA"
        private const val AREA_NAME_EXTRA = "AREA_NAME_EXTRA"
        private const val AREA_SUBJECT_EXTRA = "AREA_SUBJECT_EXTRA"

        fun startForArea(
                context: Context,
                id: String,
                title: String,
                subject: String) {

            val intent = Intent(context, LessonListActivity::class.java)
            intent.putExtra(AREA_ID_EXTRA, id)
            intent.putExtra(AREA_NAME_EXTRA, title)
            intent.putExtra(AREA_SUBJECT_EXTRA, subject)

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lesson_list)

        val id = intent.extras.getString(AREA_ID_EXTRA)
        val title = intent.extras.getString(AREA_NAME_EXTRA)
        val subject = intent.extras.getString(AREA_NAME_EXTRA)

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

    private fun initToolbar(title: String, subject: String) {
        toolbar.title =
                String.format(
                        getString(R.string.lesson_title),
                        title)
    }

    private fun initAdapter() {
        val adapter = LessonListAdapter(this)
        list.adapter = adapter

        model.lessons.observe(
                this,
                Observer {
                    adapter.submitList(it)
                })

        model.networkState.observe(this, Observer {
            //            adapter.setNetworkState(it)
        })
    }
}