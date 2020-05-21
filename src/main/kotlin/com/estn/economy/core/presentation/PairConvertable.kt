package com.estn.economy.core.presentation

interface PairConvertable {

    fun convertToPair() : Pair<Any,Any>

}

fun Collection<PairConvertable>.mapToPairs() : List<Pair<Any,Any>> {
    return this.map {
        it.convertToPair()
    }
}