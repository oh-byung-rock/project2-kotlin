package kr.co.byungrock.apptest

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sub.*

class SubActivity : AppCompatActivity() {
    lateinit var  txttotal: TextView
    lateinit var  txt1: TextView
    lateinit var  txt2: TextView
    lateinit var  txt3: TextView
    lateinit var  txt4: TextView
    lateinit var  txt5: TextView
    lateinit var  txt6: TextView
    lateinit var  txt7: TextView
    lateinit var  txt8: TextView
    lateinit var  txt9: TextView
    lateinit var  txt10: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        // val btnbtn = findViewById<Button>(R.id.button)
        // val pageback = findViewById<Button>(R.id.pageback)
        click1.setOnClickListener {
            Log.i("화면전환 이루어짐","")
            val intent = Intent(this, BlueTooth::class.java)
            startActivity(intent)
        }
        click2.setOnClickListener {
            Log.i("화면전환 이루어짐","")
            val intent = Intent(this, BlueTooth::class.java)
            startActivity(intent)
        }
        click3.setOnClickListener {
            Log.i("화면전환 이루어짐","")
            val intent = Intent(this, BlueTooth::class.java)
            startActivity(intent)
        }
        click4.setOnClickListener {
            Log.i("화면전환 이루어짐","")
            val intent = Intent(this, BlueTooth::class.java)
            startActivity(intent)
        }
        click5.setOnClickListener {
            Log.i("화면전환 이루어짐","")
            val intent = Intent(this, BlueTooth::class.java)
            startActivity(intent)
        }
        click6.setOnClickListener {
            Log.i("화면전환 이루어짐","")
            val intent = Intent(this, BlueTooth::class.java)
            startActivity(intent)
        }
        click7.setOnClickListener {
            Log.i("화면전환 이루어짐","")
            val intent = Intent(this, BlueTooth::class.java)
            startActivity(intent)
        }
        click8.setOnClickListener {
            Log.i("화면전환 이루어짐","")
            val intent = Intent(this, BlueTooth::class.java)
            startActivity(intent)
        }
        click9.setOnClickListener {
            Log.i("화면전환 이루어짐","")
            val intent = Intent(this, BlueTooth::class.java)
            startActivity(intent)
        }
        click10.setOnClickListener {
            Log.i("화면전환 이루어짐","")
            val intent = Intent(this, BlueTooth::class.java)
            startActivity(intent)
        }

        // ★ Activity > Activity 로 Data받기 시작

        txttotal = findViewById<TextView>(R.id.texttotal)
        txt1 = findViewById<TextView>(R.id.text1)
        txt2 = findViewById<TextView>(R.id.text2)
        txt3 = findViewById<TextView>(R.id.text3)
        txt4 = findViewById<TextView>(R.id.text4)
        txt5 = findViewById<TextView>(R.id.text5)
        txt6 = findViewById<TextView>(R.id.text6)
        txt7 = findViewById<TextView>(R.id.text7)
        txt8 = findViewById<TextView>(R.id.text8)
        txt9 = findViewById<TextView>(R.id.text9)
        txt10 = findViewById<TextView>(R.id.text10)
        // txt.text=Subway.selectSub?.trains?.count().toString()
        var m:MutableList<Trains>?

        if(Subway.selectState){
            m=Subway.selectSub?.trains
            Log.i("김나무",m.toString())
        }else{
            m=Subway.selectSub?.trains2
        }
        Log.d("여기는 서브","열차 수 : " + m?.count().toString())
        for (t in m as MutableList<Trains>){
            Log.d("여기는 서브","열차 "+ t.trainNo+" 혼잡도: " + t.honjob + "맨 끝 혼잡도" + t.honjob10)
            Log.d("1부터9까지",t.honjob1 + t.honjob2 + t.honjob3 + t.honjob4 + t.honjob5 + t.honjob6 + t.honjob7 + t.honjob8 + t.honjob9)

            txttotal.text= t.honjob

            txt1.text= t.honjob1
            val num1: Int = txt1.text.toString().toInt()
            if ( num1 >= 0 && num1 <= 40){
                click1.setBackgroundColor(Color.parseColor("#4CB074"));
            } else if (num1 >= 41 && num1 <= 80){
                click1.setBackgroundColor(Color.parseColor("#EABC36"));
            } else if (num1 >= 81 && num1 <= 120){
                click1.setBackgroundColor(Color.parseColor("#E78E22"));
            } else if (num1 >= 121){
                click1.setBackgroundColor(Color.parseColor("#C4261F"));
            }

            txt2.text= t.honjob2
            val num2: Int = txt2.text.toString().toInt()
            if ( num2 >= 0 && num2 <= 40){
                click2.setBackgroundColor(Color.parseColor("#4CB074"));
            } else if (num2 >= 41 && num2 <= 80){
                click2.setBackgroundColor(Color.parseColor("#EABC36"));
            } else if (num2 >= 81 && num2 <= 120){
                click2.setBackgroundColor(Color.parseColor("#E78E22"));
            } else if (num2 >= 121){
                click2.setBackgroundColor(Color.parseColor("#C4261F"));
            }
            txt3.text= t.honjob3
            val num3: Int = txt3.text.toString().toInt()
            if ( num3 >= 0 && num3 <= 40){
                click3.setBackgroundColor(Color.parseColor("#4CB074"));
            } else if (num3 >= 41 && num3 <= 80){
                click3.setBackgroundColor(Color.parseColor("#EABC36"));
            } else if (num3 >= 81 && num3 <= 120){
                click3.setBackgroundColor(Color.parseColor("#E78E22"));
            } else if (num3 >= 121){
                click3.setBackgroundColor(Color.parseColor("#C4261F"));
            }
            txt4.text= t.honjob4
            val num4: Int = txt4.text.toString().toInt()
            if ( num4 >= 0 && num4 <= 40){
                click4.setBackgroundColor(Color.parseColor("#4CB074"));
            } else if (num4 >= 41 && num4 <= 80){
                click4.setBackgroundColor(Color.parseColor("#EABC36"));
            } else if (num4 >= 81 && num4 <= 120){
                click4.setBackgroundColor(Color.parseColor("#E78E22"));
            } else if (num4 >= 121){
                click4.setBackgroundColor(Color.parseColor("#C4261F"));
            }
            txt5.text= t.honjob5
            val num5: Int = txt5.text.toString().toInt()
            if ( num5 >= 0 && num5 <= 40){
                click5.setBackgroundColor(Color.parseColor("#4CB074"));
            } else if (num5 >= 41 && num5 <= 80){
                click5.setBackgroundColor(Color.parseColor("#EABC36"));
            } else if (num5 >= 81 && num5 <= 120){
                click5.setBackgroundColor(Color.parseColor("#E78E22"));
            } else if (num5 >= 121){
                click5.setBackgroundColor(Color.parseColor("#C4261F"));
            }
            txt6.text= t.honjob6
            val num6: Int = txt6.text.toString().toInt()
            if ( num6 >= 0 && num6 <= 40){
                click6.setBackgroundColor(Color.parseColor("#4CB074"));
            } else if (num6 >= 41 && num6 <= 80){
                click6.setBackgroundColor(Color.parseColor("#EABC36"));
            } else if (num6 >= 81 && num6 <= 120){
                click6.setBackgroundColor(Color.parseColor("#E78E22"));
            } else if (num6 >= 121){
                click6.setBackgroundColor(Color.parseColor("#C4261F"));
            }
            txt7.text= t.honjob7
            val num7: Int = txt7.text.toString().toInt()
            if ( num7 >= 0 && num7 <= 40){
                click7.setBackgroundColor(Color.parseColor("#4CB074"));
            } else if (num7 >= 41 && num7 <= 80){
                click7.setBackgroundColor(Color.parseColor("#EABC36"));
            } else if (num7 >= 81 && num7 <= 120){
                click7.setBackgroundColor(Color.parseColor("#E78E22"));
            } else if (num7 >= 121){
                click7.setBackgroundColor(Color.parseColor("#C4261F"));
            }
            txt8.text= t.honjob8
            val num8: Int = txt8.text.toString().toInt()
            if ( num8 >= 0 && num8 <= 40){
                click8.setBackgroundColor(Color.parseColor("#4CB074"));
            } else if (num8 >= 41 && num8 <= 80){
                click8.setBackgroundColor(Color.parseColor("#EABC36"));
            } else if (num8 >= 81 && num8 <= 120){
                click8.setBackgroundColor(Color.parseColor("#E78E22"));
            } else if (num8 >= 121){
                click8.setBackgroundColor(Color.parseColor("#C4261F"));
            }
            txt9.text= t.honjob9
            val num9: Int = txt9.text.toString().toInt()
            if ( num9 >= 0 && num9 <= 40){
                click9.setBackgroundColor(Color.parseColor("#4CB074"));
            } else if (num9 >= 41 && num9 <= 80){
                click9.setBackgroundColor(Color.parseColor("#EABC36"));
            } else if (num9 >= 81 && num9 <= 120){
                click9.setBackgroundColor(Color.parseColor("#E78E22"));
            } else if (num9 >= 121){
                click9.setBackgroundColor(Color.parseColor("#C4261F"));
            }
            txt10.text= t.honjob10
            val num10: Int = txt10.text.toString().toInt()
            if ( num10 >= 0 && num10 <= 40){
                click10.setBackgroundColor(Color.parseColor("#4CB074"));
            } else if (num10 >= 41 && num10 <= 80){
                click10.setBackgroundColor(Color.parseColor("#EABC36"));
            } else if (num10 >= 81 && num10 <= 120){
                click10.setBackgroundColor(Color.parseColor("#E78E22"));
            } else if (num10 >= 121){
                click10.setBackgroundColor(Color.parseColor("#C4261F"));
            }

        }

        // 초록 #4CB074
        // 노랑 #EABC36
        // 주황 #E78E22
        // 빨강 #C4261F

    }

}