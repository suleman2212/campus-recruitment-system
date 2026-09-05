import { useCallback, useEffect, useState } from 'react';
import toast from 'react-hot-toast';
import DataTable from './DataTable';
import RecordForm from './RecordForm';

/**
 * @param {object} props
 * @param {string} props.eyebrow
 * @param {string} props.title
 * @param {string} props.description
 * @param {string} props.idKey - primary key field name on each row
 * @param {() => Promise<any[]>} props.listFn
 * @param {(values: object, fkValues: object) => Promise<any>} props.createFn
 * @param {(id: any, values: object) => Promise<any>} props.updateFn
 * @param {(id: any) => Promise<any>} props.deleteFn
 * @param {Array} props.columns - [{key, label, render?(row)}]
 * @param {Array} props.fields - scalar form fields, shared by create + edit
 * @param {Array} [props.fkFields] - [{name, label, required?, optionsLoader, mapOption}] create-only linked fields
 * @param {boolean} [props.readOnly] - when true, hides the add/edit form and
 *   row actions so the account can see records but not change them.
 * @param {string} [props.readOnlyNote] - optional explanation shown in place of the form.
 */
export default function CrudPage({
  eyebrow,
  title,
  description,
  idKey,
  listFn,
  createFn,
  updateFn,
  deleteFn,
  columns,
  fields,
  fkFields,
  readOnly = false,
  readOnlyNote,
}) {
  const [rows, setRows] = useState([]);
  const [loading, setLoading] = useState(true);
  const [loadError, setLoadError] = useState('');
  const [editingRecord, setEditingRecord] = useState(null);
  const [resolvedFkFields, setResolvedFkFields] = useState(fkFields || []);

  const loadRows = useCallback(async () => {
    setLoading(true);
    setLoadError('');
    try {
      const data = await listFn();
      setRows(Array.isArray(data) ? data : []);
    } catch (err) {
      setLoadError(err.message || 'Could not load records.');
    } finally {
      setLoading(false);
    }
  }, [listFn]);

  useEffect(() => {
    loadRows();
  }, [loadRows]);

  useEffect(() => {
    let cancelled = false;
    async function loadOptions() {
      if (readOnly || !fkFields || fkFields.length === 0) return;
      const resolved = await Promise.all(
        fkFields.map(async (f) => {
          try {
            const data = await f.optionsLoader();
            const options = (Array.isArray(data) ? data : []).map(f.mapOption);
            return { ...f, options };
          } catch {
            return { ...f, options: [] };
          }
        })
      );
      if (!cancelled) setResolvedFkFields(resolved);
    }
    loadOptions();
    return () => {
      cancelled = true;
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [readOnly]);

  const handleSubmit = async (values, fkValues) => {
    if (editingRecord) {
      await updateFn(editingRecord[idKey], values);
      toast.success('Changes saved.');
      setEditingRecord(null);
    } else {
      await createFn(values, fkValues);
      toast.success('Record added.');
    }
    await loadRows();
  };

  const handleDelete = async (row) => {
    if (!window.confirm('Delete this record? This cannot be undone.')) return;
    try {
      await deleteFn(row[idKey]);
      toast.success('Record deleted.');
      if (editingRecord && editingRecord[idKey] === row[idKey]) setEditingRecord(null);
      await loadRows();
    } catch (err) {
      toast.error(err.message || 'Could not delete record.');
    }
  };

  return (
    <div>
      <div className="page-header">
        <div>
          <span className="page-eyebrow">{eyebrow}</span>
          <h1>{title}</h1>
          {description && <p>{description}</p>}
        </div>
        {readOnly && <span className="badge badge-brass">View only</span>}
      </div>

      {readOnly ? (
        <div className="panel">
          <div className="panel-title">
            <h3>View only</h3>
          </div>
          <p style={{ color: 'var(--slate)', fontSize: '0.88rem', margin: 0 }}>
            {readOnlyNote ||
              'Your account can review these records, but adding, editing or deleting them happens elsewhere in the pipeline.'}
          </p>
        </div>
      ) : (
        <div className="panel">
          <div className="panel-title">
            <h3>{editingRecord ? 'Edit record' : 'Add new'}</h3>
          </div>
          <RecordForm
            fields={fields}
            fkFields={resolvedFkFields}
            editingRecord={editingRecord}
            onSubmit={handleSubmit}
            onCancelEdit={() => setEditingRecord(null)}
          />
        </div>
      )}

      <div className="panel">
        <div className="panel-title">
          <h3>All records {rows.length > 0 && <span className="badge">{rows.length}</span>}</h3>
          <button className="btn btn-ghost" onClick={loadRows}>
            Refresh
          </button>
        </div>
        {loading && <div className="empty-state">Loading…</div>}
        {loadError && <div className="alert alert-error">{loadError}</div>}
        {!loading && !loadError && (
          <DataTable
            columns={columns}
            rows={rows}
            idKey={idKey}
            onEdit={readOnly ? undefined : setEditingRecord}
            onDelete={readOnly ? undefined : handleDelete}
          />
        )}
      </div>
    </div>
  );
}
