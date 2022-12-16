package com.example.myapplication.api

class ApiException(var code: Int, override var message: String) : RuntimeException()