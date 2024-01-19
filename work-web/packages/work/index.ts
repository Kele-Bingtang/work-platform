import components from "./components";
import { installComponents } from "./installer";

export * from "@work/components";
export * from "@work/constants";
export * from "@work/directives";
export * from "@work/hooks";
export * from "@work/request";
export * from "@work/utils";

const installer = installComponents([...components]);

export const install = installer.install;
export default installer;
