package kr.co.byungrock.apptest

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter : MyAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var newsArrayList: ArrayList<News>
    lateinit var imageId : Array<Int>
    lateinit var imageId2 : Array<Int>
    lateinit var imageId3 : Array<Int>
    lateinit var heading : Array<String>
    lateinit var subid : Array<String>
    lateinit var subid2 : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("test1", "테스트") // 로그를 출력하는 위치 변경
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("test2", "테스트")
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycle_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MyAdapter(newsArrayList)
        recyclerView.adapter = adapter
//        RecyclerView에 연결할 어댑터를 설정 = 개발자가 선언한 Myadapter.kt

    }

    fun dataInitialize() {

        newsArrayList = arrayListOf<News>()
        imageId = arrayOf(
//            R은 resource로서 R.drawable.down 은 .. 아래와 같은 원리
//            R.string.head_1은 res/values/strings.xml 파일에서 head_1 이라는 문자열 리소스를 참조
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down,
            R.drawable.down

        )

        heading = arrayOf(
            getString(R.string.head_1),
            getString(R.string.head_2),
            getString(R.string.head_3),
            getString(R.string.head_4),
            getString(R.string.head_5),
            getString(R.string.head_6),
            getString(R.string.head_7),
            getString(R.string.head_8),
            getString(R.string.head_9),
            getString(R.string.head_10),
            getString(R.string.head_11),
            getString(R.string.head_12),
            getString(R.string.head_13),
            getString(R.string.head_14),
            getString(R.string.head_15),
            getString(R.string.head_16),
            getString(R.string.head_17),
            getString(R.string.head_18),
            getString(R.string.head_19),
            getString(R.string.head_20)
        )

        //☆역 코드 넣어주면 알아서 됨(위아래 배열이랑 개수 맞추고 역코드만 맞추면 댈듯)☆
        subid= arrayOf(
            "1002000211",
            "1002000212",
            "1002000213",
            "1002000214",
            "1002000215",
            "1002000216",
            "1002000217",
            "1002000218",
            "1002000219",
            "1002000220",
            "1002000221",
            "1002000222",
            "1002000223",
            "1002000224",
            "1002000225",
            "1002000226",
            "1002000227",
            "1002000228",
            "1002000229",
            "1002000230"
        )

        subid2= arrayOf(
            "1002000211",
            "1002000212",
            "1002000213",
            "1002000214",
            "1002000215",
            "1002000216",
            "1002000217",
            "1002000218",
            "1002000219",
            "1002000220",
            "1002000221",
            "1002000222",
            "1002000223",
            "1002000224",
            "1002000225",
            "1002000226",
            "1002000227",
            "1002000228",
            "1002000229",
            "1002000230"
        )

        imageId2 = arrayOf(
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline,
            R.drawable.noline
        )

        imageId3 = arrayOf(
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up,
            R.drawable.up
        )

        for (i in subid.indices) {
            Log.d("잘채워졌나", "subid[$i]: ${subid[i]}")
        }

        for (i in imageId.indices){
            //☆메인 액티비티 oncreate에서 추가하던거 여기로 바꿔서 위에 변수만 추가하면 알아서 추가되게함☆
            var subway=Subway()

            // // 여기서 Subway는 class Subway를 의미
            Subway.subway.put(subid[i],subway)
            Log.d("test3", "테스트")
            // 여기서 Subway는 class Subway의 companion object를 의미
            Subway.subway2.put(subid[i],subway)
            // 여기서 Subway는 class Subway의 companion object를 의미
            //☆news_UI 변수에 지하철 객체 넣어서 활용도 있게 바꿈☆
            // heading : 역이름
            // imageId2 : 호선 라인 이미지
            // imageId3 : 상행 지하철 이미지
            val news = News(imageId[i],heading[i],News_UI(subway),News_UI(subway),imageId2[i],imageId3[i])
            newsArrayList.add(news)
            // HomeFragment 에서 선언한 newsArrayList에 news 변수들을 할당한다
        }


    }

}