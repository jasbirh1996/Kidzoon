package com.htf.kidzoon.netUtils

import com.htf.kidzoon.models.*
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @FormUrlEncoded
    @POST("country")
    fun getCountriesList(
        @Field("device_id") device_id:String,
        @Field("device_type") device_type:String,
        @Field("locale") locale:String
    ):Single<BaseResponse<List<Country>>>

    @FormUrlEncoded
    @POST("login")
    fun userLoginWithOtp(
        @Field("device_id") device_id:String,
        @Field("device_type") device_type:String,
        @Field("locale") locale:String,
        @Field("is_otp_login")is_otp_login:String,
        @Field("is_mobile")is_mobile:String,
        @Field("dial_code")dial_code:String,
        @Field("username")username:String,
        @Field("fcm_id")fcm_id:String
    ):Single<BaseResponse<LoginWithOtp>>

    @FormUrlEncoded
    @POST("login")
    fun userLoginWithPassword(
        @Field("device_id") device_id:String,
        @Field("device_type") device_type:String,
        @Field("locale") locale:String,
        @Field("is_otp_login")is_otp_login:String,
        @Field("is_mobile")is_mobile:String,
        @Field("dial_code")dial_code:String,
        @Field("username")username:String,
        @Field("password")password:String,
        @Field("fcm_id")fcm_id:String
    ):Single<BaseResponse<User>>

    @FormUrlEncoded
    @POST("resend/otp")
    fun resendOtp(
        @Field("device_id") device_id:String,
        @Field("device_type") device_type:String,
        @Field("locale") locale:String,
        @Field("id") id:Int,
        @Field("is_mobile")is_mobile:String,
        @Field("hash_token")hash_token:String
        ):Single<BaseResponse<LoginWithOtp>>

    @FormUrlEncoded
    @POST("verify/otp")
    fun verifyOtp(
        @Field("device_id") device_id:String,
        @Field("device_type") device_type:String,
        @Field("locale") locale:String,
        @Field("id") id:Int,
        @Field("otp")otp:String,
        @Field("hash_token")hash_token:String,
        @Field("fcm_id")fcm_id:String
    ):Single<BaseResponse<User>>

    @FormUrlEncoded
    @POST("complete/profile")
    fun completeProfile(
        @Field("device_id") device_id:String,
        @Field("device_type") device_type:String,
        @Field("locale") locale:String,
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("dial_code") dial_code:String,
        @Field("mobile") mobile:String,
        @Field("password") password:String,
        @Field("dob") dob:String,
        @Field("gender") gender:String,
        @Field("image") image:String,
        @Field("image_directory") image_directory:String,
        @Field("image_extension") image_extension:String,
        @Field("image_mime_type") image_mime_type:String,
        @Field("image_size") image_size:Int
        ):Single<BaseResponse<Data>>

    @FormUrlEncoded
    @POST("change/notification/preference")
    fun changeNotificationSettings(
        @Field("device_id") device_id:String,
        @Field("device_type") device_type:String,
        @Field("locale") locale:String,
        @Field("post_notify")post_notify:String,
        @Field("like_notify")like_notify:String,
        @Field("comment_notify")comment_notify:String,
        @Field("follow_notify")follow_notify:String,
        @Field("friend_request_notify")friend_request_notify:String,
        @Field("chat_notify")chat_notify:String,
        @Field("share_notify")share_notify:String
    ):Single<BaseResponse<Blank>>

    @FormUrlEncoded
    @POST("logout")
    fun logout(
        @Field("device_id") device_id:String,
        @Field("device_type") device_type:String,
        @Field("locale") locale:String
    ):Single<BaseResponse<Blank>>

    @FormUrlEncoded
    @POST("change/privacy")
    fun changePrivacy(
        @Field("device_id") device_id:String,
        @Field("device_type") device_type:String,
        @Field("locale") locale:String,
        @Field("privacy") privacy:String
    ):Single<BaseResponse<Blank>>

    @FormUrlEncoded
    @POST("change/password")
    fun changePassword(
        @Field("device_id") device_id:String,
        @Field("device_type") device_type:String,
        @Field("locale") locale:String,
        @Field("password") password:String,
        @Field("confirm_password") confirm_password:String
    ):Single<BaseResponse<Blank>>

}