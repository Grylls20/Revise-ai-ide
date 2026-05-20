# Revise AI IDE

Revise AI IDE is a modern, AI-powered code execution and analysis platform. It features a full-stack architecture with a highly modular React (Next.js) frontend and a robust Spring Boot backend. 

The IDE integrates zero-cost cloud execution strategies via Wandbox and real-time AI code reviews using Google's bleeding-edge Gemini Flash API.

## Features

* **Multi-Language Support**: Write and execute Python, JavaScript, Go, Rust, Ruby, and C++ directly in the browser via the robust Wandbox API.
* **Instant AI Code Reviews**: Click a button to get immediate analysis, bug detection, and suggested refactoring from Google Gemini AI.
* **Modern UI Architecture**: A beautiful, 3-column light/dark mode aesthetic leveraging glassmorphism and Monaco Editor, directly inspired by premium industry standards.
* **Zero-Cost Infrastructure**: The backend leverages 100% free external APIs (Wandbox for execution and AI Studio for LLM access) to guarantee zero subscription overhead.
* **Extensible Strategy Patterns**: The Java backend uses Factory, Strategy, and Builder patterns, making it trivial to swap execution engines or AI models in the future.

## Tech Stack

### Frontend (React / Next.js)
* **Framework:** Next.js 14 (App Router)
* **Editor:** Monaco Editor (`@monaco-editor/react`)
* **Styling:** Modular CSS (`page.module.css`) with Vanilla Flexbox/Grid
* **Icons:** Lucide React

### Backend (Java / Spring Boot)
* **Framework:** Spring Boot 3.x (WebFlux)
* **Design Patterns:** Strategy, Factory, Builder
* **AI Integration:** Google Gemini API (`gemini-flash-latest`)
* **Code Execution:** Wandbox Compilation API

## Getting Started

### Prerequisites
- Node.js (v18 or higher)
- Java 17+
- Maven

### Installation & Running

1. **Start the Backend**
   Navigate to the backend directory and run the Spring Boot server:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```
   *The backend runs on `http://localhost:8081`.*

2. **Start the Frontend**
   Open a new terminal, navigate to the frontend directory, install dependencies, and start the Next.js development server:
   ```bash
   cd frontend
   npm install
   npm run dev
   ```
   *The frontend runs on `http://localhost:3000`.*

## Environment Variables

Make sure to populate your `application.properties` in the backend (`backend/src/main/resources/application.properties`):

```properties
server.port=8081
gemini.api.key=YOUR_GEMINI_API_KEY
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the MIT License.
