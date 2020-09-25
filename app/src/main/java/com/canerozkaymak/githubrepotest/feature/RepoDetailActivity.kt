package com.canerozkaymak.githubrepotest.feature

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.canerozkaymak.githubrepotest.R
import com.canerozkaymak.githubrepotest.model.Repo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_repo_detail.*

class RepoDetailActivity : AppCompatActivity() {

    private lateinit var repo: Repo
    private lateinit var menu: Menu


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)
        setSupportActionBar(detail_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        repo = intent.getSerializableExtra(ARG_REPO) as Repo
        supportActionBar?.title = repo.name

        repo.let {
            Picasso.get()
                .load(it.owner.avatar_url)
                .into(image_avatar)

            text_view_owner.text = it.owner.login
            stars_count.text = getString(R.string.stars, it.stargazers_count)
            open_issues_count.text = getString(R.string.open_issues, it.open_issues_count)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_details, menu)
        this.menu = menu

        val sharedPref =
            this.getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE)
        if (sharedPref.getBoolean(repo.id.toString(), false)) {
            menu.getItem(0).setIcon(android.R.drawable.star_big_on)
        } else {
            menu.getItem(0).setIcon(android.R.drawable.star_big_off)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_favorite -> {
                changeFavIcon()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    fun changeFavIcon() {
        val sharedPref =
            this.getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            if (sharedPref.getBoolean(repo.id.toString(), false)) {
                this.remove(repo.id.toString())
                apply()
                menu.getItem(0).setIcon(android.R.drawable.star_big_off)
            } else {
                putBoolean(repo.id.toString(), true)
                apply()
                menu.getItem(0).setIcon(android.R.drawable.star_big_on)
            }
        }
    }

    companion object {
        const val ARG_REPO = "arg_repo"
    }
}
