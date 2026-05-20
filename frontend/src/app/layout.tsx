import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "Revise AI IDE",
  description: "A premium Full-Stack AI IDE powered by Next.js, Judge0, and Grok AI.",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  );
}
