package br.com.zup.ecommerce.category

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

@Introspected
data class NewCategoryRequest(
    @field:NotBlank val name: String,
    @field:Positive var parentId: Long? = null
) {
    fun toModel(parent: Category?): Category {
        return Category(name, parent)
    }
}
