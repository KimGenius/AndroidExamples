package kr.rinc.androidexamples.network

import kr.rinc.androidexamples.model.*
import retrofit2.Call
import retrofit2.http.*

interface KakaoList {
  @Headers("Authorization: KakaoAK 16b681ef46fa26d429f45d3ecf30cc35")
  @POST("/v1/payment/ready")
  @FormUrlEncoded
  fun ready(@Field("cid") cid: String,
            @Field("partner_order_id") partner_order_id: String,
            @Field("partner_user_id") partner_user_id: String,
            @Field("item_name") item_name: String,
            @Field("quantity") quantity: Int,
            @Field("total_amount") total_amount: Int,
            @Field("vat_amount") vat_amount: Int,
            @Field("tax_free_amount") tax_free_amount: Int,
            @Field("approval_url") approval_url: String,
            @Field("fail_url") fail_url: String,
            @Field("cancel_url") cancel_url: String): Call<KakaoReady>
}