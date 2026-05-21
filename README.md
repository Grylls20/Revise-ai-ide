# Revise AI IDE

A modern, AI-powered code execution and analysis platform. It provides a full-stack browser-based development environment using a modular React (Next.js) frontend and a robust Spring Boot backend.

Live at: https://revise-ai-ide.netlify.app


## Features

Multi-Language Support
Write and execute Python, JavaScript, Go, Rust, Ruby, and C++ directly in the browser via the Wandbox compilation API.

Instant AI Code Reviews
One click gets you immediate analysis, bug detection, and refactoring suggestions powered by Google Gemini AI.

Modern UI Architecture
A 3-column light/dark mode aesthetic with glassmorphism and Monaco Editor, inspired by premium industry standards.

Zero-Cost Infrastructure
The entire stack uses free-tier services: Wandbox for execution, Gemini AI Studio for LLM access, AWS EC2 (free tier) for the backend, and Netlify for the frontend.

Extensible Design Patterns
The Java backend is architected using Factory, Strategy, and Builder patterns, making it easy to swap execution engines or AI models in the future.

Production-Ready Deployment
Next.js API routes act as a server-side proxy to the EC2 backend, eliminating CORS and mixed-content issues entirely.


## Tech Stack

Frontend
Next.js 16 (App Router), Monaco Editor, Vanilla CSS Modules, Netlify

Backend
Spring Boot 4.x (Java 17), Strategy/Factory patterns, Google Gemini API, Wandbox Compilation API, AWS EC2


## Architecture

The browser communicates with Netlify (Next.js frontend) via HTTPS. 
Next.js API routes act as a server-side proxy sending HTTP requests to the AWS EC2 Spring Boot Backend, which then routes to Google Gemini API (for AI reviews) and Wandbox API (for code execution).


## Running Locally

Prerequisites
Node.js v18+, Java 17+, Maven.

Start the Backend
Navigate to the backend directory and run: ./mvnw spring-boot:run
Runs on http://localhost:8081

Start the Frontend
Navigate to the frontend directory and run: npm install && npm run dev
Runs on http://localhost:3000


## Environment Variables

Backend (backend/.env)
GEMINI_API_KEY=your_gemini_api_key
ALLOWED_ORIGINS=http://localhost:3000,https://your-app.netlify.app

Frontend (.env.local or Netlify)
BACKEND_URL=http://localhost:8081


## Deployment

Frontend
Netlify (Auto-deploys from GitHub on push to main)

Backend
AWS EC2 (Runs as a systemd service, starts on boot)


## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request
