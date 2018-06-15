package kr.rinc.androidexamples.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_kakao_ready.*
import kr.rinc.androidexamples.R
import kr.rinc.androidexamples.model.KakaoReady
import kr.rinc.androidexamples.network.KakaoInit
import kr.rinc.androidexamples.utils.ToastUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.webkit.WebViewClient
import android.content.Intent
import android.content.ActivityNotFoundException
import android.content.Context
import android.net.Uri
import android.webkit.WebChromeClient
import android.webkit.WebView


class KakaoPayReadyActivity : BaseActivity() {
  companion object {
    val INTENT_PROTOCOL_START = "intent:"
    val INTENT_PROTOCOL_INTENT = "#Intent;"
    val INTENT_PROTOCOL_END = ";end;"
    val GOOGLE_PLAY_STORE_PREFIX = "market://details?id="
  }
  lateinit var context: Context

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_kakao_ready)
    context = this@KakaoPayReadyActivity
    KakaoInit.networkList.ready(
        "TC0ONETIME",
        "partner_order_id",
        "partner_user_id",
        "item_name",
        1,
        2200,
        200,
        0,
        "http://localhost/success",
        "https://localhost/fail",
        "https://localhost/cancel")
        .enqueue(object : Callback<KakaoReady> {
          @SuppressLint("SetJavaScriptEnabled")
          override fun onResponse(call: Call<KakaoReady>?, response: Response<KakaoReady>?) {
            if (response!!.code() == 200) {
              response.body()!!.run {
                ToastUtil.showShort(this@KakaoPayReadyActivity, "회원가입에 성공하셨습니다!")
                Log.d("kakao", response.message())
                webview.webChromeClient = WebChromeClient()
                val webSettings = webview.settings
                webSettings.javaScriptEnabled = true
                webSettings.allowContentAccess = true
                webSettings.allowFileAccess = true
                webSettings.databaseEnabled = true
                webSettings.domStorageEnabled = true
                webSettings.builtInZoomControls = true
                webview.loadUrl(this.next_redirect_app_url)
              }
            } else {
              ToastUtil.showShort(this@KakaoPayReadyActivity, "입력 값을 확인해주세요")
            }
          }

          override fun onFailure(call: Call<KakaoReady>?, t: Throwable?) {
            ToastUtil.showShort(this@KakaoPayReadyActivity, "서버 오류!")
            t!!.printStackTrace()
          }
        })
  }


//  inner class MyWebViewClient : WebViewClient() {
//
//    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//      if (url.startsWith(INTENT_PROTOCOL_START)) {
//        val customUrlStartIndex = INTENT_PROTOCOL_START.length
//        val customUrlEndIndex = url.indexOf(INTENT_PROTOCOL_INTENT)
//        if (customUrlEndIndex < 0) {
//          return false
//        } else {
//          val customUrl = url.substring(customUrlStartIndex, customUrlEndIndex)
//          try {
//            Log.d("kakao", customUrl)
//            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(customUrl)))
//          } catch (e: ActivityNotFoundException) {
//            Log.d("kakao", e.message)
//            val packageStartIndex = customUrlEndIndex + INTENT_PROTOCOL_INTENT.length
//            val packageEndIndex = url.indexOf(INTENT_PROTOCOL_END)
//
//            val packageName = url.substring(packageStartIndex, if (packageEndIndex < 0) url.length else packageEndIndex)
//            Log.d("kakao", packageName)
//            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_STORE_PREFIX + packageName)))
//          }
//
//          return true
//        }
//      } else {
//        return false
//      }
//    }
//  }
}