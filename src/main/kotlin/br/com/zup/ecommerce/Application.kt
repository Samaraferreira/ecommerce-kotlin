package br.com.zup.ecommerce

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("br.com.zup.ecommerce")
		.start()
}

