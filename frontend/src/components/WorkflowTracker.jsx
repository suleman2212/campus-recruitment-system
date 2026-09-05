import { motion } from 'framer-motion';

/**
 * Renders the placement drive's seven-stage pipeline as a horizontal
 * route of stamped stations, mirroring the system's own workflow
 * diagram: requirement -> notification -> eligible colleges ->
 * host college -> applications -> interviews -> results.
 *
 * @param {object} props
 * @param {Array<{key:string, label:string, count:number|null}>} props.stages
 */
export default function WorkflowTracker({ eyebrow = 'Drive pipeline', title, stages }) {
  const activeCount = stages.filter((s) => (s.count ?? 0) > 0).length;

  return (
    <div className="workflow-panel">
      <div className="workflow-head">
        <div>
          <span className="page-eyebrow">{eyebrow}</span>
          <h3>{title}</h3>
        </div>
      </div>

      <div className="workflow-track">
        {stages.map((stage, i) => {
          const isActive = (stage.count ?? 0) > 0;
          return (
            <div className={`workflow-station${isActive ? ' is-active' : ''}`} key={stage.key}>
              {i > 0 && (
                <div className="workflow-rail">
                  <motion.div
                    className="workflow-rail-fill"
                    initial={{ scaleX: 0 }}
                    animate={{ scaleX: i <= activeCount - 1 ? 1 : 0 }}
                    transition={{ duration: 0.5, delay: i * 0.05 }}
                  />
                </div>
              )}
              <motion.div
                className="workflow-stamp"
                initial={{ scale: 0.6, opacity: 0 }}
                animate={{ scale: 1, opacity: 1 }}
                transition={{ duration: 0.35, delay: i * 0.06 }}
              >
                {String(i + 1).padStart(2, '0')}
              </motion.div>
              <div className="workflow-station-label">{stage.label}</div>
              <div className="workflow-station-count">
                {stage.count === null ? '…' : `${stage.count} record${stage.count === 1 ? '' : 's'}`}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
