import type { Config } from "@react-router/dev/config";

export default {
  // Config options...
  // Server-side render by default, to enable SPA mode set this to `false`
  // mkai: For whatever reason, I want to use Spring boot backend, so, let's disable this and have a simple single page app.
  ssr: false,
} satisfies Config;
