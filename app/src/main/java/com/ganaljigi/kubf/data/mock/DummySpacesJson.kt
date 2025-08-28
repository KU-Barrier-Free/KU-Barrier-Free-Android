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
        "imageUrl": ["image.png", "image2.png"],
        "latitude": 32.54321,
        "longitude": 137.54321,
        "label": "A"
      },
      {
        "id": 2,
        "wheelchair": true,
        "imageUrl": ["door1.png"],
        "latitude": 32.54325,
        "longitude": 137.54350,
        "label": "B"
      },
      {
        "id": 3,
        "wheelchair": true,
        "imageUrl": ["door2.png", "door3.png"],
        "latitude": 32.54330,
        "longitude": 137.54370,
        "label": "C"
      }
    ],
    "facilityPurposes": ["은행", "휴게실", "카페", "ATM", "편의점"],
    "significantInfos": [
      {
        "id": 1,
        "description": "2층 구름다리 통로로 진입 가능",
        "imageUrl": ["bridge1.png", "bridge2.png"]
      },
      {
        "id": 2,
        "description": "장애인 전용 화장실 있음",
        "imageUrl": ["toilet.png"]
      }
    ],
    "floorList": [
      {
        "drawings": ["b1_map.png"],
        "purposes": ["주차장", "창고"],
        "spaceSummaries": [
          {
            "id": 1,
            "roomNumber": "B101",
            "roomName": "주차장",
            "comment": "장애인 전용 주차구역 있음",
            "roomImages": [
              { "imageUrl": "https://example.com/parking1.png", "imageType": "ROOM" }
            ],
            "isLecture": false
          },
          {
            "id": 2,
            "roomNumber": "B102",
            "roomName": "기계실",
            "comment": "출입 제한 구역",
            "roomImages": [
              { "imageUrl": "https://example.com/machine1.png", "imageType": "ROOM" }
            ],
            "isLecture": false
          }
        ],
        "floor": "B1"
      },
      {
        "drawings": ["1f_map.png"],
        "purposes": ["은행", "카페"],
        "spaceSummaries": [
          {
            "id": 3,
            "roomNumber": "101",
            "roomName": "신한은행",
            "comment": "휠체어 진입 가능",
            "roomImages": [
              { "imageUrl": "https://example.com/bank.png", "imageType": "ROOM" }
            ],
            "isLecture": false
          },
          {
            "id": 4,
            "roomNumber": "102",
            "roomName": "카페 레스티오",
            "comment": "테라스 있음",
            "roomImages": [
              { "imageUrl": "https://example.com/cafe.png", "imageType": "ROOM" }
            ],
            "isLecture": false
          }
        ],
        "floor": "1"
      },
      {
        "drawings": ["2f_map.png"],
        "purposes": ["강의실", "휴게실"],
        "spaceSummaries": [
          {
            "id": 5,
            "roomNumber": "201",
            "roomName": "대강의실",
            "comment": "턱 있음",
            "roomImages": [
              { "imageUrl": "https://example.com/room1.png", "imageType": "ROOM" },
              { "imageUrl": "https://example.com/door1.png", "imageType": "DOOR" }
            ],
            "isLecture": true
          },
          {
            "id": 6,
            "roomNumber": "202",
            "roomName": "세미나실",
            "comment": "빔프로젝터 설치",
            "roomImages": [
              { "imageUrl": "https://example.com/seminar.png", "imageType": "ROOM" }
            ],
            "isLecture": true
          },
          {
            "id": 7,
            "roomNumber": "203",
            "roomName": "휴게실",
            "comment": "자판기 있음",
            "roomImages": [
              { "imageUrl": "https://example.com/lounge.png", "imageType": "ROOM" }
            ],
            "isLecture": false
          }
        ],
        "floor": "2"
      },
      {
        "drawings": ["3f_map.png"],
        "purposes": ["강의실"],
        "spaceSummaries": [
          {
            "id": 8,
            "roomNumber": "301",
            "roomName": "중강의실",
            "comment": "화이트보드 있음",
            "roomImages": [
              { "imageUrl": "https://example.com/room301.png", "imageType": "ROOM" }
            ],
            "isLecture": true
          },
          {
            "id": 9,
            "roomNumber": "302",
            "roomName": "전산실습실",
            "comment": "PC 설치",
            "roomImages": [
              { "imageUrl": "https://example.com/lab302.png", "imageType": "ROOM" }
            ],
            "isLecture": true
          }
        ],
        "floor": "3"
      }
    ],
    "latitude": 37.54321,
    "longitude": 127.11111
  }
}
"""
}