package co.com.ceiba.mobile.pruebadeingreso.rest

data class Users(
    var id: Int,
    var name: String,
    var username: String,
    var email: String,
    var address: AddressUsers,
    var phone: String,
    var website: String,
    var company: CompanyUsers
)
