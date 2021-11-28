package co.com.ceiba.mobile.pruebadeingreso.rest

import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints.GET_POST_USER
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints.GET_USERS
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET(GET_USERS)
    fun getUsers(): Call<List<Users>>

    @GET
    fun getPostsById(@Url url:String): Call<List<Posts>>

}