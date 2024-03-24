package kr.co.byungrock.apptest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader


class MyAdapter(private val newsList : ArrayList<News>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    companion object{
        var hansol = mutableListOf<String>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newsList[position]

        //☆기존에 ui 바로 넣던거 함수로 바꿔서 로직 추가함☆
        currentItem.ui.SetImage(holder.titleImage)
        currentItem.ui.image2=holder.titleImage2;
        currentItem.ui2.SetImage2(holder.titleImage3)
        holder.titleImage3.setImageResource(currentItem.titleImage3)
        holder.titleImage2.setImageResource(currentItem.titleImage2)
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.tvHeading.text = currentItem.heading

        fun readPredictionsFromFile(context: Context): String {
            val stringBuilder = StringBuilder()
            try {
                val reader = BufferedReader(InputStreamReader(context.assets.open("predictions_20230719.txt")))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line).append("\n")
                }
                reader.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return stringBuilder.toString()
        }

        holder.tvHeading.setOnClickListener {
            val predictionsText = readPredictionsFromFile(it.context)
            Log.e("predictions_20230719 : ", predictionsText)
            val mina = predictionsText.trim().lines().map { line ->
                val values = line.split("\\s+".toRegex())
                values[1]
            }

            if (currentItem.heading == "구의"){
            hansol.add(currentItem.heading)
            hansol.addAll(mina)
            Log.e("실험2", hansol.toString())

            val intent: Intent = Intent(MainActivity.ApplicationContext(), NohanActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            MainActivity.ApplicationContext().startActivity(intent)}
            else {
                Log.e("역 이름 : ", currentItem.heading)
            }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
//        list_item.xml
        val titleImage3 : ShapeableImageView = itemView.findViewById(R.id.title_image3)
        val titleImage2 : ShapeableImageView = itemView.findViewById(R.id.title_image2)
        // 열차 노선라인
        val titleImage : ShapeableImageView = itemView.findViewById(R.id.title_image)
        // 열차 상행
        val tvHeading : TextView = itemView.findViewById(R.id.tvHeading)
    }

}