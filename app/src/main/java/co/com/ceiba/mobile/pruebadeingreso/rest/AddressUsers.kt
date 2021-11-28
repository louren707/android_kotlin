package co.com.ceiba.mobile.pruebadeingreso.rest

data class AddressUsers(
    var street: String,
    var suite: String,
    var city: String,
    var zipcode: String,
    var geo: GeoAddressUsers,
)
