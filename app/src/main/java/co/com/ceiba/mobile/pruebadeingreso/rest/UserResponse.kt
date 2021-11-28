package co.com.ceiba.mobile.pruebadeingreso.rest

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("") var users: List<Users>
)
