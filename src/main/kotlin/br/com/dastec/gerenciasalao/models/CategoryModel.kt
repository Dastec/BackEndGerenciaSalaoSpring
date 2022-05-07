package br.com.dastec.gerenciasalao.models

import javax.persistence.*

@Entity
@Table(name = "categories")
data class CategoryModel (
    @Column(name = "id_category")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idCategory: Long? = null,

    @Column(name = "name_category", nullable = false, unique = true)
    val nameCategory: String
)
