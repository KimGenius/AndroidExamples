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
import android.webkit.WebView
import java.net.URISyntaxException


class KakaoPayReadyActivity : BaseActivity() {
  companion object {
    val INTENT_URI_START = "intent:"
    val INTENT_FALLBACK_URL = "browser_fallback_url"
    val URI_SCHEME_MARKET = "market://details?id="
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
        "http://52.79.60.204/success",
        "https://localhost/fail",
        "https://localhost/cancel")
        .enqueue(object : Callback<KakaoReady> {
          @SuppressLint("SetJavaScriptEnabled")
          override fun onResponse(call: Call<KakaoReady>?, response: Response<KakaoReady>?) {
            if (response!!.code() == 200) {
              response.body()!!.run {
                ToastUtil.showShort(this@KakaoPayReadyActivity, "회원가입에 성공하셨습니다!")
                Log.d("kakao", response.message())
                webview.webViewClient = MyWebViewClient()
                val webSettings = webview.settings
                webSettings.javaScriptEnabled = true
                webSettings.allowContentAccess = true
                webSettings.allowFileAccess = true
                webSettings.databaseEnabled = true
                webSettings.domStorageEnabled = true
                webSettings.builtInZoomControls = true
                Log.d("tid", this.next_redirect_app_url+"?tid="+this.tid)
                webview.loadUrl(this.next_redirect_app_url+"?tid="+this.tid)
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


  inner class MyWebViewClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, uri: String): Boolean {
      if (uri.toLowerCase().startsWith(INTENT_URI_START)) {
        var parsedIntent: Intent? = null
        try {
          parsedIntent = Intent.parseUri(uri, 0)
          startActivity(parsedIntent)
        } catch (e: ActivityNotFoundException) {
          return doFallback(view, parsedIntent)
        } catch (e: URISyntaxException) {
          return doFallback(view, parsedIntent)
        }

      } else {
        view.loadUrl(uri)
      }
      return true
    }

    fun doFallback(view: WebView, parsedIntent: Intent?): Boolean {
      if (parsedIntent == null) {
        return false
      }

      val fallbackUrl = parsedIntent.getStringExtra(INTENT_FALLBACK_URL)
      if (fallbackUrl != null) {
        view.loadUrl(fallbackUrl)
        return true
      }

      val packageName = parsedIntent.`package`
      if (packageName != null) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(URI_SCHEME_MARKET + packageName)))
        return true
      }
      return false
    }
  }
}