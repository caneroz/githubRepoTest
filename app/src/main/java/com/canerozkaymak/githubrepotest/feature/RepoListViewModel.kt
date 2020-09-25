package com.canerozkaymak.githubrepotest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.canerozkaymak.githubrepotest.base.BaseViewModel
import com.canerozkaymak.githubrepotest.model.Repo
import com.canerozkaymak.githubrepotest.retrofit.GitHubService
import com.canerozkaymak.githubrepotest.service.NetworkFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoListViewModel : BaseViewModel() {
    var gitHubService: GitHubService = NetworkFactory.provideGithubService()

    private val repos: MutableLiveData<List<Repo>> = MutableLiveData()


    var userName: String
        get() = this.toString()
        set(value) {
            loadRepos(value)
        }


    fun getRepos(): LiveData<List<Repo>> {
        return repos
    }

    private fun loadRepos(user: String) {
        mCompositeDisposable.add(
            gitHubService.listRepos(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        )
    }

    private fun handleResponse(repoList: List<Repo>) {
        repos.postValue(repoList)
    }

}