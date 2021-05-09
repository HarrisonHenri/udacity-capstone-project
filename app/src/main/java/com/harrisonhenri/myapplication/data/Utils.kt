package com.harrisonhenri.myapplication.data

import com.harrisonhenri.myapplication.data.entities.CompanyEntity
import com.harrisonhenri.myapplication.repository.models.Company

fun List<CompanyEntity>.toDomainModel():List<Company> {
    return map { companyEntity ->
        Company(
            id = companyEntity.id,
            name = companyEntity.name,
            address = companyEntity.address,
            imageUrl = companyEntity.imageUrl,
            menuId = companyEntity.menuId
        )
    }
}

fun ArrayList<Company>.toDatabaseModel():Array<CompanyEntity> {
    return map { company ->
        CompanyEntity(
                id = company.id,
                name = company.name,
                address = company.address,
                imageUrl = company.imageUrl,
                menuId = company.menuId
        )
    }.toTypedArray()
}