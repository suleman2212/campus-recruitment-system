export default function DataTable({ columns, rows, idKey, onEdit, onDelete, emptyMessage }) {
  if (!rows || rows.length === 0) {
    return <div className="empty-state">{emptyMessage || 'Nothing here yet.'}</div>;
  }

  const showActions = !!(onEdit || onDelete);

  const getCellVal = (row, key) => {
    if (!row || !key) return '—';
    if (row[key] !== undefined && row[key] !== null && row[key] !== '') return row[key];
    const upperKey = key.charAt(0).toUpperCase() + key.slice(1);
    if (row[upperKey] !== undefined && row[upperKey] !== null && row[upperKey] !== '') return row[upperKey];
    const lowerKey = key.charAt(0).toLowerCase() + key.slice(1);
    if (row[lowerKey] !== undefined && row[lowerKey] !== null && row[lowerKey] !== '') return row[lowerKey];
    return '—';
  };

  const getRowId = (row) => {
    if (!row) return Math.random();
    if (row[idKey] !== undefined && row[idKey] !== null) return row[idKey];
    const upperId = idKey ? idKey.charAt(0).toUpperCase() + idKey.slice(1) : '';
    if (row[upperId] !== undefined && row[upperId] !== null) return row[upperId];
    return row.id ?? row.ID ?? Math.random();
  };

  return (
    <div className="table-wrap">
      <table>
        <thead>
          <tr>
            {columns.map((col) => (
              <th key={col.key}>{col.label}</th>
            ))}
            {showActions && <th></th>}
          </tr>
        </thead>
        <tbody>
          {rows.map((row) => (
            <tr key={getRowId(row)}>
              {columns.map((col) => (
                <td key={col.key}>{col.render ? col.render(row) : getCellVal(row, col.key)}</td>
              ))}
              {showActions && (
                <td>
                  <div style={{ display: 'flex', gap: 6, justifyContent: 'flex-end' }}>
                    {onEdit && (
                      <button className="btn btn-ghost" style={{ padding: '5px 10px' }} onClick={() => onEdit(row)}>
                        Edit
                      </button>
                    )}
                    {onDelete && (
                      <button className="btn-danger-text" onClick={() => onDelete(row)}>
                        Delete
                      </button>
                    )}
                  </div>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
