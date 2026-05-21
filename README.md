# Revise AI IDE

A modern, AI-powered code execution and analysis platform. It provides a full-stack browser-based development environment using a modular React (Next.js) frontend and a robust Spring Boot backend.

Features include real-time AI code reviews powered by Google's Gemini Flash API and zero-cost cloud execution. The stack is deployed with the backend on **AWS EC2** and the frontend on **Netlify**, utilizing a server-side proxy architecture to handle cross-origin communication securely.

🚀 **Live at: [https://revise-ai-ide.netlify.app](https://revise-ai-ide.netlify.app)**

---

## Features

Multi-Language Support: Write and execute Python, JavaScript, Go, Rust, Ruby, and C++ directly in the browser.
Instant AI Code Reviews: One click gets you immediate analysis, bug detection, and refactoring suggestions.
Modern UI Architecture: A 3-column light/dark mode aesthetic with glassmorphism and Monaco Editor.
Zero-Cost Infrastructure: The entire stack uses free-tier services.
Extensible Design Patterns: The Java backend is architected using Factory, Strategy, and Builder patterns.
Production-Ready Deployment: Next.js API routes act as a server-side proxy to the EC2 backend.

---

## Tech Stack

### Frontend
| Technology | Details |
|---|---|
| Framework | Next.js 16 (App Router) |
| Editor | Monaco Editor (`@monaco-editor/react`) |
| Styling | Vanilla CSS Modules with Flexbox/Grid |
| Icons | Lucide React |
| Hosting | Netlify |

### Backend
| Technology | Details |
|---|---|
| Framework | Spring Boot 4.x (Java 17) |
| Design Patterns | Strategy, Factory, Builder |
| AI Integration | Google Gemini API (`gemini-flash-latest`) |
| Code Execution | Wandbox Compilation API |
| Hosting | AWS EC2 (Amazon Linux 2023) |

---

## Architecture

```
Browser
  │
  │  HTTPS (same-origin)
  ▼
Netlify (Next.js frontend + API routes)
  │
  │  Server-side HTTP (no CORS)
  ▼
AWS EC2 — Spring Boot Backend (:8081)
  │
  ├──▶ Google Gemini API  (AI reviews)
  └──▶ Wandbox API        (code execution)
```

---

## Running Locally

### Prerequisites
- Node.js v18+
- Java 17+
- Maven

### 1. Start the Backend

```bash
cd backend
./mvnw spring-boot:run
```

The backend runs on `http://localhost:8081`.

### 2. Start the Frontend

```bash
cd frontend
npm install
npm run dev
```

The frontend runs on `http://localhost:3000`.

---

## Environment Variables

### Backend (`backend/.env`)

```properties
GEMINI_API_KEY=your_gemini_api_key
ALLOWED_ORIGINS=http://localhost:3000,https://your-app.netlify.app
```

Use `backend/.env.example` as a template.

### Frontend (Netlify dashboard or `.env.local`)

```properties
BACKEND_URL=http://localhost:8081
```

---

## Deployment

| Layer | Platform | Notes |
|---|---|---|
| Frontend | Netlify | Auto-deploys from GitHub on push to `main` |
| Backend | AWS EC2 | Runs as a `systemd` service, starts on boot |

The frontend uses Next.js API routes (`/api/v1/execute`, `/api/v1/review`) as a server-side proxy to the EC2 backend — no mixed-content or CORS issues.

---

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## License

Distributed under the MIT License.
