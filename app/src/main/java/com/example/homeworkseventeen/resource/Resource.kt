package com.example.homeworkseventeen.resource

sealed class Resource<D>(
) {

    data class Success<D>(val responseData: D) : Resource<D>()
    data class Failed<D>(val message: String) : Resource<D>()
    class Loading<D>() : Resource<D>()

//    object Loading : Resource<Nothing>()
}