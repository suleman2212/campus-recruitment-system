export default function DataTable({ columns, rows, idKey, onEdit, onDelete, emptyMessage }) {
  if (!rows || rows.length === 0) {
    return <div className="empty-state">{emptyMessage || 'Nothing here yet.'}</div>;
  }

  const showActions = !!(onEdit || onDelete);

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
            <tr key={row[idKey]}>
              {columns.map((col) => (
                <td key={col.key}>{col.render ? col.render(row) : row[col.key] ?? '—'}</td>
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
