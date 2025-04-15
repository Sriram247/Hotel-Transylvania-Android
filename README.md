
> **Note:** The backend code is in the **'backend'** branch
  

# 📱 Hotel Reservation Android App (frontend)

  

This is the **Android frontend** of the Hotel Reservation System, built using **Jetpack Compose**, **Kotlin**, **Retrofit**. It connects to a **Spring Boot + GraphQL** backend and supports features like hotel browsing, guest detail entry, and AI-generated hotel reviews.

  

---

  

### 🚀 Features

  

- 🔍 **Hotel Search**: Enter location, select dates, guests, and find available hotels.

- 📄 **Guest Form**: Enter individual details per guest for each reservation.

- 🧠 **AI-Powered Reviews**: View AI-generated summaries and guest reviews.

- 🛏️ **Rooms & Guests Selection**: Modal bottom sheets for dynamic guest entry.

- 🖼️ **Image Loading**: Uses Coil for asynchronous image loading.

- 🎨 **Animations**: Lottie animations for a better user experience.

- 🧭 **Jetpack Compose**: Modern UI toolkit with clean, reactive UI.

  

---

  

### 🧰 Tech Stack

  

| Layer | Library / Tool |

|--------------|--------------------------|

| UI | Jetpack Compose |

| Language | Kotlin |

| State | ViewModel + State |

| Networking | Retrofit + GraphQL |

| Image Loading| Coil |

| Architecture | MVVM |

  

---

  

### 🔧 Setup Instructions

  

1.  **Clone the project**

  

\`\`\`bash

git clone https://github.com/yourusername/hotel-reservation-android.git

cd hotel-reservation-android

\`\`\`

  

2.  **Open in Android Studio**

- File → Open → Select this project folder.

  

3.  **Set up dependencies**

- Gradle will auto-sync required libraries.

- Make sure you have Kotlin & Compose plugins installed.

  

4.  **Set the base URL**

- In \`ApiClient.kt\` or relevant Retrofit file, point the base URL to your backend GraphQL server (hosted on Azure App Service).

  
  

---

  

### 📦 Key Packages

  

\`\`\`kotlin

com.example.hoteltransylvania.ui // Jetpack Compose UI screens

com.example.hoteltransylvania.viewmodel // ViewModels (e.g., ReviewsViewModel)

com.example.hoteltransylvania.data // Data models (Hotel, GuestInfo, etc.)

com.example.hoteltransylvania.network // Retrofit & GraphQL queries

\`\`\`

  

---

  

### 🧪 Notable Screens

  

-  \`HomeScreen.kt\`: Location search, date picker, guest selector.

-  \`HotelListScreen.kt\`: List of hotels based on search results.

-  \`HotelFormScreen.kt\`: Enter guest info, view AI reviews via dialog.

-  \`ReviewsViewModel.kt\`: Handles API calls for reviews & summaries.

  

---

  

### 📸 UI Preview

  

| Screen | Description |

|----------------|----------------------------|

| Home Screen | Search hotels by location |

| Hotel Form | Enter guest info per hotel |

| AI Reviews | Summarized guest reviews |

  

---

  

### 🧠 AI Reviews (GraphQL)

  

- Fetched using a \`ReviewsViewModel\` with a GraphQL query like:

  

\`\`\`graphql

query {

reviews(hotelName: "Hotel Dracula") {

texts

ai_summary

}

}

\`\`\`

  

---

  

### ✅ TODOs

  

- [ ] Add local RoomDB caching for offline search

- [ ] Improve error handling and validation

- [ ] Add user authentication & bookings

- [ ] UI enhancements and animations

- [ ] Integrate payment system

  

---

  

### 📄 License

  

MIT License. See \`LICENSE\` file for more info.
