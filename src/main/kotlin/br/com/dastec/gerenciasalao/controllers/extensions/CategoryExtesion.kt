package br.com.dastec.gerenciasalao.controllers.extensions

import br.com.dastec.gerenciasalao.controllers.requests.categories.PostCategoryRequest
import br.com.dastec.gerenciasalao.controllers.requests.categories.PutCategoryRequest
import br.com.dastec.gerenciasalao.models.CategoryModel

fun PostCategoryRequest.toCategoryModel(): CategoryModel{
    return CategoryModel(
        nameCategory = this.nameCategory
    )
}

fun PutCategoryRequest.toCategoryModel(categoryModel: CategoryModel): CategoryModel{
    return CategoryModel(
        idCategory = categoryModel.idCategory,
        nameCategory = this.nameCategory ?: categoryModel.nameCategory
    )
}