package com.canerozkaymak.githubrepotest.retrofit

import com.canerozkaymak.githubrepotest.model.Repo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface GitHubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Observable<List<Repo>>
}