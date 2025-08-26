package com.ganaljigi.kubf.mapper

import com.ganaljigi.kubf.data.remote.response.route.PathResponseDto
import com.ganaljigi.kubf.data.remote.response.route.RoutePathDto
import com.ganaljigi.kubf.ui.common.model.RouteMode
import com.ganaljigi.kubf.ui.home.model.RouteResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.double

fun PathResponseDto.toRouteResults(): List<RouteResult> {
    return listOfNotNull(
        shortestPath?.toRouteResult(RouteMode.SHORTEST),
        noStairsPath?.toRouteResult(RouteMode.NO_STAIRS),
        barrierFreePath?.toRouteResult(RouteMode.BARRIER_FREE)
    )
}

fun RoutePathDto.toRouteResult(routeMode: RouteMode): RouteResult {
    // LineString features만 필터링하고 index 순서대로 정렬
    val lineStringFeatures = features
        .filter { it.geometry?.type == "LineString" }
        .sortedBy { it.properties?.index ?: 0 }
    
    val pathPoints = mutableListOf<LatLng>()
    
    lineStringFeatures.forEach { feature ->
        feature.geometry?.let { geometry ->
            if (geometry.type == "LineString" && geometry.coordinates is JsonArray) {
                val coordinatesArray = geometry.coordinates as JsonArray
                coordinatesArray.forEach { coordPairElement ->
                    if (coordPairElement is JsonArray && coordPairElement.size >= 2) {
                        val lng = (coordPairElement[0] as JsonPrimitive).double
                        val lat = (coordPairElement[1] as JsonPrimitive).double
                        val latLng = LatLng(lat, lng) // [longitude, latitude] -> LatLng(latitude, longitude)
                        // 중복 점 제거 (연속된 LineString의 연결점에서 발생할 수 있음)
                        if (pathPoints.isEmpty() || pathPoints.last() != latLng) {
                            pathPoints.add(latLng)
                        }
                    }
                }
            }
        }
    }
    
    android.util.Log.d("RouteMapper", "RouteMode: $routeMode, PathPoints count: ${pathPoints.size}, Features count: ${features.size}")
    
    val distanceValue = totalDistance.replace("m", "").toIntOrNull() ?: 0
    val estimatedTime = (distanceValue / 70).coerceAtLeast(1) // 평균 도보 속도 70m/분 가정
    
    return RouteResult(
        time = estimatedTime,
        distance = distanceValue,
        routeMode = routeMode,
        pathPoints = pathPoints.toImmutableList(),
        distanceText = totalDistance
    )
}