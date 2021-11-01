package com.htf.kidzoon.utils

object Constants {


    private val BASE_URL = "https://development.htf.sa/kidzoon"
    val API_URL = "$BASE_URL/api/v1/"
    val COUNTRY_IMAGE_URL = "$BASE_URL/uploads/country_flags/"
    val USER_IMAGE_URL="$BASE_URL/uploads/users/"
    val MEDIA_VIDEO_URL="$BASE_URL/uploads/media/video"
    val MEDIA_IMAGE_URL="$BASE_URL/uploads/media/image"

    val REPONSE_CODE_TOKEN_EXPIRED=401
    val REPONSE_CODE_UNPROCESSABLE_ENTITY=422

    val DB_NAME="KidzoonDB"

    val KEY_TOKEN = "token"
    val KEY_USER_JSON_DETAILS = "jsonUserDetails"
    val KEY_PREF_USER_LANGUAGE="user_lang"

    val KEY_DATA = "data"
    val KEY_FCM_TOKEN = "fcm_token"
    val KEY_CITY_DETAILS = "jsonCity"
    val KEY_FILTER_DETAILS = "filter"

    //Friends,Public,OnlyMe,FriendsExpect,SpecificFriends
    val PRIVACY_PUBLIC="Public"
    val PRIVACY_ONLY_ME="OnlyMe"
    val PRIVACY_FRIENDS="Friends"
    val PRIACY_FIRENDS_EXPECT="FriendsExpect"
    val PRIVACY_SPECIFIC_FRIENDS="SpecificFriends"
}