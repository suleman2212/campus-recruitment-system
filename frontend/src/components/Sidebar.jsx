import { NavLink } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const DASHBOARD_LINK = { to: '/', index: '00', text: 'Dashboard' };

const SECTIONS_BY_ROLE = {
  STUDENT: [
    { label: 'Overview', links: [DASHBOARD_LINK] },
    {
      label: 'Placements',
      links: [
        { to: '/requirements', index: '01', text: 'Hiring requirements' },
        { to: '/notifications', index: '02', text: 'Job notifications' },
        { to: '/applications', index: '03', text: 'Applications' },
        { to: '/interviews', index: '04', text: 'Interview schedule' },
        { to: '/placements', index: '05', text: 'Placement results' },
      ],
    },
  ],
  COMPANY: [
    { label: 'Overview', links: [DASHBOARD_LINK] },
    {
      label: 'Hiring pipeline',
      links: [
        { to: '/requirements', index: '01', text: 'Hiring requirements' },
        { to: '/notifications', index: '02', text: 'Job notifications' },
        { to: '/applications', index: '03', text: 'Applications' },
        { to: '/participation', index: '04', text: 'College participation' },
        { to: '/host-colleges', index: '05', text: 'Host colleges' },
        { to: '/interviews', index: '06', text: 'Interview schedule' },
        { to: '/placements', index: '07', text: 'Placement results' },
      ],
    },
  ],
  COLLEGE: [
    { label: 'Overview', links: [DASHBOARD_LINK] },
    {
      label: 'Campus',
      links: [
        { to: '/students', index: '01', text: 'Students' },
        { to: '/host-colleges', index: '02', text: 'Host colleges' },
        { to: '/participation', index: '03', text: 'College participation' },
      ],
    },
    {
      label: 'Hiring pipeline',
      links: [
        { to: '/notifications', index: '04', text: 'Job notifications' },
        { to: '/interviews', index: '05', text: 'Interview schedule' },
        { to: '/placements', index: '06', text: 'Placement results' },
      ],
    },
  ],
};

const DEFAULT_SECTIONS = [
  { label: 'Overview', links: [DASHBOARD_LINK] },
  {
    label: 'Directory',
    links: [
      { to: '/colleges', index: '01', text: 'Colleges' },
      { to: '/students', index: '02', text: 'Students' },
      { to: '/companies', index: '03', text: 'Companies' },
    ],
  },
  {
    label: 'Hiring pipeline',
    links: [
      { to: '/requirements', index: '04', text: 'Hiring requirements' },
      { to: '/notifications', index: '05', text: 'Job notifications' },
      { to: '/applications', index: '06', text: 'Applications' },
      { to: '/participation', index: '07', text: 'College participation' },
      { to: '/host-colleges', index: '08', text: 'Host colleges' },
      { to: '/interviews', index: '09', text: 'Interview schedule' },
      { to: '/placements', index: '10', text: 'Placement results' },
    ],
  },
];

export default function Sidebar() {
  const { username, role, logout } = useAuth();
  const sections = SECTIONS_BY_ROLE[role] || DEFAULT_SECTIONS;

  return (
    <aside className="sidebar">
      <div className="brand">
        <span className="brand-mark">Registrar Console</span>
        <span className="brand-name">Campus Recruitment</span>
      </div>

      {sections.map((section) => (
        <div className="nav-group" key={section.label}>
          <div className="nav-label">{section.label}</div>
          {section.links.map((link) => (
            <NavLink
              key={link.to}
              to={link.to}
              end={link.to === '/'}
              className={({ isActive }) => 'nav-link' + (isActive ? ' active' : '')}
            >
              <span className="nav-index">{link.index}</span>
              {link.text}
            </NavLink>
          ))}
        </div>
      ))}

      <div className="sidebar-footer">
        Signed in as <strong>{username}</strong>
        {role && <div style={{ marginTop: 2, opacity: 0.7 }}>{role.charAt(0)}{role.slice(1).toLowerCase()} account</div>}
        <button className="logout-btn" onClick={logout}>
          Log out
        </button>
      </div>
    </aside>
  );
}
