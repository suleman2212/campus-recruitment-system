import { useEffect, useState } from 'react';

function emptyValues(fields) {
  const v = {};
  fields.forEach((f) => (v[f.name] = f.default ?? ''));
  return v;
}

export default function RecordForm({ fields, fkFields, editingRecord, onSubmit, onCancelEdit, submitLabel }) {
  const [values, setValues] = useState(emptyValues(fields));
  const [fkValues, setFkValues] = useState(() => {
    const v = {};
    (fkFields || []).forEach((f) => (v[f.name] = ''));
    return v;
  });
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    if (editingRecord) {
      const next = {};
      fields.forEach((f) => {
        const raw = f.getValue ? f.getValue(editingRecord) : editingRecord[f.name];
        next[f.name] = raw ?? '';
      });
      setValues(next);
    } else {
      setValues(emptyValues(fields));
    }
    setError('');
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [editingRecord]);

  const isEditing = !!editingRecord;

  const handleChange = (name, val) => {
    setValues((prev) => ({ ...prev, [name]: val }));
  };

  const handleFkChange = (name, val) => {
    setFkValues((prev) => ({ ...prev, [name]: val }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (!isEditing && fkFields && fkFields.some((f) => f.required !== false && !fkValues[f.name])) {
      setError('Please select a value for every linked field before saving.');
      return;
    }

    setSubmitting(true);
    try {
      const payload = { ...values };
      fields.forEach((f) => {
        if (payload[f.name] === '') {
          payload[f.name] = null;
        } else if (f.type === 'number') {
          payload[f.name] = Number(payload[f.name]);
        }
      });
      await onSubmit(payload, fkValues);
      if (!isEditing) {
        setValues(emptyValues(fields));
        setFkValues(() => {
          const v = {};
          (fkFields || []).forEach((f) => (v[f.name] = ''));
          return v;
        });
      }
    } catch (err) {
      setError(err.message || 'Something went wrong while saving.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      {error && <div className="alert alert-error">{error}</div>}

      {!isEditing && fkFields && fkFields.length > 0 && (
        <div className="form-grid" style={{ marginBottom: 14 }}>
          {fkFields.map((f) => (
            <div className="field" key={f.name}>
              <label htmlFor={f.name}>{f.label}</label>
              <select
                id={f.name}
                value={fkValues[f.name]}
                onChange={(e) => handleFkChange(f.name, e.target.value)}
                required={f.required !== false}
              >
                <option value="">Select {f.label.toLowerCase()}…</option>
                {(f.options || []).map((opt) => (
                  <option key={opt.value} value={opt.value}>
                    {opt.label}
                  </option>
                ))}
              </select>
            </div>
          ))}
        </div>
      )}

      <div className="form-grid">
        {fields.map((f) => (
          <div className="field" key={f.name}>
            <label htmlFor={f.name}>{f.label}</label>
            {f.type === 'textarea' ? (
              <textarea
                id={f.name}
                value={values[f.name]}
                required={f.required}
                onChange={(e) => handleChange(f.name, e.target.value)}
              />
            ) : f.type === 'select' ? (
              <select
                id={f.name}
                value={values[f.name]}
                required={f.required}
                onChange={(e) => handleChange(f.name, e.target.value)}
              >
                <option value="">Select…</option>
                {f.options.map((opt) => (
                  <option key={opt} value={opt}>
                    {opt}
                  </option>
                ))}
              </select>
            ) : (
              <input
                id={f.name}
                type={f.type || 'text'}
                step={f.type === 'number' ? 'any' : undefined}
                value={values[f.name]}
                required={f.required}
                placeholder={f.placeholder}
                onChange={(e) => handleChange(f.name, e.target.value)}
              />
            )}
          </div>
        ))}
      </div>

      <div className="form-actions">
        <button className="btn btn-brass" type="submit" disabled={submitting}>
          {submitting ? 'Saving…' : submitLabel || (isEditing ? 'Save changes' : 'Add record')}
        </button>
        {isEditing && (
          <button type="button" className="btn btn-ghost" onClick={onCancelEdit}>
            Cancel
          </button>
        )}
      </div>
    </form>
  );
}
