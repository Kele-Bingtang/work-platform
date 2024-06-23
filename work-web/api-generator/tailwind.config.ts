import type { Config } from "tailwindcss";
import colors from "tailwindcss/colors";

export default {
  darkMode: "selector", // 暗黑模式
  content: [
    "./src/layout/**/*.{tsx,vue,scss,ts}",
    "./src/components/**/*.{html,tsx,vue,scss,ts}",
    "./src/views/**/*.{html,js,tsx,vue,scss,jsx,ts}",
  ],
  theme: {
    colors: {
      // 使用官方的默认调色板
      ...colors,
      // 添加 EP 色
      primary: "var(--el-color-primary)",
      success: "var(--el-color-success)",
      warning: "var(--el-color-warning)",
      danger: "var(--el-color-danger)",
      info: "var(--el-color-info)",
    },
    extend: {},
  },
  plugins: [],
} satisfies Config;
