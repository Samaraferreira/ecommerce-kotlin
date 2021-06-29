package br.com.zup.ecommerce.category

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.exceptions.HttpStatusException

@Controller("/api/v1/categories")
class CategoryController(
    val categoryRepository: CategoryRepository
) {

    @Post
    fun create(@Body request: NewCategoryRequest): HttpResponse<Any> {

        if (request.parentId != null) {
            val parent = categoryRepository.findById(request.parentId)
                .orElseThrow { HttpStatusException(HttpStatus.NOT_FOUND, "Category not found") }

            val newCategory = request.toModel(parent)
            categoryRepository.save(newCategory)
        } else {
            val newCategory = request.toModel(null)
            categoryRepository.save(newCategory)
        }

        return HttpResponse.ok();
    }

}