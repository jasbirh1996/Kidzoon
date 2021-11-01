package com.htf.kidzoon.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class City:Serializable {


    @SerializedName("id")
    @Expose
     var id: String? = null
    @SerializedName("City")
    @Expose
     var city: String? = null


}