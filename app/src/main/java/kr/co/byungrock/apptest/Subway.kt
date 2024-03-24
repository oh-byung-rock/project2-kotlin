package kr.co.byungrock.apptest

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
//### 역 객체임
class Subway (){
    companion object{
        var selectSub:Subway?=null//### 임시로 선택한 역 객체 저장함
        var selectState:Boolean=false
        var subway = mutableMapOf<String,Subway>()
        var subway2 = mutableMapOf<String,Subway>()
        var trains = mutableMapOf<String,Trains>()
        var trains2 = mutableMapOf<String,Trains>()
    }

//    var  subid:String
    var trains2= mutableListOf<Trains>()
    var trains= mutableListOf<Trains>()

//    init {
//        this.subid=subid
//    }



    //### 지하철 역에 들어왔을때 호출
    fun InTrain(t:Trains){
        Log.d("여기1","여기")
        t.subway?.OutTrain(t);
//       = OutTrain(t);  //### 이미 지하철이 다른역에서 왔을때 기존 역에 나갔다고 호출
        trains.add(t)   //### 지하철 저장함
        t.subway=this   //### 지하철 객체에 현재 역객체 저장
        Log.d("우주",trains.toString())

    }

    //### 지하철 나갔을때 호출
    fun OutTrain(t:Trains){
        Log.d("여기2","여기")
     if(trains.contains(t)){
         Log.d("여기3","여기")
         trains.remove(t)   //### 열차 배열에서 나간 열차 버림
     }
    }

    fun InTrain2(t:Trains){
        t.subway2?.OutTrain2(t);  //### 이미 지하철이 다른역에서 왔을때 기존 역에 나갔다고 호출
        Log.d("열차2들옴",t.trainNo)

        trains2.add(t)   //### 지하철 저장함
        t.subway2=this   //### 지하철 객체에 현재 역객체 저장
    }

    //### 지하철 나갔을때 호출
    fun OutTrain2(t:Trains){
        Log.d("열차2나감",t.trainNo)
        if(trains2.contains(t)){
            trains2.remove(t)   //### 열차 배열에서 나간 열차 버림
        }
    }

}
//class 라는 집이있으며, 이 집에는 처음에 옵션으로 선택한 주 생성자 a,b 가 있습니다.
//이후 집주인은 집에 물건(객체)들을 추가할때, 주 생성자를 사용하거나 사용하지않거나 선택해서 물건들을 다양한 형태의 결과물로 만들수있습니다.
//### 지하철 객체임
class Trains(var jf:JSONObject,var tid:String){
    var subway:Subway?=null //### 현재 있는 역 객체 저장 변수
    var subway2:Subway?=null //### 현재 있는 역 객체 저장 변수

    lateinit var statnId:String     //현재역번호
    lateinit var statnNm:String     //현재역이름
    lateinit var trainNo:String     //지하철번호
    lateinit var statnTid:String   //종착역번호
    lateinit var statnTnm:String    //종착역이름

    var honjob:String="0"  //### 혼잡도 저장변수
    var honjob1:String="0"
    var honjob2:String="0"
    var honjob3:String="0"
    var honjob4:String="0"
    var honjob5:String="0"
    var honjob6:String="0"
    var honjob7:String="0"
    var honjob8:String="0"
    var honjob9:String="0"
    var honjob10:String="0"


    // updnLine : 0 일때 상행(어플상 내려가는쪽), 1 일때 하행 (올라가는 쪽)
    init{
        statnId=jf.getString("statnId")
        Log.i("먼저s", jf.toString())
//        현재 api 전체 역ID 를 읽어온다
        trainNo=tid
        Log.i("혜린2", "$trainNo")
//        현재 api 전체 지하철No을 읽어온다.
        statnNm=jf.getString("statnNm")
        statnTid=jf.getString("statnTid")
        statnTnm=jf.getString("statnTnm")

        trainNoList.add(trainNo)
        Log.i("꽃", trainNoList.toString())
        Log.i("꽃길이", trainNoList.size.toString())
        trainNoList2.add(statnId)
        Log.i("감", trainNoList2.toString())
        Log.i("감길이", trainNoList2.size.toString())
    }

    companion object {
        var trainNoList = arrayListOf<String>() // 기존 trainNo들을 저장할 리스트
        var trainNoList2 = arrayListOf<String>() // 기존 trainNo들을 저장할 리스트
    }

}