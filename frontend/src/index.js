import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { Toaster } from 'react-hot-toast';
import App from './App.jsx';
import { AuthProvider } from './context/AuthContext.jsx';
import './index.css';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
      <AuthProvider>
        <App />
        <Toaster
          position="top-right"
          toastOptions={{
            duration: 3200,
            style: {
              background: 'var(--ink)',
              color: '#eef1f6',
              border: '1px solid var(--ink-soft)',
              borderRadius: 'var(--radius)',
              fontFamily: 'var(--font-body)',
              fontSize: '0.88rem',
              padding: '10px 14px',
            },
            success: { iconTheme: { primary: 'var(--brass)', secondary: 'var(--ink)' } },
            error: { iconTheme: { primary: '#e28b7a', secondary: 'var(--ink)' } },
          }}
        />
      </AuthProvider>
    </BrowserRouter>
  </React.StrictMode>
);
