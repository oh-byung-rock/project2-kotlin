package kr.co.byungrock.apptest

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_sub.*
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.*
import kotlin.concurrent.thread

class BlueTooth : AppCompatActivity() {
    private val REQUEST_ENABLE_BT = 10 // 블루투스 활성화 상태
    private var bluetoothAdapter: BluetoothAdapter? = null  // 블루투스 어댑터
    private var devices: Set<BluetoothDevice>? = null // 블루투스 디바이스 데이터 셋
    private var bluetoothDevice: BluetoothDevice? = null // 블루투스 디바이스
    private var bluetoothSocket: BluetoothSocket? = null // 블루투스 소켓
    private var outputStream: OutputStream? = null // 블루투스에 데이터를 출력하기 위한 출력 스트림
    private var inputStream: InputStream? = null // 블루투스에 데이터를 입력하기 위한 입력 스트림
    private var workerThread: Thread? = null // 문자열 수신에 사용되는 쓰레드
    private var readBuffer: ByteArray? = null // 수신 된 문자열을 저장하기 위한 버퍼
    private var readBufferPosition = 0 // 버퍼 내 문자 저장 위치
    private var textViewReceive: TextView? = null  // 수신 된 데이터를 표시하기 위한 텍스트 뷰
    lateinit var  chair : ImageView
    lateinit var  subsub : ImageView
    private lateinit var getResult: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)
        chair = findViewById(R.id.imageView)
        subsub = findViewById(R.id.imageView2)
        Log.e("ㅇ","oncreate")
        textViewReceive = findViewById(R.id.textView_receive) as TextView
        //★바뀐 사용★
        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) { //★onActivityResult 대신 사용 같음, 확인했을때 블루투스 체크로★
                selectBluetoothDevice() // 블루투스 디바이스 선택 함수 호출
            } else { // '취소'를 눌렀을 때
                // 여기에 처리 할 코드를 작성하세요.
            }
        }

        // 블루투스 활성화하기
        var bluetoothManager: BluetoothManager = applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager;
        bluetoothAdapter = bluetoothManager.adapter // 블루투스 어댑터를 디폴트 어댑터로 설정          // 문제1

        Log.e("ㅇ","oncreate2")

        if(bluetoothAdapter == null) { // 디바이스가 블루투스를 지원하지 않을 때
            // 여기에 처리 할 코드를 작성하세요.
            Log.e("ㅇ","oncreate3")
        }
        else { // 디바이스가 블루투스를 지원 할 때
            Log.e("ㅇ","oncreate4")
            if(bluetoothAdapter!!.isEnabled()) { // 블루투스가 활성화 상태 (기기에 블루투스가 켜져있음) !!의미 : 강제로 null이 아님을 지정
                // 본래 코드 : if(bluetoothAdapter.isEnabled())
                selectBluetoothDevice(); // 블루투스 디바이스 선택 함수 호출
            }
            else { // 블루투스가 비 활성화 상태 (기기에 블루투스가 꺼져있음)
                // 블루투스를 활성화 하기 위한 다이얼로그 출력
                Log.e("ㅇ","oncreate5")
                var intent:Intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // val intent = BluetoothAdapter.ACTION_REQUEST_ENABLE 이걸로 되나?
                // 선택한 값이 onActivityResult 함수에서 콜백된다.
                getResult.launch(intent);           //       문제2
                //★바뀐 사용★
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==100){//★블루투스 권한 요청하고 결과 받는건데 requestcode가 100이면 권한 획득같은데 검색해봐야함(아마 permissions 에 권한 이름 있을거)★
            Connect()//★권한 얻었으니까 블루투스 연결함★
        }
    }
    //★아래 변수 2개는 권한 요청할때 쓰는 변수인데 나도 잘 모름★
    val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>( Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.BLUETOOTH_CONNECT)
    // 미니10개 네모 누르면 팅기던게 이거 추가하니 Manifest.permission.BLUETOOTH_CONNECT 해결, 의미찾기
    fun selectBluetoothDevice() {

        Log.e("ㅇ","oncreate6")
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("ㅇ","oncreate7")
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                //설명 필요 (사용자가 요청을 거부한 적이 있음)
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE )//★블루투스 권한 요청함★
            }else{
                //설명 필요하지 않음
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE )//★블루투스 권한 요청함★
            }
        }else{
            Connect()//★권한 있으니까 걍 블루투스 연결함★
        }
    }
    // 이미 페어링 되어있는 블루투스 기기를 찾습니다.
    @SuppressLint("MissingPermission")//★블루투스 권한 체크하라고 에러뱉는데 그거 무시하는 헤더인듯★
    fun Connect(){
        Log.e("ㅇ","oncreate8")
        devices = bluetoothAdapter?.bondedDevices

        // 페어링 된 디바이스의 크기를 저장
        var pariedDeviceCount = devices?.size

        // 페어링 되어있는 장치가 없는 경우
        if (pariedDeviceCount === 0) {

            // 페어링을 하기위한 함수 호출
            Log.d("ㅇ","####################2")
        }
        else {

            // 디바이스를 선택하기 위한 다이얼로그 생성
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("페어링 되어있는 블루투스 디바이스 목록")

            // 페어링 된 각각의 디바이스의 이름과 주소를 저장
            val list: MutableList<String> = ArrayList()

            Log.d("ㅇ","####################")
            // 모든 디바이스의 이름을 리스트에 추가
            for (bluetoothDevice in devices!!) {
                list.add(bluetoothDevice.name)
                Log.d("ㅇ",bluetoothDevice.name)
            }
            list.add("취소")


            // List를 CharSequence 배열로 변경
            val charSequences = list.toTypedArray<CharSequence>()
            list.toTypedArray<CharSequence>()


            // 해당 아이템을 눌렀을 때 호출 되는 이벤트 리스너
            builder.setItems(charSequences,
                DialogInterface.OnClickListener { dialog, which -> // 해당 디바이스와 연결하는 함수 호출
                    connectDevice(charSequences[which].toString())
                })


            // 뒤로가기 버튼 누를 때 창이 안닫히도록 설정
            builder.setCancelable(false)

            // 다이얼로그 생성
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }


    @SuppressLint("MissingPermission")
    fun connectDevice(deviceName: String) {

        Log.d("ㅇ1",deviceName)
        // 사용자가 선택한 이름과 같은 디바이스로 설정하고 반복문 종료
        // 페어링 된 디바이스들을 모두 탐색
        for (tempDevice in devices!!) {

            if (deviceName == tempDevice.name) {
                bluetoothDevice = tempDevice
                Log.d("ㅇ2","deviceName")
                break
            }
        }

        // UUID 생성
        val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // 시바 이건 또 머야
        //★블루투스에서 uuid 따로 지정 안하면 고정인듯? 잘몰라서 걍 인터넷에 쳐서 붙여넣음★
        // Rfcomm 채널을 통해 블루투스 디바이스와 통신하는 소켓 생성
        try {
            bluetoothSocket = bluetoothDevice!!.createRfcommSocketToServiceRecord(uuid)
            thread {//★스레드는 찾은곳에서 쓰길레 넣었는데 그냥 쓰면댈듯 어짜피 메인스레드 실행되는거라 따로 스레드 만드는것도 괜찮음★
                try {
                    if(bluetoothSocket!!.isConnected) bluetoothSocket!!.close() else {
                        bluetoothSocket!!.connect()
                        // 데이터 송,수신 스트림을 얻어옵니다.
                        outputStream = bluetoothSocket?.outputStream
                        inputStream = bluetoothSocket?.inputStream
                        // 데이터 수신 함수 호출
                        receiveData()
                        sendData("통신완료")//★데이터 아두이노에 보냄★
                    }
                    Log.d("Bluetooth Activity", "Socket is connected: ${bluetoothSocket!!.isConnected}")
                } catch (ex: Exception) {
                    Log.e("Bluetooth Activity", ex.toString())
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun sendData(text: String) {

        // 문자열에 개행문자("\n")를 추가해줍니다.
        var text = text
        text += "\n"
        try {
            // 데이터 송신
            outputStream!!.write(text.toByteArray())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    fun receiveData() {
        Log.e("ㅇ","e")
        val handler = android.os.Handler(Looper.getMainLooper())//★뭔지 모르지만 인터넷에서 하라는데로 함★

        // 데이터를 수신하기 위한 버퍼를 생성
        readBufferPosition = 0
        readBuffer = ByteArray(1024) // 임시로 1024바이트 만들고 데이터 오면 배열에 넣는다. 줄바꿈시 버퍼(배열)길이 만큼 배열생성후 문자열로 바꾼다.


        // 데이터를 수신하기 위한 쓰레드 생성
        workerThread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    // 데이터를 수신했는지 확인합니다.
                    val byteAvailable = inputStream!!.available()
                    // 데이터가 수신 된 경우
                    if (byteAvailable > 0) {
                        runOnUiThread {
                            click1.setBackgroundColor(Color.parseColor("#C4261F"));
                        }
                        // 입력 스트림에서 바이트 단위로 읽어 옵니다.
                        val bytes = ByteArray(byteAvailable)
                        Log.e("ㅇ",byteAvailable.toString())
                        inputStream!!.read(bytes)

                        // 입력 스트림 바이트를 한 바이트씩 읽어 옵니다.
                        for (i in 0 until byteAvailable) {
                            val tempByte = bytes[i]
                            Log.e("받아왔음","byte "+tempByte)
                            // 개행문자를 기준으로 받음(한줄)
                            if (tempByte == '\n'.code.toByte()) {//★아두이노에서 \n 마지막에 추가한 이유★

                                // readBuffer 배열을 encodedBytes로 복사
                                val encodedBytes = ByteArray(readBufferPosition)//★배열크기 지정으로 만듬★
                                System.arraycopy(
                                    readBuffer,
                                    0,
                                    encodedBytes,
                                    0,
                                    encodedBytes.size
                                )//★배열 크기로 지정해서 만든거에 바이트 복사해서 집어넣음★

                                // 인코딩 된 바이트 배열을 문자열로 변환
                                val text = String(encodedBytes)
                                //★뒤에 인코딩 유니코드로 지정했는데 String에서 as Charset으로 바로 변경이 안되는함 그래서 걍 지움★
                                //★디폴트는 utf8인데 기본 영특숫자(아스키영역 외 3byte처리 _ 영문숫자특수문자는 1byte a1-2 / a가-4)는 아스키코드랑 똑같이 따라가서 상관없음 대신 아두이노에서 한글 보낼때는 아두이노에서 utf8로 바꿔서 보내야함★
                                readBufferPosition = 0
                                handler.post(Runnable { // 텍스트 뷰에 출력
                                    textViewReceive!!.append(//★append라 계속 추가됨★
                                        """
                                        $text
                                      
                                        """.trimIndent()
                                    )
                                })
                            } // 개행 문자가 아닐 경우
                            else{//★일반 문자는 계속 저장해둠★
                                readBuffer!![readBufferPosition++] = tempByte
                            }
                        }
                    }
                    else{
                        runOnUiThread {
                            click1.setBackgroundColor(Color.parseColor("#4CB074"));
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                try {

                    // 1초마다 받아옴
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        workerThread!!.start()
    }

}