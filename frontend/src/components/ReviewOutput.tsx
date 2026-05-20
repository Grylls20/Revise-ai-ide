import React from 'react';
import { Sparkles, Code2 } from 'lucide-react';
import styles from '../app/page.module.css';

interface ReviewOutputProps {
  review: any;
  loading: boolean;
}

const ReviewOutput: React.FC<ReviewOutputProps> = ({ review, loading }) => {
  return (
    <div className={styles.rightPane}>
      <div className={styles.rightPaneHeader}>
        <Sparkles size={18} color="var(--accent-secondary)" />
        AI Review Output
      </div>
      <div className={styles.rightPaneContent}>
        {!review && !loading && (
          <div className={styles.emptyState}>
            <Sparkles size={32} opacity={0.5} />
            <p>Click "AI Code Review" to instantly analyze your logic and catch bugs before execution.</p>
          </div>
        )}
        
        {loading && (
          <div className={styles.emptyState}>
            <p>Thinking...</p>
          </div>
        )}
        
        {review && !loading && (
          <>
            <div className={styles.aiReviewCard}>
              <div className={styles.aiReviewTitle}>
                <Sparkles size={16} /> Analysis Feedback
              </div>
              <div className={styles.aiReviewText}>{review.reviewFeedback}</div>
            </div>
            {review.suggestedRefactoring && (
              <div className={styles.aiReviewCard}>
                <div className={styles.aiReviewTitle}>
                  <Code2 size={16} /> Suggestions
                </div>
                <div className={styles.aiReviewText}>{review.suggestedRefactoring}</div>
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default ReviewOutput;
