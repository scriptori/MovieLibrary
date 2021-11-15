package me.sf.movielibrary.retrofit

interface SearchRequest<T> : Request {
    var currentSearchCriteria: Pair<String, Int>
    fun search(value: Pair<String, Int>)
}
