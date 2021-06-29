package br.com.zup.ecommerce.category

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.inject.Inject

@MicronautTest(transactional = false)
internal class CategoryControllerTest(
    val categoryRepository: CategoryRepository
) {

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    companion object {
        const val CATEGORY_NAME = "Cozinha"
    }

    @BeforeEach
    fun setup() {
        categoryRepository.deleteAll()
    }

    @Test
    fun `should register a new category`() {
        val newCategoryRequest = NewCategoryRequest(name = CATEGORY_NAME)

        val request = HttpRequest.POST("/api/v1/categories", newCategoryRequest)
        val response = client.toBlocking().exchange(request, Any::class.java)
        assertEquals(HttpStatus.OK, response.status)
    }

    @Test
    fun `should register a new category with a parent`() {
        val parent = Category("Casa")
        categoryRepository.save(parent)

        val newCategoryRequest = NewCategoryRequest(name = CATEGORY_NAME, parentId = parent.id)

        val request = HttpRequest.POST("/api/v1/categories", newCategoryRequest)
        val response = client.toBlocking().exchange(request, Any::class.java)
        assertEquals(HttpStatus.OK, response.status)
    }

    @Test
    fun `should not register a new category if parent not exists`() {
        val newCategoryRequest = NewCategoryRequest(name = CATEGORY_NAME, parentId = 8L)

        val request = HttpRequest.POST("/api/v1/categories", newCategoryRequest)
        val response = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, Any::class.java)
        }
        assertEquals(HttpStatus.NOT_FOUND, response.status)
    }

}