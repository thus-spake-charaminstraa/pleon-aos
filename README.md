<p align="center">
<img src="https://user-images.githubusercontent.com/62279741/230766858-ddcd55a2-b7c7-4a93-9952-94a075f707a5.png" width="750px">
</p>

# 🪴 PLeon(플레옹) : AI와 함께하는 반려식물 다이어리
PLeon(플레옹)은 반려식물을 키우는 1인가구를 대상으로 정서적 안정을 제공하는 반려식물 관리/소통 서비스 입니다.   
AI 모델을 활용한 식물 종 식별 기능, 식물 진단 기능, 식물 챗봇 기능을 제공하여 경쟁력을 강화하고 있습니다.

🔗 **play store link** https://play.google.com/store/apps/details?id=com.charaminstra.pleon   
🔗 **article** https://www.etnews.com/20230220000232

## 🕹 Features
<p align="center">
<img src="https://user-images.githubusercontent.com/62279741/230766999-2dcc23a0-35e0-48df-97ad-e57b0595680a.png" alt="drawing" width="250px" />
<img src="https://user-images.githubusercontent.com/62279741/230767045-d09fc825-3b55-4e2f-869e-9dfa53ab9006.png" alt="drawing" width="250px" />
<img src="https://user-images.githubusercontent.com/62279741/230767108-fd208973-5a84-4c2c-806d-a1e9967fe307.png" alt="drawing" width="250px" />
<img src="https://user-images.githubusercontent.com/62279741/230767057-d499dd15-8690-46d1-87d1-63dbdea3baef.png" alt="drawing" width="250px" />
<img src="https://user-images.githubusercontent.com/62279741/230767106-1ab2b438-7e73-492f-9671-3a3b18db3af1.png" alt="drawing" width="250px" />
<img src="https://user-images.githubusercontent.com/62279741/230767113-1841fce7-ca53-46d8-ab9e-0bc552cb1e82.png" alt="drawing" width="250px" /></br>
</p>

- **feed** 식물과 함께한 추억을 피드에 기록 및 관리 가이드 알람
- **garden** 식물 관리 상태에 따른 식물의 기분 확인 및 관리 캘린더
- **doctor** 사진을 찍으면 식물을 진단(갈변, 반점, 말린 잎 등) 해 주고, 피드에 기록
- **plant ID** 식물을 등록할 때 자동으로 종을 식별해주고, 그 종에 맞는 맞춤가이드 제공
- **AI 반려식물** 피드를 작성하면 AI 반려식물이 댓글을 달아주어 소통하는 서비스


## 🏛 Architecture
### Modularization
<p align="center">
<img src="https://user-images.githubusercontent.com/62279741/230767554-c74a1c56-4bd6-4930-9d2c-414237adef90.png" width="550px">
</p>


### Server Driven UI
<p align="center">
<img src="https://user-images.githubusercontent.com/62279741/230767589-177ac063-d01d-4011-9f4c-306e16fe5ca5.png" width="550px">
</p>

### Tech Stack & Library
- MVVM, Modularization, hilt, coroutines
- cameraX, databinding, livedata, navigation
- retrofit2, glide, lottie
- FCM, Google Analytics, Firebase Crashyltics
- server driven UI, custom UI


