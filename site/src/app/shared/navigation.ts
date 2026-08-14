export interface NavLink {
  name: string;
  link: string;
  desc?: string;
}
export const NAV_LINKS: NavLink[] = [
  {
    name: 'Inicio',
    link: '/'
  },
  {
    name: 'Catálogo',
    link: '/canciones'
  },
] as const;
