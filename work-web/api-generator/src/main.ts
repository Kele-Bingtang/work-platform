import { createApp } from "vue";
import pinia from "@/stores";
import "@/styles/index.scss";
import App from "./App.vue";
import router from "./router";
import "@work/styles/normalize.css"; // CSS Reset
import directives from "@work/directives";
import localDirectives from "@/directives";
import I18n from "@work/i18n";
import "virtual:svg-icons-register";
import { Icon } from "@work/components";
import { errorHandler, checkNeed } from "@/utils";
import { Auth, Role } from "@/components";

const app = createApp(App);

checkNeed() && (app.config.errorHandler = errorHandler);

// 全局注册按钮级别权限、页面级别权限、Icon 图标组件组件
app.use(Auth).use(Role).use(Icon);

// 是否全部引入 Element Plus 的样式
if (import.meta.env.VITE_LOAD_ALL_EP_STYLE === "true") {
  import("element-plus/theme-chalk/src/index.scss");
}

// 是否全部引入 Element Plus 的组件
if (import.meta.env.VITE_LOAD_ALL_EP_COMPONENTS === "true") {
  // EP 样式按需引入和组件全部引入有冲突。因为样式按需引入的前提是手动 import 组件（import 组件才根据组件去找样式），而组件全部引入导致在 vue 文件不需要 import。所以样式按需引入会失效，因此这里全量引入样式
  if (import.meta.env.VITE_LOAD_ALL_EP_STYLE === "false") import("element-plus/theme-chalk/src/index.scss");
  import("element-plus").then(ElementPlus => {
    app.use(ElementPlus);
  });
}

app.use(I18n).use(pinia).use(router).use(localDirectives).use(directives).mount("#app");
