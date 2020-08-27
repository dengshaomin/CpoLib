package com.code.cpodemo

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code.cpo.CpoTool
import com.code.cpodemo.convert.FastJsonConvertFactory
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.EventListener
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    val TAG = this.javaClass.simpleName
    var adapter = FpsAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        setupRadioGroup()
        start.setOnClickListener {
            CpoTool.show(applicationContext)
        }
        stop.setOnClickListener {
            CpoTool.hide(applicationContext as Application)
        }
        request.setOnClickListener {
            request()
        }


    }

    var retrofit: Retrofit? = null
    var api: IPost? = null
    private fun request() {
        var ok = OkHttpClient.Builder()
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(FastJsonConvertFactory())
                .build()
            api = (retrofit as Retrofit).create(IPost::class.java)

        }
        Thread(Runnable {
            var call = api?.request(object : HashMap<String, String>() {
                init {
                    put("type", "yuantong")
                    put("postid", "11111111111")
                }

            })
            call?.enqueue(object : Callback<PostBean> {
                override fun onFailure(call: Call<PostBean>, t: Throwable) {
                    var a = 1
                }

                override fun onResponse(call: Call<PostBean>, response: Response<PostBean>) {
                    var a = 1
                }
            })
        }).start()
    }

    private fun setupRadioGroup() {
        adapter.megaBytes = 50f
        adapter.notifyDataSetChanged()
        loadIndicator.progress = 50
        loadIndicator.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                adapter.megaBytes = i.toFloat()
                adapter.notifyDataSetChanged()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }


    class FpsAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var megaBytes = 1f
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var sampleItem = SampleItem(context)
            sampleItem.layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            var holder = object : RecyclerView.ViewHolder(sampleItem) {}
            return holder
        }

        override fun getItemCount(): Int {
            return 255
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder.itemView as SampleItem).onBind(position, megaBytes)
        }

    }
}