package com.bytedance.jstu.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Response

class DictActivity : AppCompatActivity(), View.OnClickListener {
    private var wordText : EditText? = null
    private var translateBtn : Button? = null
    private var phoneticSymbolView : TextView? = null
    private var translationView : TextView? = null
    private var exampleSentenceView : TextView? = null
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://dict.youdao.com/")
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dict)
        bindView()
        bindClickListener()
    }

    private fun bindView() {
        wordText = findViewById(R.id.et_input)
        translateBtn = findViewById(R.id.btn_translate)
        phoneticSymbolView = findViewById(R.id.tv_phonetic)
        translationView = findViewById(R.id.tv_interpretation)
        exampleSentenceView = findViewById(R.id.tv_egsentence)
    }

    private fun bindClickListener() {
        translateBtn?.setOnClickListener(this)
    }

    private fun validInput(v : EditText?) : Boolean {
        return v?.text!!.isNotBlank()
    }

    private fun makeTranslationRequest(text: String) {
//        Log.d("DictService", "request string: \"$text\"")
        val retrofitTranslationService = retrofit.create(DictService::class.java)
        val asyncCall = retrofitTranslationService.get(text)
        asyncCall.enqueue(object : Callback<DictData> {
            override fun onResponse(call: Call<DictData>, response: Response<DictData>) {
                Log.d("DictService", "Response Success")
                val dictPack = response.body()
                setTranslationViewDisplay(dictPack)
            }

            override fun onFailure(call: Call<DictData>, t: Throwable) {
                Log.d("DictService", "Response Fail")
                Toast.makeText(this@DictActivity, "网络连接错误", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setTranslationViewDisplay(d : DictData?) {
        Log.d("DictService", "ViewDisplay")
        val word = d?.getEc()?.word
        var symbolStr = ""
        if (word != null) {
            val ukSymbol = word?.get(0)?.ukphone
            val usSymbol = word?.get(0)?.usphone
            if (!ukSymbol.isNullOrBlank()) {
                symbolStr += "英式 [$ukSymbol] "
            }
            if (!usSymbol.isNullOrBlank()) {
                symbolStr += "美式 [$usSymbol] "
            }
        }
        if (symbolStr == "") {
            symbolStr = "无"
        }
        phoneticSymbolView?.text = symbolStr
        val webTranslation = d?.getWeb_trans()?.web_translation
        var translationStr = ""
        if (webTranslation != null) {
            val trans = webTranslation?.get(0)?.trans
            for (i in 0 until trans?.size!!) {
                translationStr += trans[i].value
                if (i != trans.size - 1) {
                   translationStr += "; "
                }
            }
        }
        if (translationStr == "") {
            translationStr = "无"
        }
        translationView?.text = translationStr
        val sentencePair = d?.getBlng_sents_part()?.sentence_pair
        var sentenceStr = ""
        if (sentencePair != null) {
            for (i in sentencePair.indices) {
                sentenceStr += sentencePair[i].sentence + "\n" + sentencePair[i].sentence_translation + "\n"
            }
        }
        if (sentenceStr == "") {
            sentenceStr = "无"
        }
        exampleSentenceView?.text = sentenceStr
    }

    override fun onClick(p0: View?) {
        when(p0) {
            translateBtn -> {
//                Log.d("View", "Click on translating button.")
                if (validInput(wordText)) {
//                    Log.d("DictService", "package request.")
                    makeTranslationRequest(wordText?.text.toString())
                }
            }
        }
    }
}