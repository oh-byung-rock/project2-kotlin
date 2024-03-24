package kr.co.byungrock.apptest

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.byungrock.apptest.Subway.Companion.subway
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
//    민수 : News.kt 에서 주 생성자 내에서 변수를 선언한뒤 해당 클래스객체를 참조시킨뒤
//    멤버변수에서 사용가능하게 var를 지정해서 사용하면 아래 코드처럼 mainactivity클래스객체를 instance에 지정하는것이
//    원리대로면 가능한데, 실제로 안되는 이유가 궁금

    init {
        instance=this
//        만약 var intance=this 로 하는경우 init안에 instance와 그 외 instance변수를 이름만같은 서로다른변수로 인식해서
//        초기화가 안된다. 그러니 var를 빼줘야 init외에서도 instance값을 사용할수있다.
    }
    companion object{
        lateinit var instance:MainActivity
//        변수에 직접적으로 값을 지정하는경우에만 var 를 쓰고ㅐㅜ
//        var instance:MainActivity처럼 껍데기만 선언된 경우에는 lateinit var 를써주자
//        설령 껍데기만 선언되었는데 var만 쓰는경우도있는데, 이때는 알아서 인식해서 lateinit을 주석처리해주니 걱정말자
        fun ApplicationContext(): Context {
            return  instance.applicationContext
        }
    }


    lateinit var text18:TextView
    lateinit var iii:LinearLayout
    lateinit var myAdapter: MyAdapter
    lateinit var homeFragment:HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val sAdapter = ArrayAdapter.createFromResource(this, R.array.catrgories, android.R.layout.simple_spinner_dropdown_item)
        categoryComboBox.setAdapter(sAdapter);

        // 버튼을 누르면 쓰레드 동작
        btnRemove.setOnClickListener {
            var k:String="http://swopenapi.seoul.go.kr/api/subway/4742614c45766a6c313231555a76546c/json/realtimePosition/0/50/2%ED%98%B8%EC%84%A0"
            run(k);
            Log.i("열차조회","")
            Log.i("들어온 정보확인",k)
        }

        btnRemove2.setOnClickListener{
            Log.i("호선조회","")
            homeFragment=HomeFragment()
            val transaction = supportFragmentManager.beginTransaction().replace(R.id.frame_layout, homeFragment)
            transaction.commit()
        }
    }

    fun run( url: String){
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        val response = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }
            // main thread말고 별도의 thread에서 실행해야 함.
            override fun onResponse(call: Call, response: Response) {
                Thread{

                    var str = response.body()?.string()
//                    '?' 와 '!!' 차이
//                    ? 는 null을 허용하는 null처리, !!은 null을 허용하지않는 null처리
//                    사실상 ?를 많이 사용하며, !!은 확신하지않는이상 사용하지않는다.

//                    runOnUiThread{ }
                    var j=JSONObject(str)

                    if(j.isNull("realtimePositionList")){
                        Log.e("오민수","없음")
                    }else{
                        var jlist= j.getJSONArray("realtimePositionList")
                        Log.i("전체 길이",jlist.length().toString())
                        // var jf=jlist.getJSONObject(0)

                        var temp= mutableListOf<String>()
                        var temp2= mutableListOf<String>()

                        var count = 0
                        var count2 = 0

                        for(i in 0 until jlist.length()) {
                            var jf = jlist.getJSONObject(i)
                            if(jf.getString("updnLine") == "0"){
                            count++

                            Log.i("0은 상행 10개", jf.getString("statnNm"))
                            Log.i("0은 상행 10개", jf.getString("updnLine"))
                            Log.i("0은 상행 10개", jf.getString("trainNo"))
                            var subid=jf.getString("statnId")
                            var ik= homeFragment.subid.indexOf(subid)//☆일단 들어온 열차가 기존 저장소에 있는지 여부☆

                            if(ik!=-1){
//                                -1 이 외 모든 숫자면 기존subid랑 현재subid가 일치함을 의미

                                temp.add(subid)
                            } //☆존재여부 체크(없으면 넣음)☆
                            Log.i("나이",temp.toString())

                            //### 1.지하철 정보 들어오면
                            var t:Trains?=null
                            //☆열차번호 기존 맞추던거 개별로 변경☆
                            var bbb = jf.getString("trainNo").toInt()
                            for(i in 0 until 10){
                                if(bbb > 3000) {
                                    bbb -= 1000
                                }
                                else if(bbb<3000){
                                    bbb
                                }
                            }
                            //☆여기도 기존에 열차 하나만 넣어줬던거 들어오는거 전부 초기화☆
                            var tid=bbb.toString()

                            if(Subway.trains.containsKey(tid)){//### 2. 현재 존재하는 지하철 아이디면 그냥 역에 박아줌 그리고 뽑아냄(t)
                                t= Subway.trains[tid]
                                Log.d("식식",t.toString())
                                Log.d("식",tid)
//                                Subway.kt의 companion object 내부에 있는 trains 전역변수에 접근
                                // nohan : 여기서 t 는 Trains 객체 자체가된다.

                                if(Subway.subway.containsKey(t?.statnId)){
                                    Log.d("75762",Subway.subway.toString())
                                    Log.d("89752",t?.statnId.toString())
                                    if(t != null){
                                        Subway.subway[t?.statnId]?.InTrain(t)
                                    }
                                }

                            }else{//### 3. 새로운 지하철이면 지하철 객체 생성 후 t에 넣고 지하철역에다 박아줌
//                              var t:Trains?=null 으로 위에서 초기화 시켰는데,
                                t=Trains(jf,tid)
//여기서 Trains객체가 집이라면, t 자체는 Trains 객체의 집주소를 나타낸다.
                                Subway.trains.put(t.trainNo,t)
//                                Log.i("upline='0' 중 첫번째 역ID",subid)
//                                Log.i("upline='0' 중 첫번째 지하철번호", t.toString())
                                Log.i("가나다", t.toString())
                                if(Subway.subway.containsKey(t?.statnId)){
                                    Subway.subway[t?.statnId]?.InTrain(t)
                                    Log.i("가을이2", Subway.subway[t?.statnId].toString())
                                   Log.i("가을이1", t.trainNo)

                                }
                            }
                                runOnUiThread {
                                    //☆여기서 위에 temp 저장해둔 역id를 전체 역id와 대조해서 있을경우 이미지 켜주고 없으면 이미지 꺼줌☆
                                    for(i in 0 until homeFragment.subid.size){
                                        if(temp.contains(homeFragment.subid[i])){
//
                                            Log.i("tempon",temp.toString())
                                            homeFragment.newsArrayList[i].ui.OnTrain()

                                        }else{
                                            Log.i("tempoff",temp.toString())
                                            homeFragment.newsArrayList[i].ui.OffTrain()
                                        }
                                    }
                                    Log.i("도환",homeFragment.subid.size.toString())
                                }
                        Log.i("상행갯수", "$count")
                        }

//--------------------------------------------------------------------

                            else if (jf.getString("updnLine") == "1"){
                            count2++
                                Log.i("1은 하행입니다.", jf.getString("statnNm"))
                                Log.i("1은 하행입니다.", jf.getString("updnLine"))
                                Log.i("1은 하행입니다.", jf.getString("trainNo"))
                                var subid=jf.getString("statnId")
                                var ik= homeFragment.subid2.indexOf(subid)//☆일단 들어온 열차가 기존 저장소에 있는지 여부☆

                                if(ik!=1){//☆존재여부 체크(없으면 넣음)☆
                                    temp2.add(subid)
                                } //☆존재여부 체크(없으면 넣음)☆
                                //### 1.지하철 정보 들어오면
                                var t:Trains?=null
//                                Subway.kt 내 class Trains 는 기본적으로 class가 public이라 접근가능
                                //☆열차번호 기존 맞추던거 개별로 변경☆
                                var bbb = jf.getString("trainNo").toInt()
                                for(i in 0 until 10){
                                    if(bbb > 3000) {
                                        bbb -= 1000
                                    }
                                    else if(bbb<3000){
                                        bbb
                                    }
                                }
                                //☆여기도 기존에 열차 하나만 넣어줬던거 들어오는거 전부 초기화☆
                                var tid=bbb.toString()

                                if(Subway.trains2.containsKey(tid)){//### 2. 현재 존재하는 지하철 아이디면 그냥 역에 박아줌 그리고 뽑아냄(t)
                                    Log.i("train2 _ if문 시작",Subway.trains2.toString())
                                    t= Subway.trains2[tid]
                                    // mutablemap에서 <string,trains> 라는 구성이 만약 <111,1234> <112,4567>일때 tid가 111이면 1234라는 값을 가진다. 즉, tid = key 에 해당하는 value를 뽑는것
                                    if(Subway.subway2.containsKey(t?.statnId)){
                                       Subway.subway2[t?.statnId]?.InTrain2(t as Trains)

                                    }
                                    Log.i("train2 _ if문 끝",Subway.trains2.toString())
                                    Log.i("train2 갯수 if",Subway.trains2.size.toString())
                                }else{//### 3. 새로운 지하철이면 지하철 객체 생성 후 t에 넣고 지하철역에다 박아줌
                                    Log.i("train2 _ else문 시작",Subway.trains2.toString())
                                    t=Trains(jf,tid)
                                    Subway.trains2.put(t.trainNo,t)

                                    if(Subway.subway2.containsKey(t?.statnId)){
                                        Subway.subway2[t?.statnId]?.InTrain2(t)
                                    }
                                    Log.i("train2 _ else문 끝",Subway.trains2.toString())
                                    Log.i("train2 갯수 else",Subway.trains2.size.toString())
                                }
                                // Log.d("asd",t!!.statnNm.toString())

                                runOnUiThread {
                                    //☆여기서 위에 temp 저장해둔 역id를 전체 역id와 대조해서 있을경우 이미지 켜주고 없으면 이미지 꺼줌☆
                                    for(i in 0 until homeFragment.subid2.size){
                                        if(temp2.contains(homeFragment.subid2[i])){
                                            homeFragment.newsArrayList[i].ui2.OnTrain()

                                        }else{
                                            homeFragment.newsArrayList[i].ui2.OffTrain()
                                        }
                                    }
                                }
                            Log.i("하행갯수", "$count2")
                            }
                        }

                    }
                }.start()
            }
        })
    }
}

