package com.animesh.roy.mvidemo1.repository

import androidx.lifecycle.LiveData
import com.animesh.roy.mvidemo1.api.MyRetrofitBuilder
import com.animesh.roy.mvidemo1.model.BlogPost
import com.animesh.roy.mvidemo1.model.User
import com.animesh.roy.mvidemo1.ui.main.state.MainViewState
import com.animesh.roy.mvidemo1.util.ApiSuccessResponse
import com.animesh.roy.mvidemo1.util.DataState
import com.animesh.roy.mvidemo1.util.GenericApiResponse

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {

        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    null,
                    data = MainViewState(
                        blogPost = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return MyRetrofitBuilder.apiService.getBlogPosts()
            }

        }.asLiveData()
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    null,
                    data = MainViewState(
                        user = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return MyRetrofitBuilder.apiService.getUser(userId)
            }

        }.asLiveData()
    }
}