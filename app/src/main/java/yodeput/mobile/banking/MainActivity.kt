package yodeput.mobile.banking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import yodeput.mobile.banking.feature.utils.Notifier
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Notifier.init(this)
    }
}