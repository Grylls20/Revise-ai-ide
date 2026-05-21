'use client';

import React, { useState } from 'react';
import { Terminal } from 'lucide-react';
import { useLocalStorage } from '../hooks/useLocalStorage';
import styles from './page.module.css';

import Navbar from '../components/Navbar';
import LanguagesSidebar, { LANGUAGES, DEFAULT_CODE } from '../components/LanguagesSidebar';
import CodeEditor from '../components/CodeEditor';
import ReviewOutput from '../components/ReviewOutput';

export default function Home() {
  const [language, setLanguage] = useLocalStorage('revise-ai-lang', LANGUAGES[0]);
  const [codeMap, setCodeMap] = useLocalStorage<Record<string, string>>('revise-ai-code', DEFAULT_CODE);
  
  const [executionResult, setExecutionResult] = useState<{output: string, status: string} | null>(null);
  const [review, setReview] = useState<any>(null);
  
  const [running, setRunning] = useState(false);
  const [loading, setLoading] = useState(false);

  const code = codeMap[language.name] ?? DEFAULT_CODE[language.name] ?? '';

  const setCode = (value: string | undefined) => {
    if (value !== undefined) {
      setCodeMap(prev => ({ ...prev, [language.name]: value }));
    }
  };

  const handleRun = async () => {
    setRunning(true);
    try {
      const response = await fetch(`/api/v1/execute`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          sourceCode: code,
          languageId: language.id,
          stdin: ''
        })
      });
      const data = await response.json();
      setExecutionResult({
        output: data.stdout || data.stderr || data.compileOutput || data.message || 'Execution finished with no output.',
        status: data.status || 'Success'
      });
    } catch (error) {
      console.error(error);
      setExecutionResult({
        output: 'Failed to connect to execution server.',
        status: 'Error'
      });
    } finally {
      setRunning(false);
    }
  };

  const handleReview = async () => {
    setLoading(true);
    setReview(null);
    try {
      const response = await fetch(`/api/v1/review`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          sourceCode: code,
          language: language.name
        })
      });
      const data = await response.json();
      setReview(data);
    } catch (error) {
      console.error(error);
      setReview({ reviewFeedback: 'Failed to connect to AI server.' });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.container}>
      <Navbar />

      <main className={styles.mainArea}>
        <LanguagesSidebar language={language} setLanguage={setLanguage} />

        <div className={styles.centerPane}>
          <CodeEditor 
            code={code}
            setCode={setCode}
            onReview={handleReview}
            onRun={handleRun}
            loading={loading}
            running={running}
            language={language.name}
          />

          {executionResult && (
            <div className={styles.terminalPane}>
              <div className={styles.terminalHeader}>
                <div className={styles.terminalTitle}>
                  <Terminal size={14} /> Terminal Output
                  <span className={`${styles.terminalStatus} ${executionResult.status !== 'Success' ? styles.error : ''}`}>
                    {executionResult.status}
                  </span>
                </div>
                <div className={styles.terminalActions}>
                  <button className={styles.terminalClearBtn} onClick={() => setExecutionResult(null)}>
                    Clear
                  </button>
                  <button className={styles.terminalCloseBtn} onClick={() => setExecutionResult(null)}>
                    ✕
                  </button>
                </div>
              </div>
              <div className={styles.terminalOutput}>
                {executionResult.output}
              </div>
            </div>
          )}
        </div>

        <ReviewOutput review={review} loading={loading} />
      </main>
    </div>
  );
}
