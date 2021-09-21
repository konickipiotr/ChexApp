package com.chex.module.posts.model;

import java.util.List;


public class PostsResponse {
    List<PostView> posts;

    public PostsResponse() {
    }

    public PostsResponse(List<PostView> posts) {
        this.posts = posts;
    }

    public List<PostView> getPosts() {
        return posts;
    }

    public void setPosts(List<PostView> posts) {
        this.posts = posts;
    }
}
