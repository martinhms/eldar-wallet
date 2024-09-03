package com.org.marton.studio.project.eldarwallet.domain.model

enum class Bank(val desc: String, val code: Int){
    NOT_SELECTED("Select bank", 0),
    SANTANDER("Santander", 1),
    GALICIA("Galicia", 2),
    BBVA("BBVA", 3),
    CIUDAD("Ciudad", 4),
    NACION("Nación", 5),
    MACRO("Macro",6),
    PATAGONIA("Patagonia", 7),
    HSBC("HSBC", 8),
    ICBC("ICBC", 9),
    COMAFI("Comafi", 10),
    SUPERVIELLE("Supervielle", 11)
}
