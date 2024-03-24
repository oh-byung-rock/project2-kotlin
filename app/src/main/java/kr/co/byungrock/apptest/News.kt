package kr.co.byungrock.apptest

import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

data class News(var titleImage: Int, var heading: String, var ui: News_UI, var ui2: News_UI, var titleImage2: Int, var titleImage3: Int)

class News_UI(var subway: Subway) {
    var image: ImageView? = null
    var image2: ImageView? = null
    var isOn: Boolean = false

    fun SetImage(ui: ImageView) {
        image = ui
        ui.setOnClickListener {
            GetHonjobs()
        }
        RefreshTrainView()
    }

    fun SetImage2(ui2: ImageView) {
        image = ui2
        ui2.setOnClickListener {
            GetHonjobs2()
        }
        RefreshTrainView()
    }

    fun OnTrain() {
        isOn = true
        RefreshTrainView()
    }

    fun OffTrain() {
        isOn = false
        RefreshTrainView()
    }

    private fun RefreshTrainView() {
        if (isOn) {
            image?.visibility = View.VISIBLE
        } else {
            image?.visibility = View.INVISIBLE
        }
    }

    private fun getHonjobsCommon(trains: List<Trains>) {
        val isok = Array<Boolean>(trains.size) { false }

        for (i in 0 until trains.size) {
            GetHonjob(trains[i], i, isok)
        }

        for (i in 0 until isok.size) {
            while (!isok[i]) {
                Thread.sleep(100)
            }
        }
    }

    fun GetHonjobs() {
        Thread {
            val isok = Array<Boolean>(subway.trains.size) { false }

            getHonjobsCommon(subway.trains)

            for (i in 0 until subway.trains.size) {
                Log.e(i.toString() + "번 열차 혼잡도입니다.", subway.trains[i].honjob)
            }

            Subway.selectSub = subway
            Subway.selectState = true
            val intent: Intent = Intent(MainActivity.ApplicationContext(), SubActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            MainActivity.ApplicationContext().startActivity(intent)
        }.start()
    }

    fun GetHonjobs2() {
        Thread {
            val isok = Array<Boolean>(subway.trains2.size) { false }

            getHonjobsCommon(subway.trains2)

            for (i in 0 until subway.trains2.size) {
                Log.e(i.toString() + "번 열차 혼잡도", subway.trains2[i].honjob)
            }

            Subway.selectSub = subway
            Subway.selectState = false
            val intent: Intent = Intent(MainActivity.ApplicationContext(), SubActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            MainActivity.ApplicationContext().startActivity(intent)
        }.start()
    }

    fun GetHonjob(train: Trains, index: Int, isok: Array<Boolean>) {
        val client = OkHttpClient()
        val maxRetries = 3

        fun executeRequest(retries: Int) {
            val request = Request.Builder()
                .url("https://apis.openapi.sk.com/puzzle/congestion-train/rltm/trains/2/${train.trainNo}")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("appkey", "l7xx031bf4a6cb9a4ad1bd59bf80dc9123c7")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    if (retries < maxRetries) {
                        Log.e("에러", "retry 시도 (${retries + 1})")
                        executeRequest(retries + 1)
                    } else {
                        train?.honjob = "-1"
                        isok[index] = true
                        Log.e("김밥", "한 줄")
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    Thread {
                        var str = response.body()?.string()
                        Log.i("aaa1", str as String)
                        var j = JSONObject(str)
                        if (!j.isNull("data")) {
                            var j_data = j.getJSONObject("data")
                            if (!j_data.isNull("congestionResult")) {
                                var j_congestionResult = j_data.getJSONObject("congestionResult")
                                train?.honjob = j_congestionResult.getString("congestionTrain")

                                var arr = j_congestionResult.getString("congestionCar").split('|')
                                Log.e("일하면1",arr.toString())
                                Log.e("일하면2",arr[0])

                                if (arr.size >= 10) {
                                    train?.honjob1 = arr[arr.size - 10]
                                    train?.honjob2 = arr[arr.size - 9]
                                    train?.honjob3 = arr[arr.size - 8]
                                    train?.honjob4 = arr[arr.size - 7]
                                    train?.honjob5 = arr[arr.size - 6]
                                    train?.honjob6 = arr[arr.size - 5]
                                    train?.honjob7 = arr[arr.size - 4]
                                    train?.honjob8 = arr[arr.size - 3]
                                    train?.honjob9 = arr[arr.size - 2]
                                    train?.honjob10 = arr[arr.size - 1]
                                } else {
                                    Log.e("배열의 크기가 10보다 작음","")
                                }
                            }
                        }
                        isok[index] = true
                    }.start()
                }
            })
        }
        executeRequest(0)
    }
}
