package com.project.infleran_booksearchapp.util

import com.project.infleran_booksearchapp.BuildConfig

object Constants {
    val SEARCH_BOOKS_TIME_DELAY: Long = 100
    const val BASE_URL = "https://dapi.kakao.com/"

    //local.properties 에 선언
    const val API_KEY = BuildConfig.KAKAO_REST_KEY

    //    const val API_KEY = "3ae088371c69ba3debd225b9f917f916"
    const val DATASTORE_NAME = "preferences_datastore"
    const val PAGING_SIZE = 15;

}