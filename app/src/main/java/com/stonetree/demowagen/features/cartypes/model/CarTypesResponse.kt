package com.stonetree.demowagen.features.cartypes.model

data class CarTypesResponse(var page: Int = 0,
                            var pageSize: Int = 0,
                            var totalPageCount: Int = 0,
                            var wkda: Map<String, String>)