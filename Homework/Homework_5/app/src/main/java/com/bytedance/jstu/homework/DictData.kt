package com.bytedance.jstu.homework

import com.google.gson.annotations.SerializedName

class DictData {
    private var web_trans: Web_trans? = null

    fun setWeb_trans(web_trans: Web_trans?) {
        this.web_trans = web_trans
    }

    fun getWeb_trans(): Web_trans? {
        return web_trans
    }

    class Web_trans {
        @SerializedName("web-translation")
        var web_translation: List<Web_translation>? = null
    }

    class Web_translation {
        var key: String? = null
        var trans: List<Trans>? = null
    }

    class Trans {
        var value: String? = null
        var support = 0
        var url: String? = null
    }

    private var blng_sents_part: Blng_sents_part? = null


    fun setBlng_sents_part(blng_sents_part: Blng_sents_part?) {
        this.blng_sents_part = blng_sents_part
    }

    fun getBlng_sents_part(): Blng_sents_part? {
        return blng_sents_part
    }


    class Blng_sents_part {
        @SerializedName("sentence-pair")
        var sentence_pair: List<Sentence_pair>? = null
    }

    class Sentence_pair {
        var sentence: String? = null

        @SerializedName("sentence-eng")
        var sentence_eng: String? = null

        @SerializedName("sentence-translation")
        var sentence_translation: String? = null
    }


    private var ec: Ec? = null

    fun setEc(ec: Ec?) {
        this.ec = ec
    }

    fun getEc(): Ec? {
        return ec
    }

    class Ec {
        var word: List<Word>? = null
    }

    class Word {
        var trs: List<Trs>? = null
        var usphone: String? = null
            set(phone) {
                field = usphone
            }
        var ukphone: String? = null
            set(phone) {
                field = ukphone
            }
    }

    class Trs {
        var tr: List<Tr>? = null
    }

    class Tr {
        var l: StrList? = null
    }

    class StrList {
        var str: List<String>? = null
    }
}