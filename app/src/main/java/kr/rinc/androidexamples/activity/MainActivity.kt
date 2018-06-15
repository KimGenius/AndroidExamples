package kr.rinc.androidexamples.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kr.rinc.androidexamples.R
import kr.rinc.androidexamples.utils.IntentUtil

class MainActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    naverlogin.setOnClickListener {
      IntentUtil.moveActivity(this@MainActivity, NaverLoginActivity::class.java)
    }
    kakaopay.setOnClickListener {
      IntentUtil.moveActivity(this@MainActivity, KakaoPayReadyActivity::class.java)
    }
  }
}
