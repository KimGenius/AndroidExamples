package kr.rinc.androidexamples.network

import android.annotation.SuppressLint
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


@SuppressLint("StaticFieldLeak")
object KakaoInit {
    val networkList: KakaoList
    val SERVER_URL: String = "https://kapi.kakao.com"

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addNetworkInterceptor(interceptor).build()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        networkList = retrofit.create(KakaoList::class.java)
    }
}