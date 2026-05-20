import React from 'react';
import { Code2 } from 'lucide-react';
import styles from '../app/page.module.css';

const Navbar = () => {
  return (
    <header className={styles.header}>
      <div className={styles.brand}>
        <Code2 size={24} color="var(--accent-primary)" />
        Revise AI IDE
      </div>
    </header>
  );
};

export default Navbar;
