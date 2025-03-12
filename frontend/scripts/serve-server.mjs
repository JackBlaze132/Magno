import serve from 'serve';

// Configura el servidor usando la variable de entorno para la IP
serve('dist', {
  port: 3000,
  host: process.env.INTERNAL_RESOURCE_IP || '0.0.0.0',
  single: true  // Activa el modo SPA
});
