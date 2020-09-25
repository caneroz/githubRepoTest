package com.canerozkaymak.githubrepotest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.canerozkaymak.githubrepotest.viewmodel.RepoListViewModel
import org.junit.Assert
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RepoListViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Test
    fun setUserName_repos_notEmpty() {
        val model = RepoListViewModel()

        model.userName = "caneroz"
        Assert.assertTrue(model.getRepos().value?.size == 1)

    }

    @Test
    fun setUserName_repos_empty() {
        val model = RepoListViewModel()

        model.userName = "canerozabcdef"
        Assert.assertFalse(model.getRepos().value?.size == 1)
    }
}