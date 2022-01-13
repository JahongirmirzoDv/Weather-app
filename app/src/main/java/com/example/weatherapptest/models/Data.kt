package com.example.weatherapp.models

class Data {
    var base: String? = null
    var clouds: Clouds? = null
    var cod: Int? = null
    var coord: Coord? = null
    var dt: Int? = null
    var id: Int? = null
    var main: Main? = null
    var name: String? = null
    var rain: Rain? = null
    var sys: Sys? = null
    var timezone: Int? = null
    var visibility: Int? = null
    var weather: List<Weather>? = null
    var wind: Wind? = null

    constructor(
        base: String?,
        clouds: Clouds?,
        cod: Int?,
        coord: Coord?,
        dt: Int?,
        id: Int?,
        main: Main?,
        name: String?,
        rain: Rain?,
        sys: Sys?,
        timezone: Int?,
        visibility: Int?,
        weather: List<Weather>?,
        wind: Wind?
    ) {
        this.base = base
        this.clouds = clouds
        this.cod = cod
        this.coord = coord
        this.dt = dt
        this.id = id
        this.main = main
        this.name = name
        this.rain = rain
        this.sys = sys
        this.timezone = timezone
        this.visibility = visibility
        this.weather = weather
        this.wind = wind
    }

    constructor()
}