package com.htf.kidzoon.models

data class User(
    var access_token: String,
    var `data`: Data,
    var expires_in: Int,
    var expires_unit: String,
    var token_type: String
)
data class Data(
    var chat_notify: String,
    var comment_notify: String,
    var cover_image: String,
    var created_at: String,
    var dial_code: String,
    var dob: String?,
    var email: String,
    var follow_notify: String,
    var followers: Int,
    var following: Int,
    var friend_request_notify: String,
    var friends: Int,
    var id: Int,
    var gender: String?,
    var image: String?,
    var is_returner: Int,
    var last_connected_at: Any,
    var last_status: String,
    var level: Int,
    var like_notify: String,
    var mobile: String,
    var name: String?,
    var post_notify: String,
    var privacy: String,
    var share_notify: String,
    var username: String?
)