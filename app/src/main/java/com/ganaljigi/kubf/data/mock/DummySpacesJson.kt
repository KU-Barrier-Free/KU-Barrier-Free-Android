package com.ganaljigi.kubf.data.mock

object DummySpacesJson{
    const val JSON = """{
  "success": true,
  "code": 200,
  "message": "요청에 성공하였습니다.",
  "result": {
    "id": 1,
    "number": 2,
    "name": "경영관",
    "department": "경영(전문)대학원, 경영대학",
    "image": "image.png",
    "lecture": true,
    "doorInfos": [
      {
        "id": 1,
        "wheelchair": false,
        "imageUrl": [
          "image.png",
          "image2.png"
        ],
        "latitude": 32.54321,
        "longitude": 137.54321,
        "label": "D"
      }
    ],
    "facilityPurposes": [
      "은행",
      "휴게실",
      "카페"
    ],
    "significantInfos": [
      {
        "id": 1,
        "description": "2층 구름다리 통로로 진입 가능",
        "imageUrl": [
          "image.png",
          "image2.png"
        ]
      }
    ],
    "floorList": [
      {
        "drawings": [
          "image.png",
          "image2.png"
        ],
        "purposes": [
          "은행",
          "휴게실",
          "카페"
        ],
        "spaceSummaries": [
          {
            "id": 1,
            "roomNumber": "201호",
            "roomName": "대강의실",
            "comment": "턱 있음",
            "roomImages": [
              {
                "imageUrl": "https://example.com/room1.png",
                "imageType": "ROOM"
              },
              {
                "imageUrl": "https://example.com/room2.png",
                "imageType": "DOOR"
              }
            ],
            "isLecture": true
          }
        ],
        "floor": "B1"
      }
    ],
    "latitude": 37.54321,
    "longitude": 127.11111
  }
}"""
}