import type { App } from "vue";
import role from "./modules/role";
import auth from "./modules/auth";

const directivesList: any = {
  role,
  auth,
};

const localDirectives = {
  install: function (app: App<Element>) {
    Object.keys(directivesList).forEach(key => {
      // 注册所有自定义指令
      app.directive(key, directivesList[key]);
    });
  },
};

export default localDirectives;
