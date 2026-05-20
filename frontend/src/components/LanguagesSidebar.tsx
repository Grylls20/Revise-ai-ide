import React from 'react';
import { Cpu } from 'lucide-react';
import styles from '../app/page.module.css';

export const LANGUAGES = [
  { id: 71, name: 'python', label: 'Python (3.12)' },
  { id: 62, name: 'java', label: 'Java (Mock)' },
  { id: 54, name: 'cpp', label: 'C++ (GCC 12)' },
  { id: 63, name: 'javascript', label: 'Node.js (20)' },
  { id: 60, name: 'go', label: 'Go (1.23)' },
  { id: 73, name: 'rust', label: 'Rust (1.82)' },
  { id: 72, name: 'ruby', label: 'Ruby (3.4)' }
];

export const DEFAULT_CODE: Record<string, string> = {
  python: 'print("Hello, Revise AI!")\n',
  java: 'public class Main {\n    public static void main(String[] args) {\n        System.out.println("Hello, Revise AI!");\n    }\n}\n',
  cpp: '#include <iostream>\n\nint main() {\n    std::cout << "Hello, Revise AI!" << std::endl;\n    return 0;\n}\n',
  javascript: 'console.log("Hello, Revise AI!");\n',
  go: 'package main\n\nimport "fmt"\n\nfunc main() {\n    fmt.Println("Hello, Revise AI!")\n}\n',
  rust: 'fn main() {\n    println!("Hello, Revise AI!");\n}\n',
  ruby: 'puts "Hello, Revise AI!"\n'
};

interface LanguagesSidebarProps {
  language: any;
  setLanguage: (lang: any) => void;
}

const LanguagesSidebar: React.FC<LanguagesSidebarProps> = ({ language, setLanguage }) => {
  return (
    <div className={styles.sidebarPane}>
      <div style={{ padding: '0.5rem', fontWeight: 600, color: 'var(--text-secondary)', marginBottom: '0.5rem' }}>
        Languages
      </div>
      {LANGUAGES.map(lang => (
        <div 
          key={lang.name}
          className={`${styles.langItem} ${language.name === lang.name ? styles.active : ''}`}
          onClick={() => setLanguage(lang)}
        >
          <Cpu size={16} />
          {lang.label}
        </div>
      ))}
    </div>
  );
};

export default LanguagesSidebar;
