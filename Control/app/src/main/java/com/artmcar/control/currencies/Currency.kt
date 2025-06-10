package com.artmcar.control.currencies

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ValCurs", strict = false)
data class ValCurs(
    @field:ElementList(entry = "Valute", inline = true)
    var valuteList: List<Valute>? = null
)

@Root(name = "Valute", strict = false)
data class Valute(
    @field:Element(name = "NumCode")
    var numCode: Int = 0,

    @field:Element(name = "CharCode")
    var charCode: String = "",

    @field:Element(name = "Nominal")
    var nominal: Int = 0,

    @field:Element(name = "Name")
    var name: String = "",

    @field:Element(name = "Value")
    var value: String = "",

    @field:Element(name = "VunitRate")
    var vunitRate: String = ""
)