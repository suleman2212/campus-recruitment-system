import { motion } from 'framer-motion';
import { useLocation } from 'react-router-dom';
import { AnimatePresence } from 'framer-motion';

/**
 * Fades and lifts each page's content in as the route changes, without
 * remounting the sidebar/topbar shell around it.
 */
export default function PageMotion({ children }) {
  const location = useLocation();

  return (
    <AnimatePresence mode="wait">
      <motion.div
        key={location.pathname}
        className="page-motion"
        initial={{ opacity: 0, y: 8 }}
        animate={{ opacity: 1, y: 0 }}
        exit={{ opacity: 0, y: -6 }}
        transition={{ duration: 0.22, ease: 'easeOut' }}
      >
        {children}
      </motion.div>
    </AnimatePresence>
  );
}
