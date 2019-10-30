package wooooooak.com.donutgaugeview

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_main.*
import wooooooak.com.donutgaugeview.databinding.ActivityMainBinding
import wooooooak.com.library.DonutGaugeView

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private var lazyValue = 50f

    lateinit var binding: ActivityMainBinding
    private lateinit var lazyDonutGaugeView: DonutGaugeView
    private lateinit var donutGaugeViewWitSeekBar: DonutGaugeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // databinding exmaple
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.run {
            testValue = "test Value"
            startValue = 60f
            endValue = 100f
            lifecycleOwner = this@MainActivity
            executePendingBindings()
        }

        // not databinding exmaple
        lazyDonutGaugeView = lazy_donut_gauge_view
        lazy_button.setOnClickListener {
            lazyDonutGaugeView.updateValue(lazyValue)
            lazyValue += 200
        }

        donutGaugeViewWitSeekBar = donutGaugeView5
        seekBar.setOnSeekBarChangeListener(this)

        //         Reset some data and Redraw
//        lazyDonutGaugeView.run {
//            topText = "new top"
//            bottomText = "200"
//            initValue(20f, 50f)
//        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        donutGaugeViewWitSeekBar.updateValue(progress.toFloat())
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

}
