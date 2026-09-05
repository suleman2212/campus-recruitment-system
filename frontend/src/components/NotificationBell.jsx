import { useEffect, useRef, useState } from 'react';
import { fetchJobNotifications } from '../api/jobNotifications';
import { useAuth } from '../context/AuthContext';

const POLL_MS = 20000;
const TOAST_MS = 8000;

function loadIdSet(key) {
  try {
    const raw = localStorage.getItem(key);
    return new Set(raw ? JSON.parse(raw) : []);
  } catch {
    return new Set();
  }
}

export default function NotificationBell() {
  const { username } = useAuth();
  const [notifications, setNotifications] = useState([]);
  const [open, setOpen] = useState(false);
  const [toasts, setToasts] = useState([]);
  const [error, setError] = useState('');

  const seenKey = `crs_notif_seen_${username || 'anon'}`;
  const ackKey = `crs_notif_ack_${username || 'anon'}`;
  const seenRef = useRef(new Set());
  const ackRef = useRef(new Set());
  const primedRef = useRef(false);

  // Reset per-user tracking whenever the signed-in user changes.
  useEffect(() => {
    seenRef.current = loadIdSet(seenKey);
    ackRef.current = loadIdSet(ackKey);
    primedRef.current = false;
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [username]);

  useEffect(() => {
    let cancelled = false;

    async function poll() {
      try {
        const data = await fetchJobNotifications();
        if (cancelled) return;
        const list = (Array.isArray(data) ? data : [])
          .slice()
          .sort((a, b) => (b.notification_id || 0) - (a.notification_id || 0));
        setNotifications(list);
        setError('');

        if (!primedRef.current) {
          // First load for this session: remember what already exists so we
          // don't pop a toast for every historical notification at once.
          list.forEach((n) => seenRef.current.add(n.notification_id));
          localStorage.setItem(seenKey, JSON.stringify([...seenRef.current]));
          primedRef.current = true;
          return;
        }

        const freshOnes = list.filter((n) => !seenRef.current.has(n.notification_id));
        if (freshOnes.length > 0) {
          freshOnes.forEach((n) => seenRef.current.add(n.notification_id));
          localStorage.setItem(seenKey, JSON.stringify([...seenRef.current]));
          setToasts((prev) => [
            ...prev,
            ...freshOnes.map((n) => ({ ...n, _toastId: `${n.notification_id}-${Date.now()}` })),
          ]);
        }
      } catch (err) {
        if (!cancelled) setError(err.message || 'Could not load notifications.');
      }
    }

    poll();
    const interval = setInterval(poll, POLL_MS);
    return () => {
      cancelled = true;
      clearInterval(interval);
    };
  }, [username, seenKey]);

  useEffect(() => {
    if (toasts.length === 0) return undefined;
    const timers = toasts.map((t) =>
      setTimeout(() => {
        setToasts((prev) => prev.filter((x) => x._toastId !== t._toastId));
      }, TOAST_MS)
    );
    return () => timers.forEach(clearTimeout);
  }, [toasts]);

  const unreadCount = notifications.filter((n) => !ackRef.current.has(n.notification_id)).length;

  const toggleOpen = () => {
    const next = !open;
    setOpen(next);
    if (next && notifications.length > 0) {
      notifications.forEach((n) => ackRef.current.add(n.notification_id));
      localStorage.setItem(ackKey, JSON.stringify([...ackRef.current]));
    }
  };

  const dismissToast = (id) => setToasts((prev) => prev.filter((t) => t._toastId !== id));

  return (
    <>
      <div className="notif-bell-wrap">
        <button className="notif-bell" onClick={toggleOpen} aria-label="Notifications" type="button">
          🔔
          {unreadCount > 0 && <span className="notif-badge">{unreadCount > 9 ? '9+' : unreadCount}</span>}
        </button>
        {open && (
          <div className="notif-dropdown">
            <div className="notif-dropdown-title">Job notifications</div>
            {error && (
              <div className="alert alert-error" style={{ margin: 10 }}>
                {error}
              </div>
            )}
            {notifications.length === 0 && !error && <div className="empty-state">No notifications yet.</div>}
            <div className="notif-list">
              {notifications.slice(0, 15).map((n) => (
                <div className="notif-item" key={n.notification_id}>
                  <div className="notif-item-title">{n.title}</div>
                  {n.discription && <div className="notif-item-body">{n.discription}</div>}
                  <div className="notif-item-date">{n.publish_date}</div>
                </div>
              ))}
            </div>
          </div>
        )}
      </div>

      <div className="toast-stack">
        {toasts.map((t) => (
          <div className="toast-card" key={t._toastId}>
            <button className="toast-close" onClick={() => dismissToast(t._toastId)} aria-label="Dismiss" type="button">
              ×
            </button>
            <div className="toast-title">📣 {t.title}</div>
            {t.discription && <div className="toast-body">{t.discription}</div>}
          </div>
        ))}
      </div>
    </>
  );
}
