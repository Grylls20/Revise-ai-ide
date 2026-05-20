import React from 'react';
import Editor from '@monaco-editor/react';
import { Play, Sparkles } from 'lucide-react';
import styles from '../app/page.module.css';

interface CodeEditorProps {
  code: string;
  setCode: (val: string | undefined) => void;
  onReview: () => void;
  onRun: () => void;
  loading: boolean;
  running: boolean;
  language: string;
}

const CodeEditor: React.FC<CodeEditorProps> = ({ code, setCode, onReview, onRun, loading, running, language }) => {
  return (
    <div className={styles.editorPane}>
      <div style={{ display: 'flex', justifyContent: 'flex-end', padding: '0.5rem', borderBottom: '1px solid var(--border-color)', gap: '0.5rem' }}>
        <button className="btn btn-secondary" onClick={onReview} disabled={loading}>
          <Sparkles size={16} color="var(--accent-secondary)" />
          {loading ? 'Analyzing...' : 'AI Code Review'}
        </button>
        <button className="btn btn-primary" onClick={onRun} disabled={running}>
          <Play size={16} />
          {running ? 'Running...' : 'Run Code'}
        </button>
      </div>
      <div className={styles.editorWrapper}>
        <Editor
          height="100%"
          language={language === 'cpp' ? 'cpp' : language}
          theme="vs-dark"
          value={code}
          onChange={setCode}
          options={{
            minimap: { enabled: false },
            fontSize: 14,
            fontFamily: "'JetBrains Mono', monospace",
            lineHeight: 24,
            padding: { top: 16 },
            scrollBeyondLastLine: false,
            smoothScrolling: true,
            cursorBlinking: "smooth",
            cursorSmoothCaretAnimation: "on"
          }}
        />
      </div>
      {running && (
        <div className={styles.loadingOverlay}>Running Code...</div>
      )}
    </div>
  );
};

export default CodeEditor;
