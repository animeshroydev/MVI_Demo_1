package com.animesh.roy.mvidemo1.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.animesh.roy.mvidemo1.R
import com.animesh.roy.mvidemo1.model.User
import com.animesh.roy.mvidemo1.ui.DataStateListener
import com.animesh.roy.mvidemo1.ui.main.state.MainStateEvent
import com.animesh.roy.mvidemo1.util.TopSpacingItemDecoration
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_blog_list_item.*
import java.lang.Exception

class MainFragment : Fragment(){

    lateinit var viewModel: MainViewModel

    lateinit var dataStateHandler: DataStateListener

    lateinit var mainRecyclerAdapter: MainRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?: throw Exception("Invalid Activity")

        initRecyclerView()
        subscribeObservers()

    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainFragment.context)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            mainRecyclerAdapter = MainRecyclerAdapter()
            adapter = mainRecyclerAdapter
        }
    }

    fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            println("DEBUG: DataState: $dataState")

            // handle loading and message
            dataStateHandler.onDataStateChange(dataState)

            // Handle the Data<T>
            dataState.data?.let {mainViewState ->

                mainViewState.getContentIfNotHandled()?.let {
                    it.blogPost?.let { blogPost ->
                        // set blogPost data
                        viewModel.setBlogListData(blogPost)
                    }

                    it.user?.let {user ->
                        // set User data
                        viewModel.setUser(user)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewState ->

            viewState.blogPost?.let {
               println("DEBUG: Setting blog post to RecyclerView: $it")

                mainRecyclerAdapter.submitList(it)
           }

            viewState.user?.let {
                println("DEBUG: Setting user data: $it")
                setUserProperties(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.action_get_user -> triggerGetUserEvent()

            R.id.action_get_blog -> triggerGetBlogsEvent()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setUserProperties(user: User) {
        email.text = user.email
        username.text = user.username

        view?.let {
            Glide.with(it.context)
                .load(user.image)
                .into(image)
        }
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(MainStateEvent.GetUserEvent("1"))
    }

    private fun triggerGetBlogsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetBlogPostsEvent())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            dataStateHandler = context as DataStateListener
        }catch (e: ClassCastException) {
            println("DEBUG $context must implement DataStateListener")
        }
    }
}