import { createApp } from "vue";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import "element-plus/theme-chalk/dark/css-vars.css"; // 内置暗黑模式
import "@work/styles/element-dark.scss"; // 自定义暗黑模式
import App from "./App.vue";
import router from "./router";
import "@work/styles/normalize.css"; // CSS Reset
import directives from "@work/directives";
import I18n from "@work/i18n";
// svg icons
import "virtual:svg-icons-register";
import { Icon } from "@work/components";
import errorHandler, { checkNeed } from "@/utils/errorHandler";
import Auth from "@/components/Permission/auth";
import Role from "@/components/Permission/role.vue";
import * as ElementPlusIconsVue from "@element-plus/icons-vue";

const pinia = createPinia();
const app = createApp(App);
pinia.use(piniaPluginPersistedstate);

checkNeed() && (app.config.errorHandler = errorHandler);

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}

// 全局注册按钮级别权限组件
app.component("Auth", Auth);

// 全局注册页面级别权限组件
app.component("Role", Role);

app.component("Icon", Icon);

app.use(I18n).use(pinia).use(router).use(directives).use(ElementPlus).mount("#app");
