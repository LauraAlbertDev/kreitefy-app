export interface NavLink {
  name: string;
  link: string;
  desc?: string;
}
export const NAV_LINKS: NavLink[] = [
  {
    name: 'Inicio',
    link: '/',
  },
  {
    name: 'Usuarios',
    link: '/usuarios',
  },
  {
    name: 'Estilos',
    link: '/estilos',
  },
  {
    name: 'Artistas',
    link: '/artistas',
  },
  {
    name: 'Albums',
    link: '/albums',
  },
  {
    name: 'Canciones',
    link: '/canciones',
  },
] as const;
