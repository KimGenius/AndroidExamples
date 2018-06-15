package kr.rinc.androidexamples.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_kakao_ready.*
import kr.rinc.androidexamples.R

class KakaoPaymentActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_kakao_payment)
  }
}