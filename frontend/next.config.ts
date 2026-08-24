import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  async redirects() {
    return [
      {
        source: '/employees/list',
        destination: '/employees/adm002',
        permanent: true,
      },
      {
        source: '/employees/detail',
        destination: '/employees/adm003',
        permanent: true,
      },
    ];
  },
};

export default nextConfig;
