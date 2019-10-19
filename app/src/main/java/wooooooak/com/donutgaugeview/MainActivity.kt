package wooooooak.com.donutgaugeview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import wooooooak.com.library.DonutGaugeView

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private var lazyValue = 50f

    private lateinit var lazyDonutGaugeView: DonutGaugeView
    private lateinit var donutGaugeViewWitSeekBar: DonutGaugeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        lazyDonutGaugeView = lazy_donut_gauge_view
        lazy_button.setOnClickListener {
            lazyDonutGaugeView.updateValue(lazyValue)
            lazyValue += 200
        }

        donutGaugeViewWitSeekBar = donutGaugeView5
        seekBar.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        donutGaugeViewWitSeekBar.updateValue(progress.toFloat())
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

}
