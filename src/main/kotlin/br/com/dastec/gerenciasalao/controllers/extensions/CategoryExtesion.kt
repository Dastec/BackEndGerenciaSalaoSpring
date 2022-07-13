package br.com.dastec.gerenciasalao.controllers.extensions

import br.com.dastec.gerenciasalao.controllers.requests.categories.PostCategoryRequest
import br.com.dastec.gerenciasalao.controllers.requests.categories.PutCategoryRequest
import br.com.dastec.gerenciasalao.controllers.responses.CategoryResponse
import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.BeautySalonModel

fun PostCategoryRequest.toCategoryModel(beautySalonModel: BeautySalonModel): CategoryModel {
    return CategoryModel(
        nameCategory = this.nameCategory,
        beautySalon = beautySalonModel

    )
}

fun PutCategoryRequest.toCategoryModel(categoryModel: CategoryModel): CategoryModel {
    return CategoryModel(
        idCategory = categoryModel.idCategory,
        nameCategory = this.nameCategory ?: categoryModel.nameCategory,
        beautySalon = categoryModel.beautySalon

    )
}

fun CategoryModel.toCategoryResponse(): CategoryResponse{
    return CategoryResponse(
        idCategory = this.idCategory!!,
        nameCategory = this.nameCategory
    )
}

fun List<CategoryModel>.toListCategoryResponse(): List<CategoryResponse>{
    val categoriesResponse = mutableListOf<CategoryResponse>()
    for (category in this){
        categoriesResponse.add(category.toCategoryResponse())
    }
    return categoriesResponse
}