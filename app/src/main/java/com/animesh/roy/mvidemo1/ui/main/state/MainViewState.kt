package com.animesh.roy.mvidemo1.ui.main.state

import com.animesh.roy.mvidemo1.model.BlogPost
import com.animesh.roy.mvidemo1.model.User

data class MainViewState(

    var blogPost: List<BlogPost>? = null,
    var user: User? = null

) {
}