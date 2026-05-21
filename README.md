# Revise AI IDE

Hey there! Welcome to **Revise AI IDE**, a modern, AI-powered code execution and analysis platform that I built. My goal was to create a full-stack development environment that runs completely in your browser, utilizing a highly modular React (Next.js) frontend and a robust Spring Boot backend. 

The most exciting part of this project is that I successfully integrated zero-cost cloud execution strategies via Wandbox and real-time AI code reviews using Google's bleeding-edge Gemini Flash API!

## Features

* **Multi-Language Support**: You can write and execute Python, JavaScript, Go, Rust, Ruby, and C++ directly in my IDE thanks to the robust Wandbox API integration.
* **Instant AI Code Reviews**: I've added a feature where you can click a single button to get immediate analysis, bug detection, and suggested refactoring directly from Google Gemini AI.
* **Modern UI Architecture**: I designed a beautiful, 3-column light/dark mode aesthetic leveraging glassmorphism and Monaco Editor, directly inspired by premium industry standards.
* **Zero-Cost Infrastructure**: I built the backend to leverage 100% free external APIs (Wandbox for execution and AI Studio for LLM access) to guarantee that this IDE has zero subscription overhead.
* **Extensible Strategy Patterns**: I architected the Java backend using Factory, Strategy, and Builder patterns, making it trivial for me to swap execution engines or AI models in the future.

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
Make sure you have these installed:
- Node.js (v18 or higher)
- Java 17+
- Maven

### Installation & Running

1. **Start the Backend**
   Navigate to the backend directory and run my Spring Boot server:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```
   *The backend will run on `http://localhost:8081`.*

2. **Start the Frontend**
   Open a new terminal, navigate to the frontend directory, install dependencies, and start the Next.js development server:
   ```bash
   cd frontend
   npm install
   npm run dev
   ```
   *The frontend will run on `http://localhost:3000`.*

## Environment Variables

Make sure to populate your `.env` in the backend (`backend/.env`). You can use my `.env.example` as a template:

```properties
GEMINI_API_KEY=your_key
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the MIT License.
