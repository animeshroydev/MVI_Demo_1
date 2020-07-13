package com.animesh.roy.mvidemo1.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.animesh.roy.mvidemo1.model.BlogPost
import com.animesh.roy.mvidemo1.model.User
import com.animesh.roy.mvidemo1.repository.Repository
import com.animesh.roy.mvidemo1.ui.main.state.MainStateEvent
import com.animesh.roy.mvidemo1.ui.main.state.MainStateEvent.*
import com.animesh.roy.mvidemo1.ui.main.state.MainViewState
import com.animesh.roy.mvidemo1.util.AbsentLiveData
import com.animesh.roy.mvidemo1.util.DataState

class MainViewModel: ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) {
            _stateEvent->
        handleStateEvent(_stateEvent)
        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        return when(stateEvent) {
            is GetBlogPostsEvent -> {
                return Repository.getBlogPosts()
            }

            is GetUserEvent -> {
                return Repository.getUser(stateEvent.userId)
            }

            is None -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setBlogListData(blogPosts: List<BlogPost>) {
        val update = getCurrentViewStateOrNew()
        update.blogPost = blogPosts
        _viewState.value = update
    }

    fun setUser(user: User) {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    private fun getCurrentViewStateOrNew(): MainViewState {
        val value = viewState.value?.let {
            it
        }?: MainViewState()
        return value
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }

}