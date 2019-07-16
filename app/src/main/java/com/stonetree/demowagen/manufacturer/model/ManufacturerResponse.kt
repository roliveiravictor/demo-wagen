package com.stonetree.demowagen.manufacturer.model

data class ManufacturerResponse(var page: Int = 0,
                                var pageSize: Int = 0,
                                var totalPageCount: Int = 0,
                                var wkda: Map<String, String>)

