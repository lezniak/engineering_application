package com.example.apiapp.data.objects.Dao

import com.google.gson.annotations.SerializedName

data class OrganizationItem(
    val eventId: Int,
    val id: Int,
    val name: String,
    @SerializedName("organizationMemberInformationDtoList")
    val userOrganizationList: List<UserOrganization>?,
)