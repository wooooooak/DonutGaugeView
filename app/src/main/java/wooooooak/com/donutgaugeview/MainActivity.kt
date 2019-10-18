package wooooooak.com.donutgaugeview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import wooooooak.com.library.DonutGaugeView

class MainActivity : AppCompatActivity() {

    private var lazyValue = 50f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val lazyDonutGaugeView = lazy_donut_gauge_view
        lazy_button.setOnClickListener {
            lazyDonutGaugeView.updateValue(lazyValue)
            lazyValue += 200
        }
    }
}
