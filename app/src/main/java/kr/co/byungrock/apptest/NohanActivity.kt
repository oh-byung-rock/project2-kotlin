package kr.co.byungrock.apptest

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NohanActivity : AppCompatActivity() {
    lateinit var  nohan_title: TextView
    lateinit var  nohan_txt1: TextView
    lateinit var  nohan_txt2: TextView
    lateinit var  nohan_txt3: TextView
    lateinit var  nohan_txt4: TextView
    lateinit var  nohan_txt5: TextView
    lateinit var  nohan_txt6: TextView
    lateinit var  nohan_txt7: TextView
    lateinit var  nohan_txt8: TextView
    lateinit var  nohan_txt9: TextView
    lateinit var  nohan_txt10: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nohan)

        nohan_title = findViewById<TextView>(R.id.nohan_statnID)
        nohan_txt1 = findViewById<TextView>(R.id.nohan_congestion1)
        nohan_txt2 = findViewById<TextView>(R.id.nohan_congestion2)
        nohan_txt3 = findViewById<TextView>(R.id.nohan_congestion3)
        nohan_txt4 = findViewById<TextView>(R.id.nohan_congestion4)
        nohan_txt5 = findViewById<TextView>(R.id.nohan_congestion5)
        nohan_txt6 = findViewById<TextView>(R.id.nohan_congestion6)
        nohan_txt7 = findViewById<TextView>(R.id.nohan_congestion7)
        nohan_txt8 = findViewById<TextView>(R.id.nohan_congestion8)
        nohan_txt9 = findViewById<TextView>(R.id.nohan_congestion9)
        nohan_txt10 = findViewById<TextView>(R.id.nohan_congestion10)

        nohan_title.text = MyAdapter.hansol[0]
        nohan_txt1.text = MyAdapter.hansol[1]
        nohan_txt2.text = MyAdapter.hansol[2]
        nohan_txt3.text = MyAdapter.hansol[3]
        nohan_txt4.text = MyAdapter.hansol[4]
        nohan_txt5.text = MyAdapter.hansol[5]
        nohan_txt6.text = MyAdapter.hansol[6]
        nohan_txt7.text = MyAdapter.hansol[7]
        nohan_txt8.text = MyAdapter.hansol[8]
        nohan_txt9.text = MyAdapter.hansol[9]
        nohan_txt10.text = MyAdapter.hansol[10]
    }
}