package com.canerozkaymak.githubrepotest.feature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.canerozkaymak.githubrepotest.model.Repo
import com.canerozkaymak.githubrepotest.R
import com.canerozkaymak.githubrepotest.viewmodel.RepoListViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_repo_list.*
import kotlinx.android.synthetic.main.repo_list_content.view.*
import kotlinx.android.synthetic.main.repo_list.*


class RepoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        val repoListViewModel = ViewModelProviders.of(this)[RepoListViewModel::class.java]

        repoListViewModel.getRepos().observe(this, Observer<List<Repo>> { repos ->
            if (repos.size == 0) {
                Snackbar.make(
                    parentLayout, getString(R.string.empty_user_repo_error),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            setupRecyclerView(item_list, repos)
            progLayout.stopLoading()
        })

        repoListViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) Snackbar.make(
                parentLayout, errorMessage,
                Snackbar.LENGTH_SHORT
            ).show()
            progLayout.stopLoading()
        })

        submitButton.setOnClickListener {
            if ("".equals(userEditText.text.toString())) {
                Snackbar.make(
                    parentLayout, getString(R.string.empty_username_error),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                progLayout.startLoading()
                repoListViewModel.userName = userEditText.text.toString()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        item_list.adapter?.notifyDataSetChanged()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, repos: List<Repo>) {
        recyclerView.adapter = RepoItemRecyclerViewAdapter(this, repos)
    }

    class RepoItemRecyclerViewAdapter(
        private val parentActivity: RepoListActivity,
        private val values: List<Repo>
    ) :
        RecyclerView.Adapter<RepoItemRecyclerViewAdapter.ViewHolder>() {
        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag

                    val intent = Intent(v.context, RepoDetailActivity::class.java)
                        .apply {
                            putExtra(RepoDetailActivity.ARG_REPO, item as Repo)
                        }
                    v.context.startActivity(intent)

            }
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_list_content, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.name

            val sharedPref = parentActivity.getSharedPreferences(
                parentActivity.getString(R.string.preference_key),
                Context.MODE_PRIVATE
            )
            if (sharedPref.getBoolean(item.id.toString(), false)) {
                holder.favImageView.visibility = View.VISIBLE
            } else {
                holder.favImageView.visibility = View.GONE
            }

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
            val favImageView: ImageView = view.fav_image_view
        }
    }
}
