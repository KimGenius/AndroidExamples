package kr.rinc.androidexamples.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.nhn.android.naverlogin.OAuthLogin
import kotlinx.android.synthetic.main.activity_naver_login.*
import kr.rinc.androidexamples.R
import android.widget.Toast
import com.nhn.android.naverlogin.OAuthLoginHandler



class NaverLoginActivity : BaseActivity() {
  private val mContext = this@NaverLoginActivity
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    naverLoginInit()
  }

  fun naverLoginInit() {
    var mOAuthLoginModule = OAuthLogin.getInstance()
    mOAuthLoginModule.init(
        NaverLoginActivity@this
        ,"bG9W9GErIs6pgZBszI_c"
        ,"N1XqmEN45C"
        ,"ApplicationExamples"
        //,OAUTH_CALLBACK_INTENT
        // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
    )

    val mOAuthLoginHandler = @SuppressLint("HandlerLeak")
    object : OAuthLoginHandler() {
      override fun run(success: Boolean) {
        if (success) {
          val accessToken = mOAuthLoginModule.getAccessToken(mContext)
          val refreshToken = mOAuthLoginModule.getRefreshToken(mContext)
          val expiresAt = mOAuthLoginModule.getExpiresAt(mContext)
          val tokenType = mOAuthLoginModule.getTokenType(mContext)
          Log.d("accessToken", accessToken)
          Log.d("refreshToken", refreshToken)
          Log.d("expiresAt", expiresAt.toString())
          Log.d("tokenType", tokenType)
          Log.d("mOAuthState", mOAuthLoginModule.getState(mContext).toString())
//          mOauthAT.setText(accessToken)
//          mOauthRT.setText(refreshToken)
//          mOauthExpires.setText(expiresAt.toString())
//          mOauthTokenType.setText(tokenType)
//          mOAuthState.setText(mOAuthLoginModule.getState(mContext).toString())
        } else {
          val errorCode = mOAuthLoginModule.getLastErrorCode(mContext).code
          val errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext)
          Toast.makeText(mContext, "errorCode:" + errorCode
              + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show()
        }
      }
    }

//    buttonOAuthLoginImg.setOnClickListener {
      mOAuthLoginModule.startOauthLoginActivity(mContext, mOAuthLoginHandler)
//    }
  }
}
