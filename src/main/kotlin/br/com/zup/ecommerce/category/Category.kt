package br.com.zup.ecommerce.category

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Category(
    val name: String,
    @ManyToOne var parent: Category? = null
) {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    val createdAt: LocalDateTime = LocalDateTime.now()
}
