package br.com.aaascp.androidapp.presentation.lesson

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.domain.entity.Lesson
import kotlinx.android.synthetic.main.activity_lesson_list.*

class LessonListActivity : AppCompatActivity() {

    companion object {
        private const val AREA_ID_EXTRA = "AREA_ID_EXTRA"

        fun startForArea(
                context: Context,
                areaId: String) {

            val intent = Intent(context, LessonListActivity::class.java)
            intent.putExtra(AREA_ID_EXTRA, areaId)

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lesson_list)

        val viewModel = ViewModelProviders.of(this).get(LessonListViewModel::class.java)
        viewModel.getLessonsForArea(intent.extras.getString(AREA_ID_EXTRA))
        viewModel.lessons?.observe(
                this,
                MyObserver(
                        this,
                        lesson_recycler_view))
    }

    class MyObserver(
            private val context: Context,
            private val recyclerView: RecyclerView
    ) : Observer<List<Lesson>> {

        override fun onChanged(lessons: List<Lesson>?) {
            lessons?.let {
                recyclerView.adapter = LessonListAdapter(context, it)
            }
        }
    }
}